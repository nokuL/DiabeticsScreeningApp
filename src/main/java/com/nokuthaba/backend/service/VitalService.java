package com.nokuthaba.backend.service;

import com.nokuthaba.backend.dto.VitalDto;
import com.nokuthaba.backend.models.*;
import com.nokuthaba.backend.repository.BodyMassIndexRepository;
import com.nokuthaba.backend.repository.VitalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VitalService {

    @Autowired
    VitalsRepository vitalsRepository;

    @Autowired
    DecisionSupportService decisionSupportService;

    @Autowired
    BodyMassIndexRepository bodyMassIndexRepository;

    public VitalSign updateVitalSign(VitalSign vitalSign) {
        if (vitalsRepository.existsById(vitalSign.getId())) {
            return vitalsRepository.save(vitalSign);
        } else {
            throw new IllegalArgumentException("Vital  sign not found by ID" + vitalSign.getId());
        }

    }

    public void deleteById(String id) {
        if (vitalsRepository.existsById(id)) {
            vitalsRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Invalid ID for vital sign" + id);
        }
    }

    public List<VitalSign> fetchAllVitalSigns() {
        return vitalsRepository.findAll();
    }

    public List<VitalSign> convertVitalDtoToVitalSignList(VitalDto vitalDto) {

        List<VitalSign> vitalSignList = new ArrayList<>();

        if (vitalDto.getHeight() != null) {
            VitalSign heightVital = VitalSign.builder()
                    .id(UUID.randomUUID().toString())
                    .vitalSignType(VitalSignType.Height)
                    .value(vitalDto.getHeight())
                    .dateRecorded(LocalDate.now())
                    .unitOfMeasurement(Unit.cm)
                    .performerId(vitalDto.getPerformerId())
                    .patientId(vitalDto.getPatientId())
                    .build();

            vitalSignList.add(heightVital);
        }

        if (vitalDto.getWeight() != null) {
            VitalSign weightVital = VitalSign.builder()
                    .id(UUID.randomUUID().toString())
                    .vitalSignType(VitalSignType.WEIGHT)
                    .value(vitalDto.getWeight())
                    .unitOfMeasurement(Unit.kg)
                    .dateRecorded(LocalDate.now())
                    .patientId(vitalDto.getPatientId())
                    .build();

            vitalSignList.add(weightVital);
        }

        if (vitalDto.getTemperature() != null) {
            VitalSign temperatureVital = VitalSign.builder()
                    .id(UUID.randomUUID().toString())
                    .vitalSignType(VitalSignType.TEMPERATURE)
                    .value(vitalDto.getTemperature())
                    .unitOfMeasurement(Unit.celcius_degrees)
                    .dateRecorded(LocalDate.now())
                    .patientId(vitalDto.getPatientId())
                    .build();
            vitalSignList.add(temperatureVital);
        }

        if (vitalDto.getPulse() != null) {
            VitalSign pulseVital = VitalSign.builder()
                    .id(UUID.randomUUID().toString())
                    .vitalSignType(VitalSignType.PULSE)
                    .value(vitalDto.getPulse())
                    .unitOfMeasurement(Unit.bpm)
                    .dateRecorded(LocalDate.now())
                    .patientId(vitalDto.getPatientId())
                    .build();
            vitalSignList.add(pulseVital);
        }

        if (vitalDto.getBloodGlucose() != null) {
            VitalCategory vitalCategory = decisionSupportService.getBloodGlucoseCategory(vitalDto.getBloodGlucose());
            VitalSign bloodGlucoseVital = VitalSign.builder()
                    .id(UUID.randomUUID().toString())
                    .vitalSignType(VitalSignType.BLOOD_GLUCOSE)
                    .value(vitalDto.getBloodGlucose())
                    .vitalCategory(vitalCategory)
                    .unitOfMeasurement(Unit.mgPerDL)
                    .dateRecorded(LocalDate.now())
                    .patientId(vitalDto.getPatientId())
                    .build();
            vitalSignList.add(bloodGlucoseVital);
        }

        if (vitalDto.getDiastolicBloodPressure() != null && vitalDto.getSystolicBloodPressure() != null) {
            VitalCategory vitalCategory = decisionSupportService.getBloodPressureCategory(vitalDto.getSystolicBloodPressure(), vitalDto.getDiastolicBloodPressure());
            VitalSign bloodPressure = VitalSign.builder()
                    .id(UUID.randomUUID().toString())
                    .vitalSignType(VitalSignType.BLOOD_PRESSURE)
                    .systolicValue(vitalDto.getSystolicBloodPressure())
                    .diastolicValue(vitalDto.getDiastolicBloodPressure())
                    .dateRecorded(LocalDate.now())
                    .vitalCategory(vitalCategory)
                    .unitOfMeasurement(Unit.mmHg)
                    .patientId(vitalDto.getPatientId())
                    .build();
            vitalSignList.add(bloodPressure);
        }

        if (vitalDto.getHeight() != null && vitalDto.getWeight() != null) {
            BodyMassIndex bodyMassIndex = decisionSupportService.getBMICategory(vitalDto.getWeight(), vitalDto.getHeight(), vitalDto.getPatientId());
            saveBmi(bodyMassIndex);
        }

        return saveVitalSigns(vitalSignList);
    }

    public List<VitalSign> saveVitalSigns(List<VitalSign> vitalSignList) {

        List<VitalSign> vitalsToSave =
                vitalSignList.stream().filter((vitalSign -> vitalSign.getPatientId() != null
                                && vitalSign.getDateRecorded() != null))
                        .collect(Collectors.toList());

        return vitalsRepository.saveAll(vitalsToSave);

    }

    private BodyMassIndex saveBmi(BodyMassIndex massIndex) {
        return bodyMassIndexRepository.save(massIndex);

    }


}

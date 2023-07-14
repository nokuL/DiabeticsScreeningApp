package com.nokuthaba.backend.service;

import com.nokuthaba.backend.dto.DecisionSupportDto;
import com.nokuthaba.backend.models.BodyMassIndex;
import com.nokuthaba.backend.models.VitalCategory;
import com.nokuthaba.backend.models.VitalSign;
import com.nokuthaba.backend.models.VitalSignType;
import com.nokuthaba.backend.repository.BodyMassIndexRepository;
import com.nokuthaba.backend.repository.VitalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DecisionSupportService {

    @Autowired
    VitalsRepository vitalsRepository;

    @Autowired
    BodyMassIndexRepository bodyMassIndexRepository;

    public BodyMassIndex getBMICategory(double weight , double height, String patientId){

        BodyMassIndex bmi = new BodyMassIndex();

        double bmiValue = weight / Math.pow(height, 2);
        bmi.setDateRecorded(LocalDate.now());
        bmi.setValue(bmiValue);
        bmi.setPatientId(patientId);
        bmi.setId(UUID.randomUUID().toString());


        if (bmiValue <= 18.5) {
            bmi.setVitalCategory(VitalCategory.UNDER_WEIGHT);
        } else if (bmiValue > 18.5 && bmiValue <= 24.5) {
            bmi.setVitalCategory(VitalCategory.NORMAL);
        } else if (bmiValue >30 && bmiValue <= 39.9) {
            bmi.setVitalCategory( VitalCategory.OVERWEIGHT);
        } else {
            bmi.setVitalCategory(VitalCategory.OBESE);
        }

        return bmi;

    }
    public  VitalCategory getBloodGlucoseCategory(double bloodGlucose){
        if (bloodGlucose < 70) {
            return VitalCategory.LOW;
        } else if (bloodGlucose > 70 && bloodGlucose<= 100) {
            return VitalCategory.NORMAL;
        } else if (bloodGlucose < 126) {
            return VitalCategory.HIGH;
        } else {
            return VitalCategory.VERY_HIGH;
        }
    }
    public VitalCategory getBloodPressureCategory(double systolicBP , double diastolicBP){
        if (systolicBP < 120 && diastolicBP < 80) {
            return VitalCategory.NORMAL;
        } else if (systolicBP >= 120 && systolicBP <= 129 && diastolicBP < 80) {
            return VitalCategory.ELEVATED;
        } else if (systolicBP >= 130 && systolicBP <= 139 || diastolicBP >= 80 && diastolicBP <89 ) {
            return VitalCategory.HIGH_BP_STAGE_1;
        } else if (systolicBP >= 140 && systolicBP < 180 || diastolicBP >= 90 && diastolicBP <= 120){
            return VitalCategory.HIGH_BP_STAGE_2;
        }else {
            return VitalCategory.HYPERTENSIVE_CRISI;
        }

    }

    public Map<VitalCategory, List<VitalSign>> groupVitalSignsByCategoryAndMonth( DecisionSupportDto decisionSupportDtoLocalDate) {
        VitalSignType vitalSignType = decisionSupportDtoLocalDate.getVitalSignType();
        LocalDate localDate = decisionSupportDtoLocalDate.getLocalDate();

        List<VitalSign>vitalSigns = vitalsRepository.findAll();
        return vitalSigns.stream()
                .filter(vitalSign -> vitalSign.getDateRecorded()!= null)
                .filter(vitalSign -> vitalSign.getDateRecorded().getMonth() == localDate.getMonth())
                .filter(vitalSign -> vitalSign.getVitalSignType()!= null)
                .filter(vitalSign -> vitalSign.getVitalSignType().equals(vitalSignType))
                .collect(Collectors.groupingBy(VitalSign::getVitalCategory));
    }

    public Map<VitalCategory, List<BodyMassIndex>>groupBMIByVitalCategory(DecisionSupportDto decisionSupportDtoLocalDate){
        List<BodyMassIndex>bodyMassIndices = bodyMassIndexRepository.findAll();
       return bodyMassIndices.stream()
                .filter(bmi -> bmi.getDateRecorded() != null)
                .filter(bmi -> bmi.getDateRecorded().getMonth() == decisionSupportDtoLocalDate.getLocalDate().getMonth())
                .filter(bmi -> bmi.getVitalCategory()!= null)
                .collect(Collectors.groupingBy(BodyMassIndex::getVitalCategory));

    }









}
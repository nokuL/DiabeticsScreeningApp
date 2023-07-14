package com.nokuthaba.backend.unit_tests;

import com.nokuthaba.backend.dto.DecisionSupportDto;
import com.nokuthaba.backend.models.BodyMassIndex;
import com.nokuthaba.backend.models.VitalCategory;
import com.nokuthaba.backend.models.VitalSign;
import com.nokuthaba.backend.models.VitalSignType;
import com.nokuthaba.backend.repository.VitalsRepository;
import com.nokuthaba.backend.service.DecisionSupportService;
import com.nokuthaba.backend.service.VitalService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class DecisionSupportServiceTest {

    @InjectMocks
    private  DecisionSupportService decisionSupportService ;

    @Mock
    private VitalsRepository vitalsRepository;



    @Test
    public void testGetBMICategory() {
        String patientId = UUID.randomUUID().toString();
        double weight = 75;
        double height = 1.7;
        BodyMassIndex bmi = decisionSupportService.getBMICategory(weight, height, patientId);

        assertNotNull(bmi);
        assertEquals(patientId, bmi.getPatientId());
        assertEquals(LocalDate.now(), bmi.getDateRecorded());
        assertEquals(weight / (height * height), bmi.getValue(), 0.01);

        if (bmi.getValue() <= 18.5) {
            assertEquals(VitalCategory.UNDER_WEIGHT, bmi.getVitalCategory());
        } else if (bmi.getValue() > 18.5 && bmi.getValue() <= 24.5) {
            assertEquals(VitalCategory.NORMAL, bmi.getVitalCategory());
        } else if (bmi.getValue() > 30 && bmi.getValue() <= 39.9) {
            assertEquals(VitalCategory.OVERWEIGHT, bmi.getVitalCategory());
        } else {
            assertEquals(VitalCategory.OBESE, bmi.getVitalCategory());
        }
    }

    @Test
    public void testGetBloodGlucoseCategory() {
        double lowGlucose = 50;
        double normalGlucose = 85;
        double highGlucose = 120;
        double veryHighGlucose = 200;

        assertEquals(VitalCategory.LOW, decisionSupportService.getBloodGlucoseCategory(lowGlucose));
        assertEquals(VitalCategory.NORMAL, decisionSupportService.getBloodGlucoseCategory(normalGlucose));
        assertEquals(VitalCategory.HIGH, decisionSupportService.getBloodGlucoseCategory(highGlucose));
        assertEquals(VitalCategory.VERY_HIGH, decisionSupportService.getBloodGlucoseCategory(veryHighGlucose));
    }

    @Test
    public void testGetBloodPressureCategory() {
        double normalSystolic = 110;
        double normalDiastolic = 70;
        double elevatedSystolic = 125;
        double elevatedDiastolic = 70;
        double stage1Systolic = 135;
        double stage1Diastolic = 85;
        double stage2Systolic = 150;
        double stage2Diastolic = 95;
        double hypertensiveCrisisSystolic = 180;
        double hypertensiveCrisisDiastolic = 120;

        assertEquals(VitalCategory.NORMAL, decisionSupportService.getBloodPressureCategory(normalSystolic, normalDiastolic));
        assertEquals(VitalCategory.ELEVATED, decisionSupportService.getBloodPressureCategory(elevatedSystolic, elevatedDiastolic));
        assertEquals(VitalCategory.HIGH_BP_STAGE_1, decisionSupportService.getBloodPressureCategory(stage1Systolic, stage1Diastolic));
        assertEquals(VitalCategory.HIGH_BP_STAGE_2, decisionSupportService.getBloodPressureCategory(stage2Systolic, stage2Diastolic));
        assertEquals(VitalCategory.HIGH_BP_STAGE_2, decisionSupportService.getBloodPressureCategory(hypertensiveCrisisSystolic, hypertensiveCrisisDiastolic));
    }

    @Test
    public void testGroupVitalSignsByCategoryAndMonth() {
        LocalDate localDate = LocalDate.of(2022, 1, 1);
        VitalSignType vitalSignType = VitalSignType.BLOOD_PRESSURE;

        VitalSign vitalSign1 = VitalSign.builder()
                .id("1")
                .dateRecorded(localDate)
                .vitalCategory(VitalCategory.NORMAL)
                .patientId("patient1")
                .value(120).build();

        VitalSign vitalSign2 = VitalSign.builder()
                .id("1")
                .dateRecorded(localDate)
                .vitalCategory(VitalCategory.HIGH_BP_STAGE_1)
                .patientId("patient1")
                .value(130).build();

        VitalSign vitalSign3 = VitalSign.builder()
                .id("1")
                .dateRecorded(localDate)
                .vitalCategory(VitalCategory.HIGH_BP_STAGE_2)
                .patientId("patient1")
                .value(130).build();

        List<VitalSign> vitalSigns = new ArrayList<>(Arrays.asList(vitalSign1, vitalSign2, vitalSign3));

        when(vitalsRepository.findAll()).thenReturn(vitalSigns);

        Map<VitalCategory, List<VitalSign>> expectedMap = new HashMap<>();
        expectedMap.put(VitalCategory.NORMAL, Arrays.asList(vitalSign1));
        expectedMap.put(VitalCategory.HIGH_BP_STAGE_1, Arrays.asList(vitalSign2));
        expectedMap.put(VitalCategory.HIGH_BP_STAGE_2, Arrays.asList(vitalSign3));

        DecisionSupportDto decisionSupportDto = new DecisionSupportDto();
        decisionSupportDto.setVitalSignType(vitalSignType);
        decisionSupportDto.setLocalDate(LocalDate.now());

        Map<VitalCategory, List<VitalSign>> actualMap = decisionSupportService.groupVitalSignsByCategoryAndMonth(decisionSupportDto);

        assertEquals(expectedMap, actualMap);
    }

}
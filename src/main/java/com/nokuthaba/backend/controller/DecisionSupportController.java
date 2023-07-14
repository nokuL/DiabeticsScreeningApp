package com.nokuthaba.backend.controller;

import com.nokuthaba.backend.dto.DecisionSupportDto;
import com.nokuthaba.backend.models.BodyMassIndex;
import com.nokuthaba.backend.models.Patient;
import com.nokuthaba.backend.models.VitalCategory;
import com.nokuthaba.backend.models.VitalSign;
import com.nokuthaba.backend.service.DecisionSupportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class DecisionSupportController {

    @Autowired
    DecisionSupportService decisionSupportService;


    @GetMapping("/listVitalsByMonthAndCategory")
    public Map<VitalCategory, List<VitalSign>> groupVitalSignsByCategoryAndMonth(@RequestBody DecisionSupportDto decisionSupportDto){
        return decisionSupportService.groupVitalSignsByCategoryAndMonth(decisionSupportDto);

    }

    @GetMapping("/listBMIByMonthAndCategory")
    public Map<VitalCategory, List<BodyMassIndex>> groupBMIByVitalCategory(@RequestBody DecisionSupportDto decisionSupportDto){
        return decisionSupportService.groupBMIByVitalCategory(decisionSupportDto);

    }
}

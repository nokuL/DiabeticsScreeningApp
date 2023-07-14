package com.nokuthaba.backend.controller;


import com.nokuthaba.backend.dto.VitalDto;
import com.nokuthaba.backend.models.VitalSign;
import com.nokuthaba.backend.service.VitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import static com.nokuthaba.backend.util.Constants.REPORT_URI;

@RestController
public class VitalsController {

    @Autowired
    private VitalService vitalService;


    @PostMapping("/saveVitalRecord")
    public List<VitalSign> recordVitalRecord(@RequestBody VitalDto vitalDto)  {
        System.out.println(">>>>>>>>>>>>>>>>>>>> herere"+ vitalDto);
      return   vitalService.convertVitalDtoToVitalSignList(vitalDto);


    }

    @PostMapping("/recordVitals")
    public void recordAndMapVitals(@RequestBody VitalDto vitalDto){
        System.out.println(">>>>>>>>>>>>>> vital sent here "+ vitalDto);

    }

    @GetMapping("/vitalSignList")
    public List<VitalSign> getVitalSigns(){
        return vitalService.fetchAllVitalSigns();

    }

    @PutMapping("/update-vitalSign")
    public VitalSign updateVitalSign(@RequestBody VitalSign vitalSign) throws URISyntaxException {
        VitalSign vitalSignSaved = vitalService.updateVitalSign(vitalSign);
         return  vitalSignSaved;
    }

    @DeleteMapping("/delete/vitalSign/{id}")
    public void deleteById(@PathVariable String id){
        vitalService.deleteById(id);
    }



}


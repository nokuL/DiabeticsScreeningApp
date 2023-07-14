package com.nokuthaba.backend.controller;

import com.nokuthaba.backend.models.Patient;
import com.nokuthaba.backend.models.RecordSyncStatus;
import com.nokuthaba.backend.service.PatientsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.nokuthaba.backend.util.Constants.REPORT_URI;

@RestController
@Slf4j
public class PatientController {

    @Autowired
    private PatientsService patientsService;

    @Autowired
    private RestTemplate restTemplate;


    @PostMapping("/savePatient")
    public Patient recordPatient(@RequestBody Patient patient) throws URISyntaxException {

        Patient patientSaved = patientsService.recordPatient(patient);

        return patientSaved;
    }

    @GetMapping("/patientsList")
    public List<Patient> getPatients(){
        return patientsService.findAllPatients();

    }

    @PutMapping("/update-patient")
    public Patient updatePatient(@RequestBody Patient patient) throws URISyntaxException {
        Patient savedPatient = patientsService.editPatient(patient);
        return  savedPatient;
    }

    @DeleteMapping("/delete/patient/{id}")
    public void deleteCourse(@PathVariable String id){
        patientsService.deleteById(id);
    }




}

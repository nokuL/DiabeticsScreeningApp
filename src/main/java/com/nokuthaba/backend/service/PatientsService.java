package com.nokuthaba.backend.service;

import com.nokuthaba.backend.models.Patient;
import com.nokuthaba.backend.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import static com.nokuthaba.backend.util.Constants.REPORT_URI;

@Service
@Slf4j
public class PatientsService {

    private final PatientRepository patientRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public PatientsService(PatientRepository patientRepository, RestTemplate restTemplate) {
        this.patientRepository = patientRepository;
        this.restTemplate = restTemplate;
    }


    public Patient recordPatient(Patient patient) {
        if(patient.getGender() == null){
            throw  new IllegalArgumentException("Invalid Gender for patient");
        }
        if(patient.getFirstName() == null){
            throw new IllegalArgumentException("Invalid first name");
        }
        if(patient.getLastName()== null){
            throw new IllegalArgumentException("Invalid last name");
        }
        String id = UUID.randomUUID().toString();
        patient.setId(id);
        return patientRepository.save(patient);
    }

    public List<Patient> findAllPatients() {

        return patientRepository.findAll();
    }

    public Patient editPatient(Patient patient) {
        if (patientRepository.existsById(patient.getId())) {
            return patientRepository.save(patient);
        } else {
            throw new IllegalArgumentException("Patient not found by ID" + patient.getId());
        }
    }

    public void deleteById(String id) {
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Patient not found by ID" + id);
        }

    }

    public Patient syncToServer(Patient patient, boolean isUpdate) throws URISyntaxException {
        URI uri;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Patient> httpEntity = new HttpEntity<>(patient, headers);
        log.info(">>>>>>>>>>>>>>>>>>>> SYNC SERVER REACHED");
        uri = isUpdate ? new URI(REPORT_URI + "savePatientReportRecord") : new URI(REPORT_URI + "updatePatientReportRecord");
        return restTemplate.postForObject(uri, httpEntity, Patient.class);
    }

    public Patient findById(String id) {
        return patientRepository.findById(id).orElse(null);
    }
}
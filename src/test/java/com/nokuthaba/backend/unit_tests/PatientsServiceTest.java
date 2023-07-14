package com.nokuthaba.backend.unit_tests;

import com.nokuthaba.backend.mockData.PatientMockData;
import com.nokuthaba.backend.models.Patient;
import com.nokuthaba.backend.repository.PatientRepository;
import com.nokuthaba.backend.service.PatientsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PatientsServiceTest {
    @InjectMocks
    PatientsService patientsService;

    @Mock
    PatientRepository patientRepository;

    @Before
    public void setup() {

        lenient().when(patientRepository.save(Mockito.any(Patient.class))).thenReturn(new Patient());
    }

    @Test
    public void testRecordValidPatient() {
        Patient patient = PatientMockData.getCompletePatientMock();

        when(patientRepository.save(patient)).thenReturn(patient);

        Patient savedPatient = patientsService.recordPatient(patient);

        Assertions.assertEquals(savedPatient.getFirstName(), patient.getFirstName());
        Assertions.assertEquals(savedPatient.getGender(), patient.getGender());
        Assertions.assertEquals(savedPatient.getLastName(), patient.getLastName());
        verify(patientRepository, times(1)).save(patient);

    }

    @Test
    public void testRecordNUllGenderPatient() {
        Patient invalidPatient = PatientMockData.getNullGenderPatient();
        Assertions.assertThrows(IllegalArgumentException.class, () -> patientsService.recordPatient(invalidPatient));
        verify(patientRepository, times(0)).save(invalidPatient);
    }

    @Test
    public void testRecordNUllFirstNamePatient() {
        Patient invalidPatient = PatientMockData.getNullFirstNamePatient();
        Assertions.assertThrows(IllegalArgumentException.class, () -> patientsService.recordPatient(invalidPatient));
        verify(patientRepository, times(0)).save(invalidPatient);

    }

    @Test
    public void testRecordNUllLastNamePatient() {
        Patient invalidPatient = PatientMockData.getNullLastNamePatient();
        Assertions.assertThrows(IllegalArgumentException.class, () -> patientsService.recordPatient(invalidPatient));
        verify(patientRepository, times(0)).save(invalidPatient);

    }

    @Test
    public void testFindAllPatients() {
        Patient patient1 = PatientMockData.getCompletePatientMock();

        Patient patient2 = PatientMockData.getCompletePatientMock();

        given(patientsService.recordPatient(patient1)).willReturn(PatientMockData.getCompletePatientMock());
        given(patientsService.recordPatient(patient2)).willReturn(PatientMockData.getCompletePatientMock());

        List<Patient> patients = PatientMockData.getPatientMockList();
        when(patientRepository.findAll()).thenReturn(patients);

        List<Patient> foundPatients = patientsService.findAllPatients();

        assertEquals(patients, foundPatients);

    }

    @Test
    public void testEditValidPatient() {
        Patient patient1 = PatientMockData.getCompletePatientMock();

        when(patientRepository.existsById("1")).thenReturn(true);

        when(patientRepository.save(patient1)).thenReturn(patient1);

        Patient savedPatient = patientsService.editPatient(patient1);

        Assertions.assertEquals(patient1, savedPatient);


    }

    @Test
    public void testEditInvalidPatient() {
        Patient patient1 = PatientMockData.getCompletePatientMock();

        when(patientRepository.existsById("1")).thenReturn(false);
        Assertions.assertThrows(IllegalArgumentException.class, () -> patientsService.editPatient(patient1));
    }

    @Test
    public void testDeleteValidPatient() {
        when(patientRepository.existsById("1")).thenReturn(true);

        doNothing().when(patientRepository).deleteById("1");

        patientsService.deleteById("1");

        verify(patientRepository, times(1)).deleteById("1");

    }


    @Test
    public void testDeleteInvalidPatient() {
        when(patientRepository.existsById("1")).thenReturn(false);

        doNothing().when(patientRepository).deleteById("1");

        Assertions.assertThrows(IllegalArgumentException.class, () -> patientsService.deleteById("1"));
        verify(patientRepository, times(0)).deleteById("1");

    }


}

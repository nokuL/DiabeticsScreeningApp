package com.nokuthaba.backend.unit_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nokuthaba.backend.controller.PatientController;
import com.nokuthaba.backend.mockData.PatientMockData;
import com.nokuthaba.backend.models.Patient;
import com.nokuthaba.backend.repository.UserRepository;
import com.nokuthaba.backend.service.PatientsService;
import com.nokuthaba.backend.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {PatientController.class})
@AutoConfigureMockMvc(secure = false)
public class PatientsControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    @MockBean
    UserService userService;

    @MockBean
    PatientsService patientsService;

    @MockBean
    RestTemplate restTemplate;

    @BeforeEach
    void before(){
        Patient patientObject = PatientMockData.getCompletePatientMock();

    }

    @Test
    public void testGetPatients() throws Exception {
        List<Patient> patients = PatientMockData.getPatientMockList();
        when(patientsService.findAllPatients()).thenReturn(patients);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/patientsList");
        ResultActions perform = mockMvc.perform(mockHttpServletRequestBuilder);
        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        Assertions.assertEquals(200 , mockHttpServletResponse.getStatus());
    }

    @Test
    public void testEditPatient() throws Exception {
        Patient updatedPatient =  new Patient();
        updatedPatient.setId("1");
        updatedPatient.setFirstName("Noku");
        updatedPatient.setLastName("Noku");
        updatedPatient.setAddress("Address");

        when(patientsService.editPatient(any(Patient.class))).thenReturn(updatedPatient);

        ObjectMapper objectMapper = new ObjectMapper();
        String patientJson = objectMapper.writeValueAsString(updatedPatient);
        mockMvc.perform(put("/update-patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientJson))
                .andExpect(status().isOk());

        verify(patientsService, times(1)).editPatient(any(Patient.class));
    }




    @Test
    public void deletePatientTest() throws  Exception{
        doNothing().when(patientsService).deleteById("1");
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.delete("/delete/patient/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions perform = mockMvc.perform(mockHttpServletRequestBuilder);
        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        Assertions.assertEquals(200, status);
    }
}

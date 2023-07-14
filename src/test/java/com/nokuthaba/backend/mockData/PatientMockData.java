package com.nokuthaba.backend.mockData;

import com.nokuthaba.backend.models.Gender;
import com.nokuthaba.backend.models.Patient;;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class PatientMockData {

    public static Patient getCompletePatientMock(){

        return Patient.builder()
                .firstName("Noku")
                .lastName("Lunga")
                .gender(Gender.FEMALE)
                .birthDate(LocalDate.now())
                .address("Blue zone")
                .id("1")
                .build();
    }

    public static Patient getNullGenderPatient(){

        return Patient.builder()
                .firstName("Rex")
                .lastName("Munhu")
                .build();
    }

    public static Patient getNullFirstNamePatient(){
        return Patient.builder()
                .lastName("Ncube")
                .gender(Gender.FEMALE)
                .build();
    }

    public static Patient getNullLastNamePatient(){
        return  Patient.builder()
                .firstName("Lang")
                .gender(Gender.FEMALE).build();
    }

    public static List<Patient>getPatientMockList(){
        return Arrays.asList(getCompletePatientMock(), getCompletePatientMock());
    }
}

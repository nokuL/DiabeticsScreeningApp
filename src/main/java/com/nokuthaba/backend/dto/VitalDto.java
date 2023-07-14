package com.nokuthaba.backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;

@Getter
@ToString
@Setter
public class VitalDto {
    private Double temperature;
    private Double height;
    private Double weight;
    private Double pulse;
    private Double bloodGlucose;
    private Double diastolicBloodPressure;
    private Double systolicBloodPressure;
    private String performerId;
    private String patientId;

}

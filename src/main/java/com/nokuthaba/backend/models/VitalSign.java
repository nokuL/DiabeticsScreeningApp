package com.nokuthaba.backend.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class VitalSign {
    @Id
    private String id ;

    @Enumerated(EnumType.STRING)
    private VitalSignType vitalSignType;

    @Enumerated(EnumType.STRING)
    private Unit unitOfMeasurement;

    private String patientId;

    private LocalDate dateRecorded;

    private double value;

    private String performerId;

    private double systolicValue;

    private double diastolicValue;
     @Enumerated(EnumType.STRING)
    private VitalCategory vitalCategory;



}

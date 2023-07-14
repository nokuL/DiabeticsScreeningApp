package com.nokuthaba.backend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDate;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BodyMassIndex {
    @Id
    private  String  id;
    private  String patientId;
    private  double value;
    private  LocalDate dateRecorded;
    @Enumerated(EnumType.STRING)
    private VitalCategory vitalCategory;

}

package com.nokuthaba.backend.dto;

import com.nokuthaba.backend.models.VitalSignType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DecisionSupportDto {
    private LocalDate localDate;
    private VitalSignType vitalSignType;
}

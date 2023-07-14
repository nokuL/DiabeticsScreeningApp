package com.nokuthaba.backend.repository;

import com.nokuthaba.backend.models.VitalSign;
import com.nokuthaba.backend.models.VitalSignType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface VitalsRepository extends JpaRepository<VitalSign, String> {
    List<VitalSign>findAllByVitalSignType(VitalSignType vitalSignType);
}

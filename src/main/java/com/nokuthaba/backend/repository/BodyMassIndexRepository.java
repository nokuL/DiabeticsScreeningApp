package com.nokuthaba.backend.repository;

import com.nokuthaba.backend.models.BodyMassIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyMassIndexRepository extends JpaRepository<BodyMassIndex, String> {
}

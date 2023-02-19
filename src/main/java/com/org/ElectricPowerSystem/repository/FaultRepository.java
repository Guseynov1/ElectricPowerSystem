package com.org.ElectricPowerSystem.repository;

import com.org.ElectricPowerSystem.model.entity.Fault;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaultRepository extends JpaRepository<Fault, String> { }

package com.org.ElectricPowerSystem.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@Entity
@Table(name = "fault")
@AllArgsConstructor
@NoArgsConstructor
public class Fault implements Serializable {
    @Id
    @Column(name = "value_name", nullable = false)
    private String valueName;

    @Column(name = "time")
    private float time;

    @Column(name = "time_unit_of_measurement")
    private String timeUnitOfMeasurement;

    @Column(name = "fallback_value")
    private float fallbackValue;

    @Column(name = "value_unit_of_measurement")
    private String valueUnitOfMeasurement;

}



package com.org.ElectricPowerSystem.model.dto;

import com.org.ElectricPowerSystem.model.entity.Fault;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FaultDTO {

    private String valueName, timeUnitOfMeasurement, valueUnitOfMeasurement;
    private float time;
    private float fallbackValue;

    public FaultDTO(Fault fault){
        this.valueName = fault.getValueName();
        this.time = fault.getTime();
        this.timeUnitOfMeasurement = fault.getTimeUnitOfMeasurement();
        this.fallbackValue = fault.getFallbackValue();
        this.valueUnitOfMeasurement = fault.getValueUnitOfMeasurement();
    }
}

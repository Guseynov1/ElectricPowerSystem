package com.org.ElectricPowerSystem.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class DataDTO {

    private String name;
    private String type;
    private List<?> values;
    private boolean clicked = false;
    private List<Float> RMS;

}

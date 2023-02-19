package com.org.ElectricPowerSystem.service;

import com.org.ElectricPowerSystem.model.dto.DataDTO;
import com.org.ElectricPowerSystem.model.dto.FaultDTO;

import java.util.List;

public interface ComtradeReaderService {

    void read(String path, String name);
    void save();
    void checkTrigger();
    List<FaultDTO> getFault();
    List<DataDTO> getData();

}

package com.org.ElectricPowerSystem.service;

import com.org.ElectricPowerSystem.logic.ComtradeReader;
import com.org.ElectricPowerSystem.logic.Fourier;
import com.org.ElectricPowerSystem.logic.Trigger;
import com.org.ElectricPowerSystem.model.dto.DataDTO;
import com.org.ElectricPowerSystem.model.dto.FaultDTO;
import com.org.ElectricPowerSystem.model.entity.Fault;
import com.org.ElectricPowerSystem.repository.FaultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class ComtradeReaderServiceImpl implements ComtradeReaderService {

    @Autowired
    private FaultRepository faultRepository;

    @Value("${service.set-parameter}")
    private float setParameter;
    private List<List<Float>> value;
    private LinkedList<List<Float>> valueRMS;
    private String[] valueName, unitOfMeasurement;
    private Integer[] time;
    private List<Integer> timeOfTrigger = new ArrayList<>();
    private List<Float> fallbackValue = new ArrayList<>();
    private List<String> triggeredValueName = new ArrayList<>(), triggeredUnitOfMeasurement = new ArrayList<>();

    @Override
    public void read(String path, String name) {
        ComtradeReader comtradeReader = new ComtradeReader(path, name);
        value = comtradeReader.read();
        valueName = comtradeReader.getValName();
        time = comtradeReader.getTime().toArray(new Integer[comtradeReader.getTime().size()]);
        unitOfMeasurement = comtradeReader.getUnitOfMeasurement();
        valueRMS = new LinkedList<>();
        for (List<Float> array : value) {
            valueRMS.add(new ArrayList<>());
            Fourier fourier = new Fourier();
            for (Float element : array) {
                valueRMS.getLast().add(fourier.getRMS(element));
            }
        }
    }

    @Override
    @Transactional
    public void save() {
        IntStream.range(0, triggeredValueName.size()).forEach(i -> {
            Fault fault = new Fault(triggeredValueName.get(i), timeOfTrigger.get(i) / 1000,
                    "ms", fallbackValue.get(i), triggeredUnitOfMeasurement.get(i));
            try {
                faultRepository.save(fault);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void checkTrigger() {
        Trigger trigger = new Trigger(setParameter);
        trigger.checkTrigger(valueRMS, time, valueName, unitOfMeasurement);
        timeOfTrigger = trigger.getTimeOfTrigger();
        fallbackValue = trigger.getFallbackValue();
        triggeredValueName = trigger.getTriggeredValueName();
        triggeredUnitOfMeasurement = trigger.getTriggeredUnitOfMeasurement();
    }

    @Override
    public List<FaultDTO> getFault() {
        List<FaultDTO> faultDTOList = new ArrayList<>();
        List<Fault> faults = faultRepository.findAll();
        for (Fault fault : faults) {
            FaultDTO faultDTO = new FaultDTO(fault);
            faultDTOList.add(faultDTO);
        }
        return faultDTOList;
    }

    public List<DataDTO> getData() {
        List<DataDTO> dataList = new ArrayList<>();
        int i = 0;
        for (String name : valueName) {
            DataDTO data = new DataDTO();
            data.setName(name);
            data.setValues(value.get(i));
            data.setType("analog");
            data.setRMS(valueRMS.get(i));
            dataList.add(data);
            i++;
        }
        return dataList;
    }
}

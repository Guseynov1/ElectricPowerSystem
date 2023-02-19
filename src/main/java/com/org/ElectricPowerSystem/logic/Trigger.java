package com.org.ElectricPowerSystem.logic;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data
public class Trigger {

    private List<Integer> timeOfTrigger = new ArrayList<>();
    private List<Float> fallbackValue = new ArrayList<>();
    private List<String> triggeredValueName = new ArrayList<>(), triggeredUnitOfMeasurement = new ArrayList<>();
    private float setParameter;

    public Trigger(float setParameter) {
        this.setParameter = setParameter;
    }

    public void checkTrigger(LinkedList<List<Float>> value, Integer[] time, String[] valueName, String[] unitOfMeasurement) {
        int j = 0;
        for (List<Float> array : value) {
            int i = 0;
            List<Integer> tempTime = new ArrayList<>();
            List<Float> tempVal = new ArrayList<>();
            boolean trigger = false;
            for (float element : array) {
                if (j < 3 && element > setParameter) {
                    tempTime.add(time[i]);
                    tempVal.add(element);
                    trigger = true;
                }
                i++;
            }
            if (trigger) {
                triggeredValueName.add(valueName[j]);
                triggeredUnitOfMeasurement.add(unitOfMeasurement[j]);
                if (tempVal.size() != 0 && tempVal != null) {
                    fallbackValue.add(tempVal.get((int) tempVal.size() / 2));
                }
                if (tempTime.size() != 0 && tempTime != null) {
                    timeOfTrigger.add(tempTime.get(0));
                }
            }
            j++;
        }
    }
}

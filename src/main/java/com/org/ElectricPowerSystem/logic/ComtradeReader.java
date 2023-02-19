package com.org.ElectricPowerSystem.logic;

import lombok.Data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class ComtradeReader {

    private String line;
    private File dataCfg, dataDat;
    private float[] k1 = new float[18], k2 = new float[18];
    private String[] valName = new String[18], unitOfMeasurement = new String[18];
    private List<Integer> time = new ArrayList<>();

    public ComtradeReader(String path, String name) {
        if (path == null || path.isEmpty() || name == null || name.isEmpty()) {
            path = "src/main/resources/comtrade/";
            name = "Number start = 690 Test = 4.1.2.1.1 Time = 07_19_2022 13_50_13.811 RTDS";
        }
        dataCfg = new File(path + name + ".cfg");
        dataDat = new File(path + name + ".dat");
    }

    public List<List<Float>> read() {
        BufferedReader bufReadCfg = null;
        BufferedReader bufReadDat = null;
        try {
            bufReadCfg = new BufferedReader(new FileReader(dataCfg));
            bufReadDat = new BufferedReader(new FileReader(dataDat));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int number = 0;
        String[] parse;
        try {
            while ((line = bufReadCfg.readLine()) != null) {
                if (number >= 2 && number <= 19) {
                    parse = line.split(",");
                    unitOfMeasurement[number - 2] = parse[4];
                    k1[number - 2] = Float.parseFloat(parse[5]);
                    k2[number - 2] = Float.parseFloat(parse[6]);
                    valName[number - 2] = parse[1];
                }
                number++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<List<Float>> value = new ArrayList<>();
        for (int i = 0; i < k1.length; i++) {
            value.add(new ArrayList<>());
        }

        try {
            while ((line = bufReadDat.readLine()) != null) {
                parse = line.split(",");
                int i = 0;
                for (String el : parse) {
                    if (i >= 2 && i <= 19) {
                        value.get(i - 2).add(Float.parseFloat(el) * k1[i - 2] + k2[i - 2]);
                    } else if (i == 1) time.add(Integer.parseInt(el));
                    i++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
}

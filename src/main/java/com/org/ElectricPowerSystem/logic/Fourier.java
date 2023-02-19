package com.org.ElectricPowerSystem.logic;

public class Fourier {

    private static final int size = 400;

    private final float[] sin = new float[size], cos = new float[size], bufY = new float[size], bufX = new float[size];
    private float sumX = 0, sumY = 0, ortX = 0, ortY = 0;
    private static final float k = (float) (Math.sqrt(2) / size);
    private int number = 0;

    public Fourier() {
        for (int i = 0; i < size; i++) {
            sin[i] = (float) Math.sin(2 * Math.PI * i / size);
            cos[i] = (float) Math.cos(2 * Math.PI * i / size);
        }
    }

    private float[] get0rt(float meanValue) {

        float tempValue = meanValue * sin[number];
        sumX = sumX + tempValue - bufX[number];
        bufX[number] = tempValue;

        tempValue = meanValue * cos[number];
        sumY = sumY + tempValue - bufY[number];
        bufY[number] = tempValue;

        ortX = k * sumX;
        ortY = k * sumY;

        number++;
        if (number == size) number = 0;

        return new float[]{ortX, ortY};
    }

    public float getRMS(float meanValue) {
        get0rt(meanValue);
        return (float) Math.sqrt(Math.pow(ortX, 2) + Math.pow(ortY, 2));
    }

    public float getAngle(float meanValue) {
        get0rt(meanValue);
        return (float) Math.atan2(ortY, ortX);
    }

}

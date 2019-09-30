package com.lalaalal.droni.server.airfield;

public class AirFieldHandler {
    public static final int CHANNEL_NUM = 8;
    public static final int BAND_NUM = 5;

    private String fieldName;
    private boolean[][] fpvTable;
    private int djiDrone;

    public AirFieldHandler(String name) {
        fpvTable = new boolean[BAND_NUM][CHANNEL_NUM];
        djiDrone = 0;
        fieldName = name;
    }

    boolean setDjiDrone(int value) {
        if (djiDrone + value < 3 && djiDrone + value >= 0) {
            djiDrone += value;
            return true;
        } else {
            return false;
        }
    }

    boolean getStatusAt(int band, int channel)  {
        if (!checkRange(band, channel))
            throw new IndexOutOfBoundsException();

        return fpvTable[band][channel];
    }

    String getFieldName() {
        return fieldName;
    }

    public boolean setStatusAt(int band, int channel, boolean value) throws IndexOutOfBoundsException {
        if (!checkRange(band, channel))
            throw new IndexOutOfBoundsException();
        if (fpvTable[band][channel] == value)
            return false;

        fpvTable[band][channel] = value;
        return true;
    }

    int getDjiDrone() {
        return djiDrone;
    }

    private boolean checkRange(int band, int channel) {
        return (band >= 0 && band < BAND_NUM) && (channel >= 0 && channel < CHANNEL_NUM);
    }
}

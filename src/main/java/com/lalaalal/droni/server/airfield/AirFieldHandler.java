package com.lalaalal.droni.server.airfield;

public class AirFieldHandler {
    public static final int CHANNEL_NUM = 8;
    public static final int BAND_NUM = 5;

    private String fieldName;
    private boolean[][] fpvTable;

    public AirFieldHandler(String name) {
        fpvTable = new boolean[BAND_NUM][CHANNEL_NUM];
        fieldName = name;
    }

    public boolean getStatusAt(int band, int channel)  {
        if (!checkRange(band, channel))
            throw new IndexOutOfBoundsException();

        return fpvTable[band][channel];
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setStatusAt(int band, int channel, boolean value) throws IndexOutOfBoundsException {
        if (!checkRange(band, channel))
            throw new IndexOutOfBoundsException();

        fpvTable[band][channel] = value;
    }

    private boolean checkRange(int band, int channel) {
        return (band >= 0 && band < BAND_NUM) && (channel >= 0 && channel < CHANNEL_NUM);
    }
}

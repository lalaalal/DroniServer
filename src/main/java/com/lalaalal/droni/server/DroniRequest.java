package com.lalaalal.droni.server;

import java.util.ArrayList;
import java.util.Arrays;

public class DroniRequest {
    public static final int TYPE_TEXT = 1;
    public static final int TYPE_FILE = 2;

    public String type;
    public String command;
    public ArrayList<String> stringData;
    public byte[] data;

    public DroniRequest(String type, String command, ArrayList<String> data) {
        this.command = command;
        this.stringData = data;
    }

    void printDroniRequest() {
        System.out.println("Command : " + command);
        for (int i = 0; i < stringData.size(); i++)
            System.out.println("Data[" + i + "] : " + stringData.get(i));
        System.out.println();
    }
}
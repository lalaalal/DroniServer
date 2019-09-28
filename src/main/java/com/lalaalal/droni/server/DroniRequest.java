package com.lalaalal.droni.server;

import java.util.ArrayList;
import java.util.Arrays;

public class DroniRequest {
    public String command;
    public ArrayList<String> data;

    public DroniRequest(ArrayList<String> data) {
        command = data.get(0);
        data.remove(0);
        this.data = data;
    }

    public DroniRequest(String command, ArrayList<String> data) {
        this.command = command;
        this.data = data;
    }

    public static DroniRequest parseDroniRequest(String request) {
        String[] requestData = request.split(":");
        ArrayList<String> data = new ArrayList<>(Arrays.asList(requestData));

        return new DroniRequest(data);
    }

    public void printDroniRequest() {
        System.out.println("Command : " + command);
        for (int i = 0; i < data.size(); i++)
            System.out.println("Data[" + i + "] : " + data.get(i));
        System.out.println();
    }
}
package com.lalaalal.droni.server.airfield;

import com.lalaalal.droni.server.DroniRequest;
import com.lalaalal.droni.server.Respondent;

import java.io.PrintWriter;
import java.util.ArrayList;

public class AirFieldRespondent implements Respondent {
    private PrintWriter out;

    private static final int COMMAND_INDEX = 0;
    private static final int AIRFIELD_NAME_INDEX = 1;
    private static final int BAND_INDEX = 2;
    private static final int CHANNEL_INDEX = 3;

    private String command;
    private String airFieldName;
    private int band;
    private int channel;
    private AirFieldHandler airFieldHandler;

    public AirFieldRespondent(PrintWriter out, DroniRequest request, ArrayList<AirFieldHandler> airFieldHandlers) {
        this.out = out;

        command = request.stringData.get(COMMAND_INDEX);

        if (command.equals("SET_USE") || command.equals("SET_NOT_USE")) {
            band = Integer.parseInt(request.stringData.get(BAND_INDEX));
            channel = Integer.parseInt(request.stringData.get(CHANNEL_INDEX));
        }
        airFieldName = request.stringData.get(AIRFIELD_NAME_INDEX);
        airFieldHandler = findAirFieldHandlerByName(airFieldHandlers);
    }

    @Override
    public void Response() {
        out.println("TEXT");
        if (command.equals("SET_USE")) {
            airFieldHandler.setStatusAt(band, channel, true);
            out.println("SUCCEED!");
        } else if (command.equals("SET_NOT_USE")) {
            airFieldHandler.setStatusAt(band, channel, false);
            out.println("SUCCEED!");
        } else if (command.equals("GET")) {
            for (int i = 0; i < AirFieldHandler.BAND_NUM; i++) {
                for (int j = 0; j < AirFieldHandler.CHANNEL_NUM; j++) {
                    out.print(airFieldHandler.getStatusAt(i, j));
                    out.print(":");
                }
            }
            out.println();
        } else {
            out.println("FAILED!");
        }
    }

    private AirFieldHandler findAirFieldHandlerByName(ArrayList<AirFieldHandler> airFieldHandlers) {
        for (int i = 0; i < airFieldHandlers.size(); i++) {
            AirFieldHandler airField = airFieldHandlers.get(i);
            String airFieldName = airField.getFieldName();
            if (airFieldName.equals(this.airFieldName))
                return airField;
        }
        return null;
    }
}

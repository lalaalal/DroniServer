package com.lalaalal.droni.server;

import com.lalaalal.droni.server.account.LoginRespondent;
import com.lalaalal.droni.server.account.SignUpRespondent;
import com.lalaalal.droni.server.account.UserDataRespondent;
import com.lalaalal.droni.server.airfield.AirFieldHandler;
import com.lalaalal.droni.server.airfield.AirFieldRespondent;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DroniServer implements Runnable {
    private static final int SERVER_PORT = 51101;

    private Socket client;
    private Scanner in;
    private PrintWriter out;
    private InputStream inputStream;
    private OutputStream outputStream;

    static ArrayList<AirFieldHandler> airFieldHandlers;

    private DroniServer(Socket client) throws IOException {
        this.client = client;

        in = new Scanner(client.getInputStream());
        out = new PrintWriter(client.getOutputStream(), true);
        inputStream = client.getInputStream();
        outputStream = client.getOutputStream();
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        ExecutorService pool = Executors.newFixedThreadPool(500);

        airFieldHandlers = new ArrayList<>();

        AirFieldHandler gwang = new AirFieldHandler("광나루");
        gwang.setStatusAt(4, 1, true);
        gwang.setStatusAt(4, 3, true);
        gwang.setStatusAt(4, 4, true);
        gwang.setStatusAt(4, 7, true);

        airFieldHandlers.add(gwang);
        airFieldHandlers.add(new AirFieldHandler("신정"));


        while(true) {
            pool.execute(new DroniServer(serverSocket.accept()));
        }
    }

    @Override
    public void run() {
        try {
            DroniRequest request = parseDroniRequest();
            request.printDroniRequest();

            Respondent respondent = findSuitableRespondent(request);
            respondent.Response();
        } catch (WrongRequestException e) {
            System.out.println(e.getMessage());
        } finally {
            try { client.close(); } catch (IOException e) { e.printStackTrace(); }
        }
    }

    private Respondent findSuitableRespondent(DroniRequest request) throws WrongRequestException {
        if (request.command.equals("LOGIN")) {
            return new LoginRespondent(out, request);
        } else if (request.command.equals("SIGN_UP")) {
            return new SignUpRespondent(out, request);
        } else if (request.command.equals("USER_DATA")) {
            return new UserDataRespondent(out, request);
        } else if (request.command.equals("AIRFIELD")) {
            return new AirFieldRespondent(out, request, airFieldHandlers);
        }
        else {
            throw new WrongRequestException(request.command);
        }
    }

    // TODO: File receive
    private DroniRequest parseDroniRequest()  {
        String type = in.nextLine();
        String command = in.nextLine();
        String request = in.nextLine();
        String[] requestData = request.split(":");
        ArrayList<String> data = new ArrayList<>(Arrays.asList(requestData));

        return new DroniRequest(type, command, data);
    }
}
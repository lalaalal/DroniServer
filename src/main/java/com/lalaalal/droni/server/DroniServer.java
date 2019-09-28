package com.lalaalal.droni.server;

import com.lalaalal.droni.server.account.LoginRespondent;
import com.lalaalal.droni.server.account.SignUpRespondent;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DroniServer implements Runnable {
    private static final int SERVER_PORT = 5110;

    private Socket client;
    private Scanner in;
    private PrintWriter out;

    private DroniServer(Socket client) throws IOException {
        this.client = client;

        in = new Scanner(client.getInputStream());
        out = new PrintWriter(client.getOutputStream(), true);
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);

        ExecutorService pool = Executors.newFixedThreadPool(500);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            pool.execute(new DroniServer(serverSocket.accept()));
        }
    }

    @Override
    public void run() {
        try {
            DroniRequest request = DroniRequest.parseDroniRequest(in.nextLine());
            request.printDroniRequest();

            Respondent respondent = findSuitableRespondent(request);
            respondent.Response();
        } catch (UnknownRequestException e) {
            System.out.println(e.getMessage());
        } finally {
            try { client.close(); } catch (IOException e) { e.printStackTrace(); }
        }
    }

    private Respondent findSuitableRespondent(DroniRequest request) throws UnknownRequestException {
        if (request.command.equals("LOGIN")) {
            return new LoginRespondent(out, request);
        } else if (request.command.equals("SIGN_UP")) {
            return new SignUpRespondent(out, request);
        } else {
            throw new UnknownRequestException(request.command);
        }
    }
}
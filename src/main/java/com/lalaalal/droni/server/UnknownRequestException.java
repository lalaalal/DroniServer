package com.lalaalal.droni.server;

public class UnknownRequestException extends Exception {
    private String command;

    public UnknownRequestException(String command) {
        this.command = command;
    }

    @Override
    public String getMessage() {
        return "UnknownRequest : " + command;
    }
}

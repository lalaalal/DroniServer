package com.lalaalal.droni.server;

public class WrongRequestException extends Exception {
    private String command;

    public WrongRequestException(String command) {
        this.command = command;
    }

    @Override
    public String getMessage() {
        return "WrongRequest : " + command;
    }
}

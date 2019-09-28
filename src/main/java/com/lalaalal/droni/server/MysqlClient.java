package com.lalaalal.droni.server;

import java.sql.*;

public class MysqlClient {
    private static final String DBName = "droni";
    private static final String TARGET_URL = "jdbc:mysql://mysql.isdj.ml/" + DBName;
    private static final String USER = "droni";
    private static final String PASSWORD = "HelloDrone!5110";

    private Statement statement;

    public MysqlClient() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = DriverManager.getConnection(TARGET_URL, USER, PASSWORD);
        statement = connection.createStatement();
    }

    public ResultSet select(String query) throws Exception {
        if (!query.contains("SELECT"))
            throw new Exception();
        return statement.executeQuery(query);
    }

    public int update(String query) throws Exception {
        if (!query.contains("UPDATE") && !query.contains("INSERT") && !query.contains("DELETE"))
            throw new Exception();
        return statement.executeUpdate(query);
    }
}

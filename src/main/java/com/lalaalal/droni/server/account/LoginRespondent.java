package com.lalaalal.droni.server.account;

import com.lalaalal.droni.server.DroniRequest;
import com.lalaalal.droni.server.MysqlClient;
import com.lalaalal.droni.server.Respondent;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginRespondent implements Respondent {
    private static final int ID_INDEX = 0;
    private static final int PW_INDEX = 1;

    private String querySelectUserIdPw;

    private PrintWriter out;
    private String requestId;
    private String requestPw;

    public LoginRespondent(PrintWriter out, DroniRequest request) {
        this.out = out;

        requestId = request.data.get(ID_INDEX);
        requestPw = request.data.get(PW_INDEX);

        querySelectUserIdPw = "SELECT id, pw FROM user WHERE id = \"" + requestId + "\"";
    }

    @Override
    public void Response() {
        if (login()) {
            out.println("SUCCEED!");
        } else {
            out.println("FAILED!");
        }
    }

    public boolean login() {
        try {
            MysqlClient mysqlClient = new MysqlClient();
            ResultSet resultSet = mysqlClient.select(querySelectUserIdPw);

            return checkUserIdPw(resultSet);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkUserIdPw(ResultSet resultSet) throws SQLException {
        if (!resultSet.next())
            return false;
        String userPw = resultSet.getString(PW_INDEX + 1);
        return requestPw.equals(userPw);
    }
}

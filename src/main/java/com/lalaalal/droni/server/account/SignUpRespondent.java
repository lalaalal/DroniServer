package com.lalaalal.droni.server.account;

import com.lalaalal.droni.server.DroniRequest;
import com.lalaalal.droni.server.MysqlClient;
import com.lalaalal.droni.server.Respondent;
import com.lalaalal.droni.server.WrongRequestException;

import java.io.PrintWriter;

public class SignUpRespondent implements Respondent {
    private static final int DATA_SIZE = 2;

    private static final int ID_INDEX = 0;
    private static final int PW_INDEX = 1;

    private String querySelectUserId;
    private String queryInsertUser;

    private PrintWriter out;
    private String requestId;
    private String requestPw;

    public SignUpRespondent(PrintWriter out, DroniRequest request) throws WrongRequestException {
        this.out = out;

        if (request.stringData.size() != DATA_SIZE)
            throw new WrongRequestException(request.command);

        requestId = request.stringData.get(ID_INDEX);
        requestPw = request.stringData.get(PW_INDEX);

        querySelectUserId = "SELECT id FROM user WHERE id = \"" + requestId + "\"";
        queryInsertUser = "INSERT INTO user VALUES (\"" + requestId + "\", \"" + requestPw + "\")";
    }

    @Override
    public void Response() {
        if (signUp())
            out.println("SUCCEED!");
        else
            out.println("FAILED!");
    }

    private boolean signUp() {
        try {
            MysqlClient mysqlClient = new MysqlClient();
            int status = mysqlClient.update(queryInsertUser);

            return checkStatus(status);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkStatus(int status) {
        return status == 1;
    }
}

package com.lalaalal.droni.server.account;

import com.lalaalal.droni.server.DroniRequest;
import com.lalaalal.droni.server.MysqlClient;
import com.lalaalal.droni.server.Respondent;
import com.lalaalal.droni.server.WrongRequestException;

import java.io.PrintWriter;
import java.sql.ResultSet;

public class UserDataRespondent implements Respondent {
    private String userId;
    private String querySelectUserData;

    private PrintWriter out;

    public UserDataRespondent(PrintWriter out, DroniRequest request) throws WrongRequestException {
        if (request.stringData.size() != 1)
            throw new WrongRequestException(request.command);

        this.out = out;

        userId = request.stringData.get(0);
        querySelectUserData = "SELECT name, career, phone, drone FROM user WHERE id = \"" + userId + "\"";
    }

    @Override
    public void Response() {
        out.println("TEXT");
        out.println(getUserData());
    }

    private String getUserData() {
        try {
            MysqlClient mysqlClient = new MysqlClient();
            ResultSet resultSet = mysqlClient.select(querySelectUserData);

            if (!resultSet.next())
                return "NO SUCH ID";
            StringBuilder result = new StringBuilder();
            for (int i = 1; i <= 4; i++) {
                result.append(resultSet.getString(i));
                result.append(":");
            }

            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "FAILED";
        }
    }
}

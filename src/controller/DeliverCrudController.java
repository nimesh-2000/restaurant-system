package controller;

import model.Deliver;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeliverCrudController {
    public static ArrayList<String> getDeliverCodes() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT deliver_id FROM Deliver");
        ArrayList<String> codeList = new ArrayList<>();
        while (result.next()){
            codeList.add(result.getString(1));
        }
        return codeList;
    }

    public static Deliver getDeliver(String code) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Deliver WHERE deliver_id=?", code);
        if (result.next()){
            return new Deliver(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)
            );
        }
        return null;
    }
}

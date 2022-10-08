package controller;

import model.Customer;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerCrudController {
    public static ArrayList<String> getCustomerCodes() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT customer_id FROM Customer");
        ArrayList<String> codeList = new ArrayList<>();
        while (result.next()){
            codeList.add(result.getString(1));
        }
        return codeList;
    }

    public static Customer getCustomer(String code) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Customer WHERE customer_id=?", code);
        if (result.next()){
            return new Customer(
                    result.getString(1),
                    result.getString(5),
                    result.getString(4),
                    result.getString(3),
                    result.getString(2)

            );
        }
        return null;
    }
}

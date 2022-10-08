package controller;

import model.Deliver;
import model.Supplier;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierCrudController {
    public static ArrayList<String> getSupplierCodes() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT sup_id FROM Supplier");
        ArrayList<String> codeList = new ArrayList<>();
        while (result.next()){
            codeList.add(result.getString(1));
        }
        return codeList;
    }

    public static Supplier getSupplier(String code) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Supplier WHERE sup_id=?", code);
        if (result.next()){
            return new Supplier(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)
            );
        }
        return null;
    }
}

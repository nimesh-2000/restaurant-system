package controller;

import model.FoodItem;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FoodItemCrudController {
    public static ArrayList<String> getFoodItemCodes() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT food_id FROM FoodItem");
        ArrayList<String> codeList = new ArrayList<>();
        while (result.next()){
            codeList.add(result.getString(1));
        }
        return codeList;
    }

    public static FoodItem getFoodItem(String code) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM FoodItem WHERE food_id=?", code);
        if (result.next()){
            return new FoodItem(
                    result.getString(1),
                    result.getString(2),
                    result.getDouble(3),
                    result.getInt(4)
            );
        }
        return null;
    }
}

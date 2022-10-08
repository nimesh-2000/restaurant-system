package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Employee;
import model.FoodDetail;
import model.FoodItem;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.EmployeeTM;
import view.tm.FoodIngredientTM;
import view.tm.FoodItemTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class FoodItemFormController {
    public AnchorPane foodItemContext;
    public JFXTextField txtFoodId;
    public JFXTextField txtFoodName;
    public JFXTextField txtFoodPrice;
    public JFXTextField txtFoodDescription;
    public TableView<FoodItemTM> tblFoodItem;
    public TableColumn colFoodId;
    public TableColumn colFoodName;
    public TableColumn colFoodPrice;
    public TableColumn colFoodDescription;
    public TableColumn colFoodDelete;
    public JFXButton btnSaveFoodItem;
    public TableColumn colQtyOnHand;
    public JFXTextField txtQtyOnHand;
    public JFXComboBox cmbSelectIngredient;
    public TableView tblSelectIngredient;
    public TableColumn colIngredientId;
    public TableColumn colIngredientName;
    public JFXTextField txtIngredientQty;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    Pattern idPattern = Pattern.compile("^(F-)[0-9]{3,5}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,15}$");
    Pattern qty = Pattern.compile("^[0-9 ]{1,}$");
    Pattern price = Pattern.compile("^[1-9][0-9]*(.[0-9]{1,2})?$");

    public void initialize() throws SQLException, ClassNotFoundException {
        colFoodId.setCellValueFactory(new PropertyValueFactory<>("foodId"));
        colFoodName.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        colFoodPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colFoodDelete.setCellValueFactory(new PropertyValueFactory<>("foodBtn"));
        colIngredientId.setCellValueFactory(new PropertyValueFactory<>("ingredientId"));
        colIngredientName.setCellValueFactory(new PropertyValueFactory<>("ingredientName"));
        btnSaveFoodItem.setDisable(true);
        loadAllFoodItem();
        loadIngredientIds();
        validateCenter();
        tblFoodItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                try {
                    FoodItemTM selectedItem = tblFoodItem.getSelectionModel().getSelectedItem();
                    txtFoodId.setText(selectedItem.getFoodId());
                    txtFoodName.setText(selectedItem.getFoodName());
                    txtFoodPrice.setText(String.valueOf(selectedItem.getPrice()));
                    txtQtyOnHand.setText(String.valueOf(selectedItem.getQtyOnHand()));

                    search();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                btnSaveFoodItem.setText("Update");
                //setData((RoomRM) newValue);
            }
        });

    }

    private void validateCenter() {
        btnSaveFoodItem.setDisable(true);
        map.put(txtFoodId,idPattern);
        map.put(txtFoodName,namePattern);
        map.put(txtFoodPrice,price);
        map.put(txtQtyOnHand,qty);
    }

    public void foodIdOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void search() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM FoodItem WHERE food_id=?",txtFoodId.getText());
        if (result.next()) {
            txtFoodName.setText(result.getString(2));
            txtFoodPrice.setText(result.getString(3));
            txtQtyOnHand.setText(result.getString(4));
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
        }
    }


    public void foodItemSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<FoodDetail>ingradientTMS=new ArrayList<>();
        for (FoodIngredientTM temp:ingredient) {
            ingradientTMS.add(new FoodDetail(txtFoodId.getText(),temp.getIngredientId(),temp.getQty()));
        }
        if (btnSaveFoodItem.getText().equals("Update")){
            FoodItem c = new FoodItem(
                    txtFoodId.getText(),txtFoodName.getText(), Double.parseDouble(txtFoodPrice.getText()),Integer.parseInt(txtQtyOnHand.getText()));

            try{
                boolean isUpdated = CrudUtil.execute("UPDATE FoodItem SET food_name=? , price=? ,  qty=? WHERE food_id=?",c.getFoodName(),c.getPrice(),c.getQtyOnHand(),c.getFoodId());
                if (isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                    loadAllFoodItem();
                    btnSaveFoodItem.setText("Save");
                }else{
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }


            }catch (SQLException | ClassNotFoundException e){

            }
        }else {
            FoodItem c = new FoodItem(
                    txtFoodId.getText(),txtFoodName.getText(), Double.parseDouble(txtFoodPrice.getText()), Integer.parseInt(txtQtyOnHand.getText()),ingradientTMS);


            try {

                if (SaveItem(c)){

                new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
           }


            } catch ( Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

            clearAllTexts();
            loadAllFoodItem();
        }
    }

    private void clearAllTexts() {
        txtFoodId.clear();
        txtFoodName.clear();
        txtFoodPrice.clear();
        txtQtyOnHand.clear();
        txtFoodId.requestFocus();
    }

    private void loadAllFoodItem() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM FoodItem");
        ObservableList<FoodItemTM> obList = FXCollections.observableArrayList();


        while (result.next()) {
            Button btn1 = new Button("Delete");
            btn1.setOnAction(event -> {
                FoodItemTM selectedItem= tblFoodItem.getSelectionModel().getSelectedItem();
                txtFoodId.setText(selectedItem.getFoodId());


                deleteFoodItem();

                try {
                    loadAllFoodItem();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

            obList.add(
                    new FoodItemTM(
                            result.getString(1),
                            result.getString(2),
                            result.getDouble(3),
                            result.getInt(4),
                            btn1
                    )
            );

        }
        tblFoodItem.setItems(obList);
        clearAllTexts();
    }

    private void deleteFoodItem() {
        try{

            if(CrudUtil.execute("DELETE FROM FoodItem WHERE food_id=?",txtFoodId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                btnSaveFoodItem.setText("Save");
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }
    }

    public void foodItemSearchOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }
    ObservableList<FoodIngredientTM> ingredient=FXCollections.observableArrayList();
    public void ingredientAddOnAction(ActionEvent actionEvent) {

        tblSelectIngredient.refresh();
        btnSaveFoodItem.setDisable(false);

        String ing_Name=getIngredientName((String) cmbSelectIngredient.getValue());
        FoodIngredientTM foodIngredientTM=new FoodIngredientTM((String) cmbSelectIngredient.getValue(),ing_Name,Integer.parseInt(txtIngredientQty.getText()));

        ingredient.add(foodIngredientTM);
        tblSelectIngredient.setItems(ingredient);
    }

    private String getIngredientName(String id) {
        try {
            PreparedStatement stm= DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Ingredient WHERE ingredient_id=?");
            stm.setString(1,id);
            ResultSet rst= stm.executeQuery();

            if (rst.next()){
                return rst.getString(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    private void loadIngredientIds() throws SQLException, ClassNotFoundException {

        ResultSet result = CrudUtil.execute("SELECT ingredient_id FROM Ingredient");
        ArrayList<String> codeList = new ArrayList<>();
        while (result.next()){
            codeList.add(result.getString(1));
        }
        cmbSelectIngredient.getItems().addAll(codeList);

    }
    public boolean SaveItem(FoodItem item) {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.prepareStatement("INSERT INTO FoodItem VALUES (?,?,?,?)");
            stm.setObject(1, item.getFoodId());
            stm.setObject(2, item.getFoodName());
            stm.setObject(3, item.getPrice());
            stm.setObject(4, item.getQtyOnHand());


            if (stm.executeUpdate() > 0) {

                if (saveFoodDetail(item.getFoodId(),item.getFoodDetails())) {
                    con.commit();
                    return true;
                }else {
                    con.rollback();
                    return false;
                }
            }else {
                con.rollback();
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    private boolean saveFoodDetail(String foodId, ArrayList<FoodDetail> foodDetails) throws SQLException, ClassNotFoundException {
        for (FoodDetail temp:foodDetails) {
            if (CrudUtil.execute("INSERT INTO Food_detail VALUES(?,?,?)",temp.getIngredientId(),foodId,temp.getQty())){

            }else {
                return false;
            }
        }
        return true;
    }

    public void textField_key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnSaveFoodItem);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }
    }
}


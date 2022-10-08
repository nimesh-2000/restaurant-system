package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Deliver;
import model.FoodItem;
import model.Ingredient;
import model.Supplier;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.FoodItemTM;
import view.tm.IngredientTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class IngredientFormController {
    public AnchorPane ingredientContext;
    public JFXTextField txtIngredientId;
    public JFXTextField txtIngredientName;
    public JFXTextField txtIngredientQty;
    public JFXTextField txtIngredientPrice;
    public TableView<IngredientTM> tblIngredient;
    public TableColumn colIngredientId;
    public TableColumn colIngredientName;
    public TableColumn colIngredientQty;
    public TableColumn colIngredientPrice;
    public TableColumn colIngredientDelete;
    public JFXButton btnSaveIngredient;
    public TableColumn colSupplierId;
    public JFXComboBox cmbSupplierId;
    public JFXTextField txtSupplierName;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    Pattern idPattern = Pattern.compile("^(C-)[0-9]{3,5}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,15}$");
    Pattern qty = Pattern.compile("^[0-9 ]{1,}$");
    Pattern price = Pattern.compile("^[1-9][0-9]*(.[0-9]{1,2})?$");


    public void initialize() throws SQLException, ClassNotFoundException {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colIngredientId.setCellValueFactory(new PropertyValueFactory<>("ingredientId"));
        colIngredientName.setCellValueFactory(new PropertyValueFactory<>("ingredientName"));
        colIngredientQty.setCellValueFactory(new PropertyValueFactory<>("ingredientQty"));
        colIngredientPrice.setCellValueFactory(new PropertyValueFactory<>("ingredientPrice"));
        colIngredientDelete.setCellValueFactory(new PropertyValueFactory<>("ingredientBtn"));
        loadAllIngredient();
        setSupplierCodes();
        validateCenter();
        btnSaveIngredient.setDisable(true);
        tblIngredient.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    IngredientTM selectedItem = tblIngredient.getSelectionModel().getSelectedItem();
                    txtIngredientId.setText(selectedItem.getIngredientId());
                    txtIngredientName.setText(selectedItem.getIngredientName());
                    txtIngredientQty.setText(String.valueOf(selectedItem.getIngredientQty()));
                    txtIngredientPrice.setText(String.valueOf(selectedItem.getIngredientPrice()));

                    search();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                btnSaveIngredient.setText("Update");
                //setData((RoomRM) newValue);
            }
        });
        cmbSupplierId.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setSupplierData((String) newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void validateCenter() {
        btnSaveIngredient.setDisable(true);
        map.put(txtIngredientId,idPattern);
        map.put(txtIngredientName,namePattern);
        map.put(txtIngredientPrice,price);
        map.put(txtIngredientQty,qty);
    }

    private void setSupplierData(String supplierId) throws SQLException, ClassNotFoundException {
        Supplier supplier = new SupplierCrudController().getSupplier(supplierId);
        if (supplier==null){
            new Alert(Alert.AlertType.WARNING,"Empty Result Set").show();
        }else {
            txtSupplierName.setText(supplier.getSupName());
        }
    }
    private void setSupplierCodes(){
        try {
            List<String> supplierIds = SupplierCrudController.getSupplierCodes();
            cmbSupplierId.getItems().addAll(supplierIds);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void ingredientIdOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void search() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Ingredient WHERE ingredient_id=?",txtIngredientId.getText());
        if (result.next()) {
            txtIngredientName.setText(result.getString(2));
            txtIngredientQty.setText(result.getString(3));
            txtIngredientPrice.setText(result.getString(4));
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
        }
    }

    public void ingredientSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (btnSaveIngredient.getText().equals("Update")){
            Ingredient c = new Ingredient(
                    (String)cmbSupplierId.getValue() , txtIngredientId.getText(),txtIngredientName.getText(),Double.parseDouble(txtIngredientPrice.getText()), Integer.parseInt(txtIngredientQty.getText()));

            try{
                boolean isUpdated = CrudUtil.execute("UPDATE Ingredient SET ingredient_name=? , qty=? ,  price=?, sup_id=? WHERE ingredient_id=?",c.getIngredientName(),c.getQty(),c.getPrice(),c.getSupId(),c.getIngredientId());
                if (isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                    loadAllIngredient();
                    btnSaveIngredient.setText("Save");
                }else{
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }


            }catch (SQLException | ClassNotFoundException e){

            }
        }else {
            Ingredient c = new Ingredient(
                    (String)cmbSupplierId.getValue(), txtIngredientId.getText(),txtIngredientName.getText(),Double.parseDouble(txtIngredientPrice.getText()),Integer.parseInt(txtIngredientQty.getText())
            );

            try {
                if (CrudUtil.execute("INSERT INTO Ingredient VALUES (?,?,?,?,?)",c.getIngredientId(),c.getIngredientName(),c.getQty(),c.getPrice(),c.getSupId())){
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

            clearAllTexts();
            loadAllIngredient();
        }
    }

    private void clearAllTexts() {
        txtIngredientId.clear();
        txtIngredientName.clear();
        txtIngredientQty.clear();
        txtIngredientPrice.clear();
        txtIngredientId.requestFocus();
        setBorders(txtIngredientId,txtIngredientName,txtIngredientPrice,txtIngredientQty);
    }
    public void setBorders(JFXTextField... textFields){
        for (JFXTextField textField : textFields) {
            textField.getParent().setStyle("-fx-border-color: rgba(76, 73, 73, 0.83)");
        }
    }

    private void loadAllIngredient() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Ingredient");
        ObservableList<IngredientTM> obList = FXCollections.observableArrayList();


        while (result.next()) {
            Button btn1 = new Button("Delete");
            btn1.setOnAction(event -> {
                IngredientTM selectedItem= tblIngredient.getSelectionModel().getSelectedItem();
                txtIngredientId.setText(selectedItem.getIngredientId());


                deleteIngredient();

                try {
                    loadAllIngredient();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

            obList.add(
                    new IngredientTM(
                            result.getString(5),
                            result.getString(1),
                            result.getString(2),
                            result.getInt(3),
                            result.getDouble(4),
                            btn1
                    )
            );

        }
        tblIngredient.setItems(obList);
        clearAllTexts();
    }

    private void deleteIngredient() {
        try{

            if(CrudUtil.execute("DELETE FROM Ingredient WHERE ingredient_id=?",txtIngredientId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                btnSaveIngredient.setText("Save");
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }
    }

    public void ingredientSearchOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    public void textField_key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnSaveIngredient);

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


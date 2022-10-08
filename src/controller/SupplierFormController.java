package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Employee;
import model.Supplier;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.EmployeeTM;
import view.tm.SupplierTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class SupplierFormController {
    public AnchorPane supplierContext;
    public JFXTextField txtSupplierId;
    public JFXTextField txtSupplierName;
    public JFXTextField txtSupplierAddress;
    public TableView <SupplierTM>tblSupplier;
    public TableColumn colSupId;
    public TableColumn colSupName;
    public TableColumn colSupAddress;
    public TableColumn colSupContact;
    public TableColumn colSupDelete;
    public JFXButton btnSaveSupplier;
    public JFXTextField txtSupplierContact;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    Pattern idPattern = Pattern.compile("^(S-)[0-9]{3,5}$");
    Pattern namePattern = Pattern.compile("^([A-Z][a-z]*((\\s)))+[A-Z][a-z]*$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,20}$");
    Pattern contactPattern = Pattern.compile("^07(7|6|8|1|2|5|0|4)-[0-9]{7}$");

    public void initialize() throws SQLException, ClassNotFoundException {
        colSupId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colSupName.setCellValueFactory(new PropertyValueFactory<>("supName"));
        colSupAddress.setCellValueFactory(new PropertyValueFactory<>("supAddress"));
        colSupContact.setCellValueFactory(new PropertyValueFactory<>("supContact"));
        colSupDelete.setCellValueFactory(new PropertyValueFactory<>("supplierBtn"));
        loadAllSupplier();
        validateCenter();
        btnSaveSupplier.setDisable(true);
        tblSupplier.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                try {
                    SupplierTM selectedItem = tblSupplier.getSelectionModel().getSelectedItem();
                    txtSupplierId.setText(selectedItem.getSupId());
                    txtSupplierName.setText(selectedItem.getSupName());
                    txtSupplierAddress.setText(String.valueOf(selectedItem.getSupAddress()));
                    txtSupplierContact.setText(selectedItem.getSupContact());

                    search();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                btnSaveSupplier.setText("Update");
                //setData((RoomRM) newValue);
            }
        });

    }

    private void validateCenter() {
        btnSaveSupplier.setDisable(true);
        map.put(txtSupplierId,idPattern);
        map.put(txtSupplierName,namePattern);
        map.put(txtSupplierAddress,addressPattern);
        map.put(txtSupplierContact,contactPattern);
    }


    public void supIdOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveSupplierOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (btnSaveSupplier.getText().equals("Update")){
            Supplier c = new Supplier(
                    txtSupplierId.getText(),txtSupplierName.getText(),txtSupplierAddress.getText(),txtSupplierContact.getText());

            try{
                boolean isUpdated = CrudUtil.execute("UPDATE Supplier SET sup_name=? , sup_address=? , sup_contact=? WHERE sup_id=?",c.getSupName(),c.getSupAddress(),c.getSupContact(),c.getSupId());
                if (isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                    loadAllSupplier();
                    btnSaveSupplier.setText("Save");
                }else{
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }


            }catch (SQLException | ClassNotFoundException e){

            }
        }else {
            Supplier c = new Supplier(
                    txtSupplierId.getText(),txtSupplierName.getText(), txtSupplierAddress.getText(), txtSupplierContact.getText()
            );

            try {
                if (CrudUtil.execute("INSERT INTO Supplier VALUES (?,?,?,?)",c.getSupId(),c.getSupName(),c.getSupAddress(),c.getSupContact())){
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

            clearAllTexts();
            loadAllSupplier();
        }
    }
    private void loadAllSupplier() throws SQLException, ClassNotFoundException {

        ResultSet result = CrudUtil.execute("SELECT * FROM Supplier");
        ObservableList<SupplierTM> obList = FXCollections.observableArrayList();


        while (result.next()) {
            Button btn1 = new Button("Delete");
            btn1.setOnAction(event -> {
                SupplierTM selectedItem= tblSupplier.getSelectionModel().getSelectedItem();
                txtSupplierId.setText(selectedItem.getSupId());


                deleteSupplier();

                try {
                    loadAllSupplier();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

            obList.add(
                    new SupplierTM(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            btn1
                    )
            );

        }
        tblSupplier.setItems(obList);
        clearAllTexts();
    }
    private void deleteSupplier() {
        try{

            if(CrudUtil.execute("DELETE FROM Supplier WHERE sup_id=?",txtSupplierId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                btnSaveSupplier.setText("Save");
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }
    }
    private void clearAllTexts() {
        txtSupplierId.clear();
        txtSupplierName.clear();
        txtSupplierAddress.clear();
        txtSupplierContact.clear();
        txtSupplierId.requestFocus();
        setBorders(txtSupplierId,txtSupplierName,txtSupplierAddress,txtSupplierContact);
    }
    public void setBorders(JFXTextField... textFields){
        for (JFXTextField textField : textFields) {
            textField.getParent().setStyle("-fx-border-color: rgba(76, 73, 73, 0.83)");
        }
    }


    public void searchSupplierOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }
    private void search() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Supplier WHERE sup_id=?",txtSupplierId.getText());
        if (result.next()) {
            txtSupplierName.setText(result.getString(2));
            txtSupplierAddress.setText(result.getString(3));
            txtSupplierContact.setText(result.getString(4));
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
        }
    }

    public void textField_key_Released(KeyEvent keyEvent) {

        Object response = ValidationUtil.validate(map,btnSaveSupplier);

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

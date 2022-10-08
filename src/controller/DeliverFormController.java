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
import model.Employee;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.DeliverTM;
import view.tm.EmployeeTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class DeliverFormController {
    public AnchorPane deliverContext;
    public JFXTextField txtDeliverId;
    public JFXTextField txtDriverName;
    public JFXTextField txtDeliverContactNo;
    public TableView<DeliverTM> tblDeliver;
    public TableColumn colDeliverId;
    public TableColumn colDriverName;
    public TableColumn colVehicleType;
    public TableColumn colDeliverDelete;
    public JFXComboBox cmbVehicleType;
    public JFXButton btnSaveDeliver;
    public TableColumn colDriverContact;
    public TableColumn colDeliverDelete1;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    Pattern idPattern = Pattern.compile("^(D-)[0-9]{3,5}$");
    Pattern namePattern = Pattern.compile("^([A-Z][a-z]*((\\s)))+[A-Z][a-z]*$");
    Pattern contactPattern = Pattern.compile("^07(7|6|8|1|2|5|0|4)-[0-9]{7}$");

    public void initialize() throws SQLException, ClassNotFoundException {

        ObservableList<String> obl = FXCollections.observableArrayList();

        obl.add ("Bike");
        obl.add("Van");

        cmbVehicleType.setItems (obl);

        //cmbGender.getItems().addAll("Male","Female");
        //cmbGender.getItems().addAll("Female");

        colDeliverId.setCellValueFactory(new PropertyValueFactory<>("deliverId"));
        colDriverName.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        colVehicleType.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        colDriverContact.setCellValueFactory(new PropertyValueFactory<>("driverContact"));
        colDeliverDelete1.setCellValueFactory(new PropertyValueFactory<>("deliverBtn"));
        validateCenter();
        btnSaveDeliver.setDisable(true);
        loadAllDeliver();
        tblDeliver.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                try {
                    DeliverTM selectedItem = tblDeliver.getSelectionModel().getSelectedItem();
                    txtDeliverId.setText(selectedItem.getDeliverId());
                    txtDriverName.setText(selectedItem.getDriverName());
                    cmbVehicleType.setValue(selectedItem.getVehicleType());

                    search();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                btnSaveDeliver.setText("Update");

            }
        });

    }

    private void validateCenter() {
        btnSaveDeliver.setDisable(true);
        map.put(txtDeliverId,idPattern);
        map.put(txtDriverName,namePattern);
        map.put(txtDeliverContactNo,contactPattern);
    }

    public void deliverIdOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveDeliverOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (btnSaveDeliver.getText().equals("Update")){
            Deliver c = new Deliver(
                    txtDeliverId.getText(),txtDriverName.getText(), (String) cmbVehicleType.getValue(),txtDeliverContactNo.getText());

            try{
                boolean isUpdated = CrudUtil.execute("UPDATE Deliver SET driver_name=? ,driver_contact=?, vehicle_type=? WHERE deliver_id=?",c.getDriverName(),c.getDriverContact(),c.getVehicleType(),c.getDeliverId());
                if (isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                    loadAllDeliver();
                    btnSaveDeliver.setText("Save");
                }else{
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }


            }catch (SQLException | ClassNotFoundException e){

            }
        }else {
            Deliver c = new Deliver(
                    txtDeliverId.getText(),txtDriverName.getText(),(String) cmbVehicleType.getValue(),txtDeliverContactNo.getText()
            );

            try {
                if (CrudUtil.execute("INSERT INTO Deliver VALUES (?,?,?,?)",c.getDeliverId(),c.getDriverName(),c.getVehicleType(),c.getDriverContact())){
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

            clearAllTexts();
            loadAllDeliver();
        }
    }

    private void clearAllTexts() {
        txtDeliverId.clear();
        txtDriverName.clear();
        txtDeliverContactNo.clear();
        txtDeliverId.requestFocus();
        setBorders(txtDeliverId,txtDriverName,txtDeliverContactNo);
    }
    public void setBorders(JFXTextField... textFields){
        for (JFXTextField textField : textFields) {
            textField.getParent().setStyle("-fx-border-color: rgba(76, 73, 73, 0.83)");
        }
    }

    private void loadAllDeliver() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Deliver");
        ObservableList<DeliverTM> obList = FXCollections.observableArrayList();


        while (result.next()) {
            Button btn1 = new Button("Delete");
            btn1.setOnAction(event -> {
                DeliverTM selectedItem= tblDeliver.getSelectionModel().getSelectedItem();
                txtDeliverId.setText(selectedItem.getDeliverId());


                deleteEmployee();

                try {
                    loadAllDeliver();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

            obList.add(
                    new DeliverTM(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            btn1
                    )
            );

        }
        tblDeliver.setItems(obList);
        clearAllTexts();
    }

    private void deleteEmployee() {
        try{

            if(CrudUtil.execute("DELETE FROM Deliver WHERE deliver_id=?",txtDeliverId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                btnSaveDeliver.setText("Save");
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }
    }

    public void searchDeliverOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    private void search() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Deliver WHERE deliver_id=?",txtDeliverId.getText());
        if (result.next()) {
            txtDriverName.setText(result.getString(2));
            cmbVehicleType.setValue(result.getString(3));
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
        }
    }

    public void textField_key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnSaveDeliver);

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

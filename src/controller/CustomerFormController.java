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
import model.Customer;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.CustomerTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class CustomerFormController {
    public AnchorPane customerContext;
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerContact;
    public TableColumn colCustomerId;
    public TableColumn colCustomerName;
    public TableColumn colCustomerAddress;
    public TableColumn colCustomerContact;
    public TableColumn colCustomerDelete;
    public TableView<CustomerTM> tblCustomer;
    public JFXButton btnSaveCustomer;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    Pattern idPattern = Pattern.compile("^(C-)[0-9]{3,5}$");
    Pattern namePattern = Pattern.compile("^([A-Z][a-z]*((\\s)))+[A-Z][a-z]*$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,20}$");
    Pattern contactPattern = Pattern.compile("^07(7|6|8|1|2|5|0|4)-[0-9]{7}$");

    public void initialize() throws SQLException, ClassNotFoundException {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        colCustomerContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colCustomerDelete.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadAllCustomers();
       validateCenter();
        btnSaveCustomer.setDisable(true);


        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                try {
                    CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
                    txtCustomerId.setText(selectedItem.getCustomerId());
                    txtCustomerName.setText(selectedItem.getCustomerName());
                    txtCustomerAddress.setText(selectedItem.getCustomerAddress());
                    txtCustomerContact.setText(selectedItem.getContact());

                    search();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                btnSaveCustomer.setText("Update");
                //setData((RoomRM) newValue);
            }
        });

    }
    public void validateCenter(){
        btnSaveCustomer.setDisable(true);
        map.put(txtCustomerId,idPattern);
        map.put(txtCustomerName,namePattern);
        map.put(txtCustomerAddress,addressPattern);
        map.put(txtCustomerContact,contactPattern);
    }


    public void customerSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
              String userId=LoginFormController.userId;
        if (btnSaveCustomer.getText().equals("Update")){
            Customer c = new Customer(
                  userId,txtCustomerId.getText(),txtCustomerName.getText(), txtCustomerAddress.getText(),txtCustomerContact.getText());

            try{
                boolean isUpdated = CrudUtil.execute("UPDATE Customer SET name=? , address=? , contact=? WHERE customer_id=?",c.getCustomerName(),c.getAddress(),c.getContact(),c.getCustomerId());
                if (isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                    loadAllCustomers();
                    btnSaveCustomer.setText("Save");
                }else{
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }


            }catch (SQLException | ClassNotFoundException e){

            }
        }else {
            Customer c = new Customer(
                    userId,txtCustomerId.getText(),txtCustomerName.getText(), txtCustomerAddress.getText(), txtCustomerContact.getText()
            );

            try {
                if (CrudUtil.execute("INSERT INTO Customer VALUES (?,?,?,?,?)",c.getCustomerId(),c.getCustomerName(),c.getAddress(),c.getContact(),c.getAdminId())){
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

            clearAllTexts();
            loadAllCustomers();
        }
    }

    private void loadAllCustomers() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Customer");
        ObservableList<CustomerTM> obList = FXCollections.observableArrayList();


        while (result.next()) {
            Button btn1 = new Button("Delete");
            btn1.setOnAction(event -> {
                CustomerTM selectedItem= tblCustomer.getSelectionModel().getSelectedItem();
                txtCustomerId.setText(selectedItem.getCustomerId());


               deleteCustomer();

                try {
                    loadAllCustomers();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

            obList.add(
                    new CustomerTM(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            btn1
                    )
            );

        }
        tblCustomer.setItems(obList);
        clearAllTexts();

    }

    private void deleteCustomer() {
        try{

            if(CrudUtil.execute("DELETE FROM Customer WHERE customer_id=?",txtCustomerId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                btnSaveCustomer.setText("Save");
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }

    }

    private void clearAllTexts() {
        txtCustomerId.clear();
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txtCustomerContact.clear();
        txtCustomerId.requestFocus();
        setBorders(txtCustomerId,txtCustomerName,txtCustomerAddress,txtCustomerContact);

    }

//    private void setBorders(TextField... textFields) {
//        for (TextField textField : textFields) {
//           // textField.getParent().setStyle("-fx-border-color: rgba(76, 73, 73, 0.83)");
//        }
//
//    }


 //   ObservableList<CustomerTM> tmList = FXCollections.observableArrayList();
//    public void saveCustomer() {
//        String customerId = txtCustomerId.getText();
//        String customerName = txtCustomerName.getText();
//        String customerAddress = txtCustomerAddress.getText();
//        String customerContact = txtCustomerContact.getText();
//
//        Button btn = new Button("DELETE");
//        CustomerTM customerTM = new CustomerTM(customerId, customerName, customerAddress, customerContact, btn);
//        btn.setOnAction(e -> {
//
//            tmList.remove(customerTM);
//           // calculateTotal();
//
//
//        });
//
//        tmList.add(customerTM);
//        tblCustomer.setItems(tmList);
//
//        tblCustomer.refresh();
//    //calculateTotal();
//
//        btn.setOnAction(e -> {
//            try {
//                if (CrudUtil.execute("DELETE FROM Customer WHERE id=?", colCustomerId.getText())) {
//                    new Alert(Alert.AlertType.CONFIRMATION, "Deleted..!").show();
//                } else {
//                    new Alert(Alert.AlertType.WARNING, "Try Again..!").show();
//                }
//            } catch (SQLException | ClassNotFoundException ex) {
//
//            }
//        });
//    }
   


    public void customerSearchOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }


    }

    public void customerIdOnAction(ActionEvent actionEvent) {

        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    private void search() throws ClassNotFoundException, SQLException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Customer WHERE customer_id=?",txtCustomerId.getText());
        if (result.next()) {
            txtCustomerName.setText(result.getString(2));
            txtCustomerAddress.setText(result.getString(3));
            txtCustomerContact.setText(result.getString(4));
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
        }
    }


    public void setBorders(JFXTextField... textFields){
        for (JFXTextField textField : textFields) {
            textField.getParent().setStyle("-fx-border-color: rgba(76, 73, 73, 0.83)");
        }
    }



    public void textField_key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnSaveCustomer);

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


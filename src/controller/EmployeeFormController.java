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
import model.Employee;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.CustomerTM;
import view.tm.EmployeeTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class EmployeeFormController {
    public AnchorPane employeeContext;
    public JFXTextField txtEmployeeId;
    public JFXTextField txtEmployeeName;
    public JFXTextField txtEmployeeSalary;
    public JFXTextField txtEmployeeContact;
    public TableView<EmployeeTM> tblEmployee;
    public TableColumn colEmployeeId;
    public TableColumn colEmployeeName;
    public TableColumn colEmployeeSalary;
    public TableColumn colEmployeeContact;
    public TableColumn colEmployeeDelete;
    public JFXButton btnSaveEmployee;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    Pattern idPattern = Pattern.compile("^(E-)[0-9]{3,5}$");
    Pattern namePattern = Pattern.compile("^([A-Z][a-z]*((\\s)))+[A-Z][a-z]*$");
    Pattern salaryPattern = Pattern.compile("^[1-9][0-9]*(.[0-9]{2})?$");
    Pattern contactPattern = Pattern.compile("^07(7|6|8|1|2|5|0|4)-[0-9]{7}$");

    public void initialize() throws SQLException, ClassNotFoundException {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colEmployeeSalary.setCellValueFactory(new PropertyValueFactory<>("employeeSalary"));
        colEmployeeContact.setCellValueFactory(new PropertyValueFactory<>("employeeContact"));
        colEmployeeDelete.setCellValueFactory(new PropertyValueFactory<>("employeeBtn"));
        loadAllEmployee();
        validateCenter();
        btnSaveEmployee.setDisable(true);
        tblEmployee.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                try {
                    EmployeeTM selectedItem = tblEmployee.getSelectionModel().getSelectedItem();
                    txtEmployeeId.setText(selectedItem.getEmployeeId());
                    txtEmployeeName.setText(selectedItem.getEmployeeName());
                    txtEmployeeSalary.setText(String.valueOf(selectedItem.getEmployeeSalary()));
                    txtEmployeeContact.setText(selectedItem.getEmployeeContact());

                    search();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                btnSaveEmployee.setText("Update");
                //setData((RoomRM) newValue);
            }
        });

    }

    private void validateCenter() {
        btnSaveEmployee.setDisable(true);
        map.put(txtEmployeeId,idPattern);
        map.put(txtEmployeeName,namePattern);
        map.put(txtEmployeeSalary,salaryPattern);
        map.put(txtEmployeeContact,contactPattern);
    }


    public void employeeIdOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    public void employeeSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (btnSaveEmployee.getText().equals("Update")){
            Employee c = new Employee(
                    txtEmployeeId.getText(),txtEmployeeName.getText(), Double.parseDouble(txtEmployeeSalary.getText()),txtEmployeeContact.getText());

            try{
                boolean isUpdated = CrudUtil.execute("UPDATE Employee SET name=? , salary=? , contact=? WHERE employee_id=?",c.getEmployeeName(),c.getSalary(),c.getContact(),c.getEmployeeId());
                if (isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                    loadAllEmployee();
                    btnSaveEmployee.setText("Save");
                }else{
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }


            }catch (SQLException | ClassNotFoundException e){

            }
        }else {
            Employee c = new Employee(
                    txtEmployeeId.getText(),txtEmployeeName.getText(), Double.parseDouble(txtEmployeeSalary.getText()), txtEmployeeContact.getText()
            );

            try {
                if (CrudUtil.execute("INSERT INTO Employee VALUES (?,?,?,?,?)",c.getEmployeeId(),c.getEmployeeName(),c.getSalary(),c.getContact(),c.getAdminId())){
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

            clearAllTexts();
            loadAllEmployee();
        }
    }

    private void loadAllEmployee() throws SQLException, ClassNotFoundException {

        ResultSet result = CrudUtil.execute("SELECT * FROM Employee");
        ObservableList<EmployeeTM> obList = FXCollections.observableArrayList();


        while (result.next()) {
            Button btn1 = new Button("Delete");
            btn1.setOnAction(event -> {
                EmployeeTM selectedItem= tblEmployee.getSelectionModel().getSelectedItem();
                txtEmployeeId.setText(selectedItem.getEmployeeId());


                deleteEmployee();

                try {
                    loadAllEmployee();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

            obList.add(
                    new EmployeeTM(
                            result.getString(1),
                            result.getString(2),
                            result.getDouble(3),
                            result.getString(4),
                            btn1
                    )
            );

        }
        tblEmployee.setItems(obList);
        clearAllTexts();
    }

    private void deleteEmployee() {
        try{

            if(CrudUtil.execute("DELETE FROM Employee WHERE employee_id=?",txtEmployeeId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                btnSaveEmployee.setText("Save");
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }
    }


    private void clearAllTexts() {
        txtEmployeeId.clear();
        txtEmployeeName.clear();
        txtEmployeeSalary.clear();
        txtEmployeeContact.clear();
        txtEmployeeId.requestFocus();
        setBorders(txtEmployeeId,txtEmployeeName,txtEmployeeSalary,txtEmployeeContact);
    }
    public void setBorders(JFXTextField... textFields){
        for (JFXTextField textField : textFields) {
            textField.getParent().setStyle("-fx-border-color: rgba(76, 73, 73, 0.83)");
        }
    }



    public void employeeSearchOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    private void search() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Employee WHERE employee_id=?",txtEmployeeId.getText());
        if (result.next()) {
            txtEmployeeName.setText(result.getString(2));
            txtEmployeeSalary.setText(result.getString(3));
            txtEmployeeContact.setText(result.getString(4));
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
        }
    }

    public void textField_key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnSaveEmployee);

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

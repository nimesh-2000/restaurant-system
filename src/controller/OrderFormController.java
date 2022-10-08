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
import model.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.CrudUtil;
import util.Util;
import util.ValidationUtil;
import view.tm.CartTM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class OrderFormController {
   // public AnchorPane employeeContext;
    public TableView tblOrder;
    public TableColumn colFoodId;
    public TableColumn colFoodName;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public TableColumn colCost;
    public TableColumn colDelete;
    public JFXComboBox cmbCustomerId;
    public JFXTextField txtOrderCustomerName;
    public JFXTextField txtOrderCustomerAddress;
    public JFXComboBox cmbFoodId;
    public JFXTextField txtFoodDescription;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQty;
    public JFXComboBox cmbDeliverId;
    public JFXTextField txtPaymentId;
    public JFXComboBox cmbPaymentType;
    public JFXTextField txtOrderDate;
    public JFXTextField txtDriverName;
    public TableColumn colTotal;
    public Label lblTotal;
    public JFXTextField txtCustomerId1;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerContact;
    public JFXTextField txtCustomerName;
    public JFXTextField txtOrderId;
    public AnchorPane orderContext;
    public JFXButton btnAddToCart;
    String date;
    ObservableList<CartTM> obList = FXCollections.observableArrayList();

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    Pattern namePattern = Pattern.compile("^[A-z ]{3,15}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,20}$");
    Pattern contactPattern = Pattern.compile("^07(7|6|8|1|2|5|0|4)-[0-9]{7}$");
    Pattern description = Pattern.compile("^[A-z ]{3,15}$");
    Pattern qtyOnHand = Pattern.compile("^[0-9 ]{1,}$");
    Pattern unitPrice = Pattern.compile("^[1-9][0-9]*(.[0-9]{1,2})?$");
    Pattern qty = Pattern.compile("^[0-9 ]{1,}$");
    Pattern driverNamePattern = Pattern.compile("^[A-z ]{3,15}$");

    public void initialize() throws SQLException, ClassNotFoundException {
        colFoodId.setCellValueFactory(new PropertyValueFactory<>("foodId"));
        colFoodName.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("cartDelete"));
        validateCenter();
        btnAddToCart.setDisable(true);
        txtOrderId.setText(generateOrderId());
        txtPaymentId.setText(generatePaymentId());

        ObservableList<Object> obl = FXCollections.observableArrayList();

        obl.add("Cash On Delivery");
        obl.add("Bank Transfer");

        cmbPaymentType.setItems(obl);
        loadDate();
        setFoodItemCodes();
        setCustomerCodes();
        setDeliverCodes();
        //loadAllSuppliers();

        cmbFoodId.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setFoodItemData((String) newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

        cmbCustomerId.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setCustomerData((String) newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

        cmbDeliverId.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setDeliverData((String) newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

    }

    private void validateCenter() {
        btnAddToCart.setDisable(true);
        map.put(txtCustomerName,namePattern);
        map.put(txtCustomerAddress,addressPattern);
        map.put(txtCustomerContact,contactPattern);
        map.put(txtFoodDescription,description);
        map.put(txtQtyOnHand,qtyOnHand);
        map.put(txtUnitPrice,unitPrice);
        map.put(txtQty,qty);
       // map.put(txtDriverName,driverNamePattern);
    }

    private void setDeliverData(String driverId) throws SQLException, ClassNotFoundException {
        Deliver deliver = new DeliverCrudController().getDeliver(driverId);
        if (deliver==null){
            new Alert(Alert.AlertType.WARNING,"Empty Result Set").show();
        }else {
            txtDriverName.setText(deliver.getDriverName());
        }
    }

    private void setCustomerData(String customerId) throws SQLException, ClassNotFoundException {
        Customer customer = new CustomerCrudController().getCustomer(customerId);
        if (customer==null){
            new Alert(Alert.AlertType.WARNING,"Empty Result Set").show();
        }else {
          //  txtCustomerId1.setText(customer.getCustomerName());
            txtCustomerContact.setText(customer.getCustomerName());
            txtCustomerAddress.setText(customer.getAddress());
            txtCustomerName.setText(customer.getContact());


        }
    }

    private void setFoodItemData(String foodItemId) throws SQLException, ClassNotFoundException {
        FoodItem foodItem = new FoodItemCrudController().getFoodItem(foodItemId);
        if (foodItem==null){
            new Alert(Alert.AlertType.WARNING,"Empty Result Set").show();
        }else {
            txtFoodDescription.setText(foodItem.getFoodName());
            txtQtyOnHand.setText(String.valueOf(foodItem.getQtyOnHand()));
            txtUnitPrice.setText(String.valueOf(foodItem.getPrice()));
        }
    }

    private void setDeliverCodes() {
        try {
            List<String> driverIds = DeliverCrudController.getDeliverCodes();
            cmbDeliverId.getItems().addAll(driverIds);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCustomerCodes() {
        try {
            List<String> customerIds = CustomerCrudController.getCustomerCodes();
            cmbCustomerId.getItems().addAll(customerIds);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setFoodItemCodes() {
        try {
            List<String> foodItemIds = FoodItemCrudController.getFoodItemCodes();
            cmbFoodId.getItems().addAll(foodItemIds);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadDate() {
        date=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        txtOrderDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }


    public void addToCartOnAction(ActionEvent actionEvent) {
        double total=Double.parseDouble(txtUnitPrice.getText())*Integer.parseInt(txtQty.getText());
        if (Integer.parseInt(txtQtyOnHand.getText())<Integer.parseInt(txtQty.getText())){
            new Alert(Alert.AlertType.WARNING,"Invalid Quantity").show();
            return;
        }

        Button btn1=new Button("Delete");
        CartTM cartTM = new CartTM((String) cmbFoodId.getValue(),txtFoodDescription.getText(),Double.parseDouble(txtUnitPrice.getText()),Integer.parseInt(txtQty.getText()),total,btn1);
//        try {
//            updateQuentity((String) cmbFoodId.getValue(),Integer.parseInt(txtQty.getText()));
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        int rowNum=isExists(cartTM);
        if (rowNum==1){
            btn1.setOnAction(e -> {
                obList.remove(cartTM);
                ClearAllText();
                calculateTotal();

            });
            obList.add(cartTM);
        }else {
            CartTM temp = (CartTM) obList.get(rowNum);
            CartTM newTm = new CartTM(temp.getFoodId(),temp.getFoodName(),temp.getUnitPrice(),Integer.valueOf((temp.getQty())+Integer.parseInt(txtQty.getText())),temp.getTotal()+total,temp.getCartDelete());
            obList.remove(rowNum);
            obList.add(newTm);



        }

        tblOrder.setItems(obList);
        calculateTotal();
    }

    private void ClearAllText() {
            txtCustomerName.clear();
            txtCustomerAddress.clear();
            txtCustomerContact.clear();
            txtQtyOnHand.clear();
            txtUnitPrice.clear();
            txtQty.clear();
            txtDriverName.clear();
            txtFoodDescription.clear();
            cmbCustomerId.requestFocus();
            cmbFoodId.requestFocus();
            cmbDeliverId.requestFocus();
        setBorders(txtCustomerName,txtCustomerAddress,txtCustomerContact,txtQtyOnHand,txtUnitPrice,txtQty,txtDriverName,txtFoodDescription);
    }
    public void setBorders(JFXTextField... textFields){
        for (JFXTextField textField : textFields) {
            textField.getParent().setStyle("-fx-border-color: rgba(76, 73, 73, 0.83)");
        }
    }


    private void calculateTotal() {
        double tot = 0;
        for (CartTM tm : obList
        ) {
            tot += tm.getTotal();
        }
        lblTotal.setText(String.valueOf(tot));
    }


    private int isExists(CartTM tm) {
        for (int i=0;i<obList.size();i++){
            if (tm.getFoodId().equals(obList.get(i).getFoodId())){
                return i;
            }
        }
        return 1;
    }

    private boolean updateQuentity(String foodItemId, int qty) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection()
                .prepareStatement
                        ("UPDATE FoodItem SET qty=(qty-" + qty
                                + ") WHERE food_id='" + foodItemId + "'");
        return stm.executeUpdate()>0;
    }

    public void placeOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> items = new ArrayList<>();
        for (CartTM temp : obList
        ) {
            items.add(new OrderDetail(txtOrderId.getText(), temp.getFoodId(), txtPaymentId.getText(), temp.getQty()));
        }
        Order order = new Order((String) cmbDeliverId.getValue(),(String) cmbCustomerId.getValue(),txtOrderId.getText(),txtFoodDescription.getText(),Double.parseDouble(lblTotal.getText()),txtOrderDate.getText(), items);
        if (placeOrder(order)){
            new Alert(Alert.AlertType.CONFIRMATION,"Your Order is Successfull").show();
            txtOrderId.setText(generateOrderId());
            try {
            updateQuentity((String) cmbFoodId.getValue(),Integer.parseInt(txtQty.getText()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        }else {
            new Alert(Alert.AlertType.WARNING,"Your Order Fail").show();
        }



    }

    private String generateOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT order_id FROM Orders ORDER BY order_id DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "O-00" + tempId;
            } else if (tempId < 99) {

                return "O-0" + tempId;

            } else {
                return "O-" + tempId;
            }

        } else {
            return "O-001";
        }

    }
    private String generatePaymentId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT payment_id FROM Payment ORDER BY payment_id DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "P-00" + tempId;
            } else if (tempId < 99) {

                return "P-0" + tempId;

            } else {
                return "P-" + tempId;
            }

        } else {
            return "P-001";
        }
    }
    private boolean placeOrder(Order o) {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.prepareStatement("INSERT INTO Orders VALUES(?,?,?,?,?,?) ");
            stm.setObject(1, o.getOrderId());
            stm.setObject(2, o.getOrderName());
            stm.setObject(3, o.getTotalPrice());
            stm.setObject(4, o.getCustomerId());
            stm.setObject(5, o.getDeliverId());
            stm.setObject(6, o.getOrderDate());

            if (stm.executeUpdate() > 0) {
                if (saveOrderDetails(o.getOrderId(), o.getItems())) {
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

    private boolean saveOrderDetails(String orderId, ArrayList<OrderDetail> items) throws SQLException, ClassNotFoundException {

        for (OrderDetail temp:items

        ) {
            System.out.println(orderId+""+temp.getFoodId()+""+temp.getPaymentId()+""+temp.getQty());
            if (CrudUtil.execute("INSERT INTO OrderDetail VALUES(?,?,?,?)",orderId,temp.getFoodId(),temp.getPaymentId(),temp.getQty())){

            }else {
                return false;
            }
        }
        return true;

    }

    public void printBillOnAction(ActionEvent actionEvent) {
        String orderCode = txtOrderId.getText();
        double totalCost = Double.parseDouble(lblTotal.getText());

        HashMap paramMap = new HashMap();
        paramMap.put("orderId", orderCode);// id = report param name // customerID = UI typed value
        paramMap.put("total", totalCost);

        //ObservableList<CartTM> tableRecords = tblOrder.getItems();

        try {
            JasperReport compileReport  = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/report/billReport_A4.jasper"));
          //  ObservableList<CartTM> tableRecords = tblOrder.getItems();
            JasperPrint jasperPrint= JasperFillManager.fillReport(compileReport,paramMap,new JRBeanArrayDataSource(tblOrder.getItems().toArray()));
            JasperViewer.viewReport(jasperPrint,false);

        } catch( JRException e) {
            e.printStackTrace();
        }
    }

    public void paymentAddOnAction(ActionEvent actionEvent) {
        try {
            if (CrudUtil.execute("INSERT INTO Payment VALUES (?,?,?,?)",txtPaymentId.getText(),cmbPaymentType.getValue(),lblTotal.getText(),date)){
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void paymentDeleteOnAction(ActionEvent actionEvent) {
        try{

            if(CrudUtil.execute("DELETE FROM Payment WHERE payment_id=?",txtPaymentId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                txtPaymentId.setText("");
                cmbPaymentType.setValue("");
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }
    }

    public void paymentIdSearchOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }
    private void search() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Payment WHERE payment_id=?",txtPaymentId.getText());
        if (result.next()) {
            cmbPaymentType.setValue(result.getString(2));
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
        }
    }

    public void viewOrderOnAction(ActionEvent actionEvent) throws IOException {
        Util.navigate(orderContext,"OrderViewForm");
    }

    public void textField_key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnAddToCart);

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

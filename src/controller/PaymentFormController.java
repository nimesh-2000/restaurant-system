package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Payment;
import util.CrudUtil;
import view.tm.PaymentTM;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentFormController {
    public AnchorPane paymentContext;
    public TableView tblPayment;
    public TableColumn colPaymentId;
    public TableColumn colPaymentType;
    public TableColumn colPaymentDate;
    public TableColumn colTotal;


    public void initialize() throws SQLException, ClassNotFoundException {
        colPaymentId.setCellValueFactory(new PropertyValueFactory("paymentId"));
        colPaymentType.setCellValueFactory(new PropertyValueFactory("paymentType"));
        colTotal.setCellValueFactory(new PropertyValueFactory("totalPayment"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory("paymentDate"));

        try {
           loadAllPayments();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
       // loadAllPayments();
    }

    private void loadAllPayments() throws ClassNotFoundException, SQLException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Payment");
        ObservableList<PaymentTM> obList = FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new PaymentTM(
                            result.getString(1),
                            result.getString(2),
                            result.getDouble(3),
                            result.getString(4)
                    )
            );
        }
        tblPayment.setItems(obList);

    }
}

package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import util.CrudUtil;
import view.tm.OrderViewTM;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderViewFormController {
    public AnchorPane orderViewContext;
    public TableView tblOrderView;
    public TableColumn colOrderId;
    public TableColumn colFoodItem;
    public TableColumn colCost;
    public TableColumn colCustomerId;
    public TableColumn colDeliverId;
    public TableColumn colDate;


    public void initialize(){
        colOrderId.setCellValueFactory(new PropertyValueFactory("orderId"));
        colFoodItem.setCellValueFactory(new PropertyValueFactory("foodItem"));
        colCost.setCellValueFactory(new PropertyValueFactory("cost"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory("customerId"));
        colDeliverId.setCellValueFactory(new PropertyValueFactory("deliverId"));
        colDate.setCellValueFactory(new PropertyValueFactory("date"));

        try {
            loadAllOrders();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAllOrders() throws ClassNotFoundException, SQLException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Orders");
        ObservableList<OrderViewTM> obList = FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new OrderViewTM(
                            result.getString(1),
                            result.getString(2),
                            result.getDouble(3),
                            result.getString(4),
                            result.getString(5),
                            result.getString(6)
                    )
            );
        }
        tblOrderView.setItems(obList);

    }
}

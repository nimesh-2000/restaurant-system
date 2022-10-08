package controller;


import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DashboardMainFormController {
    public AnchorPane dashBoardContext;
    public Label lblTotalOrders;
    public Label lblTotalCustomers;
    public Label lblTotalDeliver;
    public Label lblTotalEmployee;
    public Label lblTotalFoodItems;
    @FXML
    private AreaChart<?, ?> areaChart;

    public void initialize() throws SQLException, ClassNotFoundException {
//        XYChart.Series series1=new XYChart.Series();
//
//        series1.getData().add(new XYChart.Data("2010",80));
//        series1.getData().add(new XYChart.Data("2011",45));
//        series1.getData().add(new XYChart.Data("2012",90));
//        series1.getData().add(new XYChart.Data("2013",110));
//        series1.getData().add(new XYChart.Data("2014",200));
//        series1.getData().add(new XYChart.Data("2015",70));
//        series1.getData().add(new XYChart.Data("2016",90));
//        series1.getData().add(new XYChart.Data("2017",150));
//        series1.getData().add(new XYChart.Data("2018",110));
//        series1.getData().add(new XYChart.Data("2019",119));
//        series1.getData().add(new XYChart.Data("2020",130));
//        series1.getData().add(new XYChart.Data("2021",190));
//
//
//        areaChart.getData().addAll(series1);
      loadAllLabelData();
        setLineChartOrder();

    }
   public void loadAllLabelData() throws SQLException, ClassNotFoundException {
       ResultSet resultSet = CrudUtil.execute("SELECT COUNT(order_id) FROM Orders");
       if (resultSet.next()) {
           lblTotalOrders.setText(String.valueOf(resultSet.getInt(1)));
       }

       ResultSet resultSet2 = CrudUtil.execute("SELECT COUNT(customer_id) FROM Customer");
       if (resultSet2.next()) {
           lblTotalCustomers.setText(String.valueOf(resultSet2.getInt(1)));
       }

       ResultSet resultSet3 = CrudUtil.execute("SELECT COUNT(deliver_id) FROM Deliver");
       if (resultSet3.next()) {
           lblTotalDeliver.setText(String.valueOf(resultSet3.getInt(1)));
       }

       ResultSet resultSet4 = CrudUtil.execute("SELECT COUNT(employee_id) FROM Employee");
       if (resultSet4.next()) {
           lblTotalEmployee.setText(String.valueOf(resultSet4.getInt(1)));
       }
       ResultSet resultSet5 = CrudUtil.execute("SELECT COUNT(food_id) FROM FoodItem");
       if (resultSet5.next()) {
           lblTotalFoodItems.setText(String.valueOf(resultSet5.getInt(1)));
       }
   }
    private void setLineChartOrder() throws SQLException, ClassNotFoundException {

        ResultSet result =CrudUtil.execute("SELECT order_date,total_price FROM Orders");

        double janCost=0;
        double febCost=0;
        double marCost=0;
        double aprCost=0;
        double mayCost=0;
        double junCost=0;
        double julCost=0;
        double aguCost=0;
        double sepCost=0;
        double octCost=0;
        double novCost=0;
        double decCost=0;

        while(result.next()){
            java.sql.Date dt=result.getDate(1);
            LocalDate localDate = dt.toLocalDate();

            if(localDate.getMonthValue()==1){
                janCost+= result.getDouble(2);
            }else if(localDate.getMonthValue()==2){
                febCost+= result.getDouble(2);
            }else if(localDate.getMonthValue()==3){
                marCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==4){
                aprCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==5){
                mayCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==6){
                junCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==7){
                julCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==8){
                aguCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==9){
                sepCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==10){
                octCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==11){
                novCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==12){
                decCost+=result.getDouble(2);
            }


        }

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");

        final LineChart<String, Number> lineChart =
                new LineChart<String, Number>(xAxis, yAxis);



        XYChart.Series series = new XYChart.Series();
        series.setName("Month");

        series.getData().add(new XYChart.Data("Jan", janCost));
        series.getData().add(new XYChart.Data("Feb", febCost));
        series.getData().add(new XYChart.Data("Mar", marCost));
        series.getData().add(new XYChart.Data("Apr", aprCost));
        series.getData().add(new XYChart.Data("May", mayCost));
        series.getData().add(new XYChart.Data("Jun", junCost));
        series.getData().add(new XYChart.Data("Jul", julCost));
        series.getData().add(new XYChart.Data("Aug", aguCost));
        series.getData().add(new XYChart.Data("Sep", sepCost));
        series.getData().add(new XYChart.Data("Oct", octCost));
        series.getData().add(new XYChart.Data("Nov", novCost));
        series.getData().add(new XYChart.Data("Dec", decCost));



        areaChart.getData().add(series);
    }

}

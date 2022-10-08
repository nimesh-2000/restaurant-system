package model;

import java.util.ArrayList;

public class FoodItem {
    private String foodId;
    private String foodName;
    private double price;
    private int qtyOnHand;
    private ArrayList<FoodDetail>foodDetails;
    public FoodItem() {
    }

    public FoodItem(String foodId, String foodName, double price, int qtyOnHand, ArrayList<FoodDetail> foodDetails) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.price = price;
        this.qtyOnHand = qtyOnHand;
        this.foodDetails = foodDetails;
    }

    public FoodItem(String foodId, String foodName, double price, int qtyOnHand) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.price = price;
        this.qtyOnHand = qtyOnHand;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    @Override
    public String toString() {
        return "FoodItem{" +
                "foodId='" + foodId + '\'' +
                ", foodName='" + foodName + '\'' +
                ", price=" + price +
                ", qtyOnHand=" + qtyOnHand +
                '}';
    }

    public ArrayList<FoodDetail> getFoodDetails() {
        return foodDetails;
    }

    public void setFoodDetails(ArrayList<FoodDetail> foodDetails) {
        this.foodDetails = foodDetails;
    }
}

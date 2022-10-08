package view.tm;

import javafx.scene.control.Button;

public class FoodItemTM {
    private String foodId;
    private String foodName;
    private double price;
    private int qtyOnHand;
    private Button foodBtn;

    public FoodItemTM() {
    }

    public FoodItemTM(String foodId, String foodName, double price, int qtyOnHand, Button foodBtn) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.price = price;
        this.qtyOnHand = qtyOnHand;
        this.foodBtn = foodBtn;
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

    public Button getFoodBtn() {
        return foodBtn;
    }

    public void setFoodBtn(Button foodBtn) {
        this.foodBtn = foodBtn;
    }

    @Override
    public String toString() {
        return "FoodItemTM{" +
                "foodId='" + foodId + '\'' +
                ", foodName='" + foodName + '\'' +
                ", price=" + price +
                ", qtyOnHand=" + qtyOnHand +
                ", foodBtn=" + foodBtn +
                '}';
    }
}

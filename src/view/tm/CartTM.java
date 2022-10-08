package view.tm;

import javafx.scene.control.Button;

public class CartTM {
          private String foodId;
          private String foodName;
          private double unitPrice;
          private int qty;
          private double total;
          private Button cartDelete;

    public CartTM() {
    }

    public CartTM(String foodId, String foodName, double unitPrice, int qty, double total, Button cartDelete) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.total = total;
        this.cartDelete = cartDelete;
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

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Button getCartDelete() {
        return cartDelete;
    }

    public void setCartDelete(Button cartDelete) {
        this.cartDelete = cartDelete;
    }

    @Override
    public String toString() {
        return "CartTM{" +
                "foodId='" + foodId + '\'' +
                ", foodName='" + foodName + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                ", total=" + total +
                ", cartDelete=" + cartDelete +
                '}';
    }
}

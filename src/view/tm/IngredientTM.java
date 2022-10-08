package view.tm;

import javafx.scene.control.Button;

public class IngredientTM {
    private String SupplierId;
    private String ingredientId;
    private String ingredientName;
    private int ingredientQty;
    private double ingredientPrice;
    private Button ingredientBtn;

    public IngredientTM() {
    }

    public IngredientTM(String supplierId, String ingredientId, String ingredientName, int ingredientQty, double ingredientPrice, Button ingredientBtn) {
        SupplierId = supplierId;
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.ingredientQty = ingredientQty;
        this.ingredientPrice = ingredientPrice;
        this.ingredientBtn = ingredientBtn;
    }

    public String getSupplierId() {
        return SupplierId;
    }

    public void setSupplierId(String supplierId) {
        SupplierId = supplierId;
    }

    public String getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public int getIngredientQty() {
        return ingredientQty;
    }

    public void setIngredientQty(int ingredientQty) {
        this.ingredientQty = ingredientQty;
    }

    public double getIngredientPrice() {
        return ingredientPrice;
    }

    public void setIngredientPrice(double ingredientPrice) {
        this.ingredientPrice = ingredientPrice;
    }

    public Button getIngredientBtn() {
        return ingredientBtn;
    }

    public void setIngredientBtn(Button ingredientBtn) {
        this.ingredientBtn = ingredientBtn;
    }

    @Override
    public String toString() {
        return "IngredientTM{" +
                "SupplierId='" + SupplierId + '\'' +
                ", ingredientId='" + ingredientId + '\'' +
                ", ingredientName='" + ingredientName + '\'' +
                ", ingredientQty=" + ingredientQty +
                ", ingredientPrice=" + ingredientPrice +
                ", ingredientBtn=" + ingredientBtn +
                '}';
    }
}
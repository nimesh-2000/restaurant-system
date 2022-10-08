package model;

public class Ingredient {
    private String supId;
    private String ingredientId;
    private String ingredientName;
    private double price;
    private int qty;

    public Ingredient() {
    }


    public Ingredient(String supId, String ingredientId, String ingredientName, double price, int qty) {
        this.supId = supId;
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.price = price;
        this.qty = qty;
    }

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "supId='" + supId + '\'' +
                ", ingredientId='" + ingredientId + '\'' +
                ", ingredientName='" + ingredientName + '\'' +
                ", price=" + price +
                ", qty=" + qty +
                '}';
    }
}
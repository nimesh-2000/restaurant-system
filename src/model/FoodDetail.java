package model;

public class FoodDetail {
    private String foodId;
    private String ingredientId;
    private int qty;

    public FoodDetail() {
    }

    public FoodDetail(String foodId, String ingredientId, int qty) {
        this.foodId = foodId;
        this.ingredientId = ingredientId;
        this.qty = qty;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }


    @Override
    public String toString() {
        return "FoodDetail{" +
                "foodId='" + foodId + '\'' +
                ", ingredientId='" + ingredientId + '\'' +
                ", qty=" + qty +
                '}';
    }
}
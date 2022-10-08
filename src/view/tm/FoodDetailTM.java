package view.tm;

public class FoodDetailTM {
    private String ingredientId;
    private String foodId;
    private int qty;

    public FoodDetailTM() {
    }

    public FoodDetailTM(String ingredientId, String foodId, int qty) {
        this.ingredientId = ingredientId;
        this.foodId = foodId;
        this.qty = qty;
    }

    public String getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}

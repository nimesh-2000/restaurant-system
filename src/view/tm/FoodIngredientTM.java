package view.tm;

public class FoodIngredientTM {
    private String ingredientId;
    private String ingredientName;
    private int qty;

    public FoodIngredientTM() {
    }

    public FoodIngredientTM(String ingredientId, String ingredientName, int qty) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.qty = qty;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "FoodIngredientTM{" +
                "ingredientId='" + ingredientId + '\'' +
                ", ingredientName='" + ingredientName + '\'' +
                ", qty=" + qty +
                '}';
    }
}

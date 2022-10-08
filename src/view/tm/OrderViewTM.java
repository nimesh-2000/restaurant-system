package view.tm;

public class OrderViewTM {
    private String orderId;
    private String foodItem;
    private double Cost;
    private String customerId;
    private String deliverId;
    private String date;

    public OrderViewTM() {
    }

    public OrderViewTM(String orderId, String foodItem, double cost, String customerId, String deliverId, String date) {
        this.orderId = orderId;
        this.foodItem = foodItem;
        Cost = cost;
        this.customerId = customerId;
        this.deliverId = deliverId;
        this.date = date;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    public double getCost() {
        return Cost;
    }

    public void setCost(double cost) {
        Cost = cost;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "OrderViewTM{" +
                "orderId='" + orderId + '\'' +
                ", foodItem='" + foodItem + '\'' +
                ", Cost=" + Cost +
                ", customerId='" + customerId + '\'' +
                ", deliverId='" + deliverId + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}

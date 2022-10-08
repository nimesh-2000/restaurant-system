package model;

public class OrderDetail {
    private String orderId;
    private String foodId;
    private String paymentId;
    private int qty;

    public OrderDetail() {
    }

    public OrderDetail(String orderId, String foodId, String paymentId, int qty) {
        this.orderId = orderId;
        this.foodId = foodId;
        this.paymentId = paymentId;
        this.qty = qty;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderId='" + orderId + '\'' +
                ", foodId='" + foodId + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", qty=" + qty +
                '}';
    }
}

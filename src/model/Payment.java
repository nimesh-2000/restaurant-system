package model;

public class Payment {
   private String paymentId;
   private String paymentType;
   private double totalPayment;
   private String date;

    public Payment() {
    }

    public Payment(String paymentId, String paymentType, double totalPayment, String date) {
        this.paymentId = paymentId;
        this.paymentType = paymentType;
        this.totalPayment = totalPayment;
        this.date = date;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId='" + paymentId + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", totalPayment='" + totalPayment + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}

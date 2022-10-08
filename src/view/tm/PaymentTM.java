package view.tm;

public class PaymentTM {
    private String paymentId;
    private String paymentType;
    private double totalPayment;
    private String paymentDate;

    public PaymentTM() {
    }

    public PaymentTM(String paymentId, String paymentType, double totalPayment, String paymentDate) {
        this.paymentId = paymentId;
        this.paymentType = paymentType;
        this.totalPayment = totalPayment;
        this.paymentDate = paymentDate;
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

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }



    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    @Override
    public String toString() {
        return "PaymentTM{" +
                "paymentId='" + paymentId + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", totalPayment='" + totalPayment + '\'' +
                ", paymentDate='" + paymentDate + '\'' +
                '}';
    }
}

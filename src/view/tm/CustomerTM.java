package view.tm;

import javafx.scene.control.Button;

public class CustomerTM {
           private String customerId;
           private String customerName;
           private String customerAddress;
           private String contact;
           private Button btn;

    public CustomerTM() {
    }

    public CustomerTM(String customerId, String customerName, String customerAddress, String contact, Button btn) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.contact = contact;
        this.btn = btn;
    }

    public CustomerTM(String customerID, String customerName, String customerAddress, String customerContact) {
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "CustomerTM{" +
                "customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", contact='" + contact + '\'' +
                ", btn=" + btn +
                '}';
    }
}

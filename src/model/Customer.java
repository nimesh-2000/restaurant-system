package model;

public class Customer {
    private String adminId;
    private String customerId;
    private String customerName;
    private String address;
    private String contact;

    public Customer() {
    }

    public Customer(String customerId, String customerName, String address, String contact) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.contact = contact;
    }

    public Customer(String adminId, String customerId, String customerName, String address, String contact) {
        this.adminId = adminId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.contact = contact;
    }


    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "adminId='" + adminId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}

package view.tm;

import javafx.scene.control.Button;

public class DeliverTM {

    private String deliverId;
    private String driverName;
    private String vehicleType;
    private String driverContact;
    private Button deliverBtn;

    public DeliverTM() {
    }

    public DeliverTM(String deliverId, String driverName, String vehicleType, String driverContact, Button deliverBtn) {
        this.deliverId = deliverId;
        this.driverName = driverName;
        this.vehicleType = vehicleType;
        this.driverContact = driverContact;
        this.deliverBtn = deliverBtn;
    }

    public String getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getDriverContact() {
        return driverContact;
    }

    public void setDriverContact(String driverContact) {
        this.driverContact = driverContact;
    }

    public Button getDeliverBtn() {
        return deliverBtn;
    }

    public void setDeliverBtn(Button deliverBtn) {
        this.deliverBtn = deliverBtn;
    }

    @Override
    public String toString() {
        return "DeliverTM{" +
                "deliverId='" + deliverId + '\'' +
                ", driverName='" + driverName + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", driverContact='" + driverContact + '\'' +
                ", deliverBtn=" + deliverBtn +
                '}';
    }
}
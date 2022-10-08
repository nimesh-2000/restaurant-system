package model;

public class Deliver {
    private String deliverId;
    private String driverName;
    private String vehicleType;
    private String driverContact;

    public Deliver() {
    }

    public Deliver(String deliverId, String driverName, String vehicleType, String driverContact) {
        this.deliverId = deliverId;
        this.driverName = driverName;
        this.vehicleType = vehicleType;
        this.driverContact = driverContact;
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

    @Override
    public String toString() {
        return "Deliver{" +
                "deliverId='" + deliverId + '\'' +
                ", driverName='" + driverName + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", driverContact='" + driverContact + '\'' +
                '}';
    }
}
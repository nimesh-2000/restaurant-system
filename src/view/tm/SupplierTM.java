package view.tm;

import javafx.scene.control.Button;

public class SupplierTM {
    private String supId;
    private String supName;
    private String supAddress;
    private String supContact;
    private Button supplierBtn;

    public SupplierTM() {
    }

    public SupplierTM(String supId, String supName, String supAddress, String supContact, Button supplierBtn) {
        this.supId = supId;
        this.supName = supName;
        this.supAddress = supAddress;
        this.supContact = supContact;
        this.supplierBtn = supplierBtn;
    }

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }

    public String getSupAddress() {
        return supAddress;
    }

    public void setSupAddress(String supAddress) {
        this.supAddress = supAddress;
    }

    public String getSupContact() {
        return supContact;
    }

    public void setSupContact(String supContact) {
        this.supContact = supContact;
    }

    public Button getSupplierBtn() {
        return supplierBtn;
    }

    public void setSupplierBtn(Button supplierBtn) {
        this.supplierBtn = supplierBtn;
    }

    @Override
    public String toString() {
        return "SupplierTM{" +
                "supId='" + supId + '\'' +
                ", supName='" + supName + '\'' +
                ", supAddress='" + supAddress + '\'' +
                ", supContact='" + supContact + '\'' +
                ", supplierBtn=" + supplierBtn +
                '}';
    }
}
package view.tm;

import javafx.scene.control.Button;

public class EmployeeTM {
    private String employeeId;
    private String employeeName;
    private double employeeSalary;
    private String employeeContact;
    private Button employeeBtn;

    public EmployeeTM() {
    }

    public EmployeeTM(String employeeId, String employeeName, double employeeSalary, String employeeContact, Button employeeBtn) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeSalary = employeeSalary;
        this.employeeContact = employeeContact;
        this.employeeBtn = employeeBtn;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public double getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(double employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public String getEmployeeContact() {
        return employeeContact;
    }

    public void setEmployeeContact(String employeeContact) {
        this.employeeContact = employeeContact;
    }

    public Button getEmployeeBtn() {
        return employeeBtn;
    }

    public void setEmployeeBtn(Button btn) {
        this.employeeBtn = employeeBtn;
    }

    @Override
    public String toString() {
        return "EmployeeTM{" +
                "employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", employeeSalary=" + employeeSalary +
                ", employeeContact='" + employeeContact + '\'' +
                ", employeeBtn =" + employeeBtn +
                '}';
    }
}

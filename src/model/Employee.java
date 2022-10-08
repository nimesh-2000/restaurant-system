package model;

public class Employee {
    private String adminId;
    private String employeeId;
    private String employeeName;
    private double salary;
    private String contact;

    public Employee() {
    }

    public Employee(String employeeId, String employeeName, double salary, String contact) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.salary = salary;
        this.contact = contact;
    }

    public Employee(String adminId, String employeeId, String employeeName, double salary, String contact) {
        this.adminId = adminId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.salary = salary;
        this.contact = contact;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "adminId='" + adminId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", salary=" + salary +
                ", contact='" + contact + '\'' +
                '}';
    }
}

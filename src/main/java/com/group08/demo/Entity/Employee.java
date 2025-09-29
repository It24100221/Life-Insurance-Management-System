package com.group08.demo.Entity;

public abstract class Employee {
    private String employeeName;
    private String employeeNIC;
    private String employeePassword;
    private String role;

    public Employee() {}

    public Employee(String employeeName, String employeeNIC, String employeePassword, String role) {
        this.employeeName = employeeName;
        this.employeeNIC = employeeNIC;
        this.employeePassword = employeePassword;
        this.role = role;
    }

    // Overloaded constructor for employees without password
    public Employee(String employeeName, String employeeNIC, String role) {
        this.employeeName = employeeName;
        this.employeeNIC = employeeNIC;
        this.employeePassword = null; // or assign default
        this.role = role;
    }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public String getEmployeeNIC() { return employeeNIC; }
    public void setEmployeeNIC(String employeeNIC) { this.employeeNIC = employeeNIC; }

    public String getEmployeePassword() { return employeePassword; }
    public void setEmployeePassword(String employeePassword) { this.employeePassword = employeePassword; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}


class InsuranceAgent extends Employee {
    public InsuranceAgent(String employeeName, String employeeNIC, String employeePassword) {
        super(employeeName, employeeNIC, employeePassword, "InsuranceAgent");
    }
}

class SupportStaff extends Employee {
    public SupportStaff(String employeeName, String employeeNIC, String employeePassword) {
        super(employeeName, employeeNIC, employeePassword, "SupportStaff");
    }
}

class ClaimsOfficer extends Employee {
    public ClaimsOfficer(String employeeName, String employeeNIC, String employeePassword) {
        super(employeeName, employeeNIC, employeePassword, "ClaimsOfficer");
    }
}

class Admin extends Employee {
    public Admin() {
        super("Admin","000000000000","Admin123","Admin");
    }
}

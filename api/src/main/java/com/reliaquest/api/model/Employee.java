package com.reliaquest.api.model;

public class Employee {
    private String id;
    private String employeeName;
    private int employeeSalary;
    private int employeeAge;
    private String employeeTitle;
    private String employeeEmail;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getEmployeeName() {
        return employeeName;
    }
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    public int getEmployeeSalary() {
        return employeeSalary;
    }
    public void setEmployeeSalary(int employeeSalary) {
        this.employeeSalary = employeeSalary;
    }
    public int getEmployeeAge() {
        return employeeAge;
    }
    public void setEmployeeAge(int employeeAge) {
        this.employeeAge = employeeAge;
    }
    public String getEmployeeTitle() {
        return employeeTitle;
    }
    public void setEmployeeTitle(String employeeTitle) {
        this.employeeTitle = employeeTitle;
    }
    public String getEmployeeEmail() {
        return employeeEmail;
    }
    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", employeeSalary=" + employeeSalary +
                ", employeeAge=" + employeeAge +
                ", employeeTitle='" + employeeTitle + '\'' +
                ", employeeEmail='" + employeeEmail + '\'' +
                '}';
    }


}

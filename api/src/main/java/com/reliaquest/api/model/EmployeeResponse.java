package com.reliaquest.api.model;

public class EmployeeResponse {
    private String status;
    private List<Employee> data;

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public List<Employee> getData() {
        return data;
    }
    public void setData(List<Employee> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EmployeeResponse{" +
                "status='" + status + '\'' +
                ", data=" + data +
                '}';
    }


}

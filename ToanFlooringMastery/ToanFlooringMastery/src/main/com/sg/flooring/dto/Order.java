package main.com.sg.flooring.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Order {

    private LocalDate orderDate;
    private int orderNum;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }
    public int getOrderNum() {
        return orderNum;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerName() {
        return customerName;
    }

    public void setState(String state) {
        this.state = state;
    }
    public String getState() {
        return state;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
    public String getProductType() {
        return productType;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }
    public BigDecimal getArea() {
        return area;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }
    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }
    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }
    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }
    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }
    public BigDecimal getTax() {
        return tax;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    public BigDecimal getTotal() {
        return total;
    }
}

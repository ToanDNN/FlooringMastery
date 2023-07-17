package main.com.sg.flooring.ui;

import main.com.sg.flooring.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FloorView {
    private UserIO io;

    public FloorView(UserIO io) {
        this.io = io;
    }

    public int menuSelection() {
        //io.print("<<Flooring Program>>");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Exit");

        return io.readInt("Please select from the above options", 1, 5);
    }

    public void displayFloorBanner() {
        io.print("<<Flooring Program>>");
    }
    public void displayOrderBanner() {
        io.print("<<Display Order>>");
    }
    public void displayDateBanner(LocalDate dateChoice) {
        System.out.printf("<< Orders on %s >>\n", dateChoice);
    }

    public void displayAddOrderBanner() {
        io.print("<<Add Order>>");
    }
    public void displayAddOrderSuccess() {
        io.print("Order Added Successfully");
    }

    public void displayEditOrderBanner() {
        io.print("<<Edit Order>>");
    }
    public void displayEditSuccess() {
        io.print("Order Updated Successfully");
    }
    public void displayEditFail() {
        io.print("Order Not Updated");
    }

    public void displayRemoveOrderBanner() {
        io.print("<<Remove Order>>");
    }
    public void displayRemoveSuccess() {
        io.print("Order Successfully Removed");
    }
    public void displayRemoveFail() {
        io.print("Order Not Removed");
    }
    //public void displayErrMsg()

    public void displayOrdersByDate(List<Order> orders) {
        for(Order order : orders) {
            displayOrder(order);
        }
    }
    public void displayOrder(Order order) {
        io.print("Order Date: " + order.getOrderDate());
        io.print("Order Number: " + order.getOrderNum());
        io.print("Customer Name: " + order.getCustomerName());
        io.print("State: " + order.getState());
        io.print("Tax Rate: " + order.getTaxRate());
        io.print("Product Type: " + order.getProductType());
        io.print("Area: " + order.getArea());
        io.print("Cost per sq ft: " + order.getCostPerSquareFoot());
        io.print("Labor Cost per sq ft: " + order.getLaborCostPerSquareFoot());
        io.print("Material Cost: " + order.getMaterialCost());
        io.print("Labor Cost: " + order.getLaborCost());
        io.print("Tax: " + order.getTax());
        io.print("TOTAL: " + order.getTotal());
        io.print("\n");
    }

    public String save() {
        return io.readString("Would you like to save order? Y/N: ",1);
    }
    public Order getOrder() {
        Order order = new Order();
        order.setOrderDate(getDate());
        order.setCustomerName(io.readString("Enter Customer Name: "));
        String state = io.readString("Enter State: ");
        order.setState(state.toUpperCase());
        String product = io.readString("Enter Product Type: ");
        product = product.substring(0,1).toUpperCase() + product.substring(1).toLowerCase();
        order.setProductType(product);
        order.setArea(io.readBigDecimal("Enter Area: ", 2));
        return order;
    }

    public LocalDate getDate() {
        return io.readDate("Please enter date (MMDDYYYY): ");
    }
    public LocalDate getDateGeneral() {
        return io.readDateGeneral("Please enter date (MMDDYYYY): ");
    }

    public int inputOrderNum() {
        return io.readInt("Please Enter Order Number: ");
    }

    public Order editOrder(Order updateOrder) {
        io.print("Editing Order Date: " + updateOrder.getOrderDate());
        String customerName = io.readString("Enter Customer Name (" + updateOrder.getCustomerName() + "): ");
        String state = io.readString("Enter State (" + updateOrder.getState() + "): ");
        String product = io.readString("Enter Product Type (" + updateOrder.getProductType() + "): ");
        BigDecimal area = io.readBigDecimal("Enter Area (" + updateOrder.getArea() + "): ", 2);

        if(!customerName.isBlank()) {
            updateOrder.setCustomerName(customerName);
        }
        if(!state.isBlank()) {
            updateOrder.setState(state.toUpperCase());
        }
        if(!product.isBlank()) {
            product = product.substring(0,1).toUpperCase() + product.substring(1).toLowerCase();
            updateOrder.setProductType(product);
        }
        if(!(area == null) && area.compareTo(BigDecimal.ZERO) == 1) {
            updateOrder.setArea(area);
        }
        return updateOrder;
    }

    public String remove() {
        return io.readString("Do you want to remove this order?",1);
    }

    public void displayContinue() {
        io.readString("Please hit enter to continue");
    }
    public void displayUnknownCommand() {
        io.print("Unknown Command!!!");
        displayContinue();
    }
}

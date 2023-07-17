package main.com.sg.flooring.dao;

import main.com.sg.flooring.dto.Order;
import main.com.sg.flooring.service.FloorValidationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FloorDAOImplTest {

    private FloorDAO dao = new FloorDAOImpl();

    @Test
    void getOrdersTest() throws Exception{
        assertEquals(1, dao.getOrders(LocalDate.of(2023,8,8)).size());
        try {
            List<Order> orders = dao.getOrders(LocalDate.of(2023, 8, 8));
            fail("Expected FloorValidationException was not thrown");
        } catch (FloorValidationException e) {
        }
    }

    @Test
    void getOrderTest() throws Exception{
        Order order = dao.getOrder(LocalDate.of(2023,8,8), 1);
        assertNotNull(order);

        try {
            order = dao.getOrder(LocalDate.of(2023, 8, 8), 0);
            fail("Expected FloorPersistenceException was not thrown");
        } catch (FloorPersistenceException e) {

        }
        try {
            dao.getOrder(LocalDate.of(2009, 02, 01), 1);
            fail("Expected FloorPersistenceException was not thrown");
        }catch (FloorPersistenceException e) {

        }
    }

    @Test
    void addOrderTest() throws Exception{
        LocalDate date = LocalDate.parse("07311999", DateTimeFormatter.ofPattern("MMddyyyy"));
        //List<Order> initialOrders = dao.getOrders(date);

        Order order = new Order();
        Order testOrder = new Order();
        order.setOrderDate(date);
        order.setCustomerName("Toan Failin");
        order.setState("TX");
        order.setTaxRate(new BigDecimal("4.45"));
        order.setProductType("Wood");
        order.setArea(new BigDecimal("100"));
        order.setCostPerSquareFoot(new BigDecimal("5.15"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        order.setMaterialCost(order.getCostPerSquareFoot().multiply(order.getArea()).setScale(2, RoundingMode.HALF_UP));
        order.setLaborCost(order.getLaborCostPerSquareFoot().multiply(order.getArea()).setScale(2, RoundingMode.HALF_UP));
        order.setTax(order.getTaxRate().divide(new BigDecimal("100.00")).multiply((order.getMaterialCost().add(order.getLaborCost()))).setScale(2, RoundingMode.HALF_UP));
        order.setTotal(order.getMaterialCost().add(order.getLaborCost()).add(order.getTax()));

        testOrder = dao.addOrder(order);
        //List<Order> fromDAO = dao.getOrders(order.getOrderDate());

        assertNotNull(order);
    }

    @Test
    void orderCalcTest() throws Exception{
        LocalDate date = LocalDate.parse("07311999", DateTimeFormatter.ofPattern("MMddyyyy"));

        Order order = new Order();
        order.setOrderDate(date);
        order.setCustomerName("Toan Failin");
        order.setState("TX");
        order.setTaxRate(new BigDecimal("4.45"));
        order.setProductType("Wood");
        order.setArea(new BigDecimal("100"));
        order.setCostPerSquareFoot(new BigDecimal("5.15"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        order.setMaterialCost(order.getCostPerSquareFoot().multiply(order.getArea()).setScale(2, RoundingMode.HALF_UP));
        order.setLaborCost(order.getLaborCostPerSquareFoot().multiply(order.getArea()).setScale(2, RoundingMode.HALF_UP));
        order.setTax(order.getTaxRate().divide(new BigDecimal("100.00")).multiply((order.getMaterialCost().add(order.getLaborCost()))).setScale(2, RoundingMode.HALF_UP));
        order.setTotal(order.getMaterialCost().add(order.getLaborCost()).add(order.getTax()));

        assertNotNull(order);
    }

    @Test
    void editOrderTest() throws Exception{
        LocalDate date = LocalDate.parse("07311999", DateTimeFormatter.ofPattern("MMddyyyy"));

        Order order = new Order();
        order.setOrderDate(date);
        order.setCustomerName("Toan Failin");
        order.setState("TX");
        order.setTaxRate(new BigDecimal("4.45"));
        order.setProductType("Wood");
        order.setArea(new BigDecimal("100"));
        order.setCostPerSquareFoot(new BigDecimal("5.15"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        order.setMaterialCost(order.getCostPerSquareFoot().multiply(order.getArea()).setScale(2, RoundingMode.HALF_UP));
        order.setLaborCost(order.getLaborCostPerSquareFoot().multiply(order.getArea()).setScale(2, RoundingMode.HALF_UP));
        order.setTax(order.getTaxRate().divide(new BigDecimal("100.00")).multiply((order.getMaterialCost().add(order.getLaborCost()))).setScale(2, RoundingMode.HALF_UP));
        order.setTotal(order.getMaterialCost().add(order.getLaborCost()).add(order.getTax()));

        order = dao.addOrder(order);
        Order editedOrder = order;
        editedOrder.setOrderNum(0);
        editedOrder.setCustomerName("IM GOING TO FAIL");
        dao.editOrder(editedOrder);
        assertNull(editedOrder);
    }

}
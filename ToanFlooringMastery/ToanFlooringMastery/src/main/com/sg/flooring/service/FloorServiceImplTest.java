package main.com.sg.flooring.service;

import main.com.sg.flooring.dao.FloorDAO;
import main.com.sg.flooring.dao.FloorDAOImpl;
import main.com.sg.flooring.dao.FloorPersistenceException;
import main.com.sg.flooring.dto.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FloorServiceImplTest {

    private FloorService service;

    public FloorServiceImplTest() {
        FloorDAO dao = new FloorDAOImpl();

        service = new FloorServiceImpl(dao);
    }
    @Test
    void getOrdersTest() throws Exception{
        assertEquals(1, service.getOrders(LocalDate.of(2023,8,8)).size());
        try {
            List<Order> orders = service.getOrders(LocalDate.of(2009, 02, 01));
            fail("Expected FloorValidationException was not thrown");
        } catch (FloorValidationException e) {
        }
    }

    @Test
    void getOrderTest() throws Exception{
        Order order = service.getOrder(LocalDate.of(2023,8,8), 1);
        assertNotNull(order);

        try {
            order = service.getOrder(LocalDate.of(2023, 8, 8), 0);
            fail("Expected FloorPersistenceException was not thrown");
        } catch (FloorPersistenceException e) {

        }
        try {
            service.getOrder(LocalDate.of(2009, 02, 01), 1);
            fail("Expected FloorPersistenceException was not thrown");
        }catch (FloorPersistenceException e) {

        }
    }

    @Test
    void addOrderTest() throws Exception{
        LocalDate date = LocalDate.parse("08082023", DateTimeFormatter.ofPattern("MMddyyyy"));
        Order order = new Order();
        order.setOrderDate(date);
        order.setCustomerName("Toan Failing");
        order.setState("TX");
        order.setProductType("Wood");
        order.setArea(new BigDecimal("100"));

        assertEquals(order, service.addOrder(order));
    }

    @Test
    void orderCalcTest() throws Exception{
        Order order1 = new Order();
        order1.setCustomerName("Toan Failing");
        order1.setState("TX");
        order1.setProductType("Wood");
        order1.setArea(new BigDecimal("100"));

        Order order2 = new Order();
        order2.setCustomerName("Toan Failing");
        order2.setState("TX");
        order2.setProductType("Wood");
        order2.setArea(new BigDecimal("100"));

        assertEquals(service.orderCalc(order1).getTotal(), service.orderCalc(order2).getTotal());
    }

    @Test
    void removeOrderTest() throws Exception{
        LocalDate date = LocalDate.parse("08082023", DateTimeFormatter.ofPattern("MMddyyyy"));
        Order removedOrder = service.getOrder(date, 1);
        assertNotNull(removedOrder);

        try {
            removedOrder = service.getOrder(date, 0);
            fail("Expected FloorPersistenceException was not thrown");
        } catch (FloorPersistenceException e) {

        }
    }

    @Test
    void saveTest() throws Exception{
        Order order = service.getOrder(LocalDate.of(2023,07,20), 1);
        assertNotNull(order);

        try {
            order = service.getOrder(LocalDate.of(2023,07,20), 0);
            fail("Expected FloorPersistenceException was not thrown");
        } catch (FloorPersistenceException e) {

        }
    }
}
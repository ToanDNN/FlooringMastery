package main.com.sg.flooring.dao;

import main.com.sg.flooring.dto.Order;

import java.time.LocalDate;
import java.util.List;

public interface FloorDAO {
    List<Order> getOrders(LocalDate date) throws FloorPersistenceException;
    Order addOrder(Order order) throws FloorPersistenceException;
    Order editOrder(Order updateOrder) throws FloorPersistenceException;
    Order removeOrder(Order removeOrder) throws FloorPersistenceException;
    //Order orderCalc(Order order) throws FloorPersistenceException;
    Order getOrder(LocalDate date, int orderNum) throws FloorPersistenceException;
    void save(Order order) throws FloorPersistenceException;
}

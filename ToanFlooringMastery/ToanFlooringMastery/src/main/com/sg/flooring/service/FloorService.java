package main.com.sg.flooring.service;

import main.com.sg.flooring.dao.FloorDAO;
import main.com.sg.flooring.dao.FloorPersistenceException;
import main.com.sg.flooring.dto.Order;

import java.time.LocalDate;
import java.util.List;

public interface FloorService {

    List<Order> getOrders(LocalDate date) throws FloorPersistenceException;
    Order getOrder(LocalDate date, int orderNum) throws FloorPersistenceException, FloorValidationException;

    Order addOrder(Order order) throws FloorPersistenceException, FloorValidationException;

    Order orderCalc(Order order) throws FloorPersistenceException, FloorValidationException;

    Order editOrder(Order updateOrder) throws FloorPersistenceException, FloorValidationException;

    Order removeOrder(Order removeOrder) throws FloorPersistenceException, FloorValidationException;

    //void save(Order order) throws FloorPersistenceException;
}

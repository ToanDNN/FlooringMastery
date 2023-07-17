package main.com.sg.flooring.service;

import main.com.sg.flooring.dao.FloorDAO;
import main.com.sg.flooring.dao.FloorPersistenceException;
import main.com.sg.flooring.dto.Order;
import main.com.sg.flooring.dto.Product;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class FloorServiceImpl implements FloorService{
    private FloorDAO dao;
    private static final String DELIMITER = ",";

    public FloorServiceImpl (FloorDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<Order> getOrders(LocalDate date) throws FloorPersistenceException {
        List<Order> ordersByDate = dao.getOrders(date);
        if(!ordersByDate.isEmpty()) {
            return ordersByDate;
        } else {
            throw new FloorPersistenceException("ERROR No Orders Exist for that date");
        }
    }
    @Override
    public Order getOrder(LocalDate date, int orderNum) throws FloorPersistenceException, FloorValidationException {
        List<Order> orders = getOrders(date);
        Order order = orders.stream().filter(o -> o.getOrderNum() == orderNum).findFirst().orElse(null);

        if(order != null) {
            return order;
        } else {
            throw new FloorValidationException("INVALID ORDER");
        }
    }
    @Override
    public Order addOrder(Order order) throws FloorPersistenceException, FloorValidationException {
        dao.addOrder(order);
        return order;
    }
    @Override
    public Order orderCalc(Order order) throws FloorPersistenceException, FloorValidationException {
        HashMap<String, BigDecimal> orderStateTax = loadTax();
        List<Product> productList = LoadProductsFile();

        if(orderStateTax.containsKey(order.getState())) {
            order.setTaxRate(orderStateTax.get(order.getState()).setScale(2, RoundingMode.HALF_UP));
        }
        for (Product p : productList) {
            if(p.getProductType().equals(order.getProductType())) {
                order.setCostPerSquareFoot(p.getCostPerSqFt());
                order.setLaborCostPerSquareFoot(p.getLaborCostPerSqFt().setScale(2, RoundingMode.HALF_UP));
            }
        }

        order.setMaterialCost(order.getCostPerSquareFoot().multiply(order.getArea()).setScale(2,RoundingMode.HALF_UP));
        order.setLaborCost(order.getLaborCostPerSquareFoot().multiply(order.getArea()).setScale(2, RoundingMode.HALF_UP));
        order.setTax(order.getTaxRate().divide(new BigDecimal("100.00")).multiply((order.getMaterialCost().add(order.getLaborCost()))).setScale(2, RoundingMode.HALF_UP));
        order.setTotal(order.getMaterialCost().add(order.getLaborCost()).add(order.getTax()));
        return order;
    }

    private HashMap<String, BigDecimal> loadTax() throws FloorPersistenceException {
        HashMap<String, BigDecimal> stateTaxes = new HashMap<String, BigDecimal>();
        Scanner sc;
        File file = new File("Data/Taxes.txt");
        if (file.isFile()) {
            try {
                sc = new Scanner(new BufferedReader(new FileReader(file)));
            } catch (FileNotFoundException e) {
                throw new FloorPersistenceException("Could not load Taxes file", e);
            }
            String currentLine;
            String[] lineSplit;
            sc.nextLine();
            while (sc.hasNextLine()) {
                currentLine = sc.nextLine();
                lineSplit = currentLine.split(DELIMITER);

                stateTaxes.put(lineSplit[0], new BigDecimal(lineSplit[2]));
            }
        }

        return stateTaxes;
    }
    private List<Product> LoadProductsFile() throws FloorPersistenceException {
        Scanner sc;
        List<Product> products = new ArrayList<>();

        try {
            sc = new Scanner(new BufferedReader(new FileReader("Data/Products.txt")));
        } catch (FileNotFoundException e) {
            throw new FloorPersistenceException(
                    "Could not load products data into memory.", e);
        }

        String currentLine;
        String[] lineSplit;
        while (sc.hasNextLine()) {
            currentLine = sc.nextLine();
            lineSplit = currentLine.split(DELIMITER);
            if (lineSplit.length == 3) {
                Product currentProduct = new Product();
                currentProduct.setProductType(lineSplit[0]);
                currentProduct.setCostPerSqFt(new BigDecimal(lineSplit[1]));
                currentProduct.setLaborCostPerSqFt(new BigDecimal(lineSplit[2]));
                products.add(currentProduct);
            } else {

            }
        }
        sc.close();

        if (!products.isEmpty()) {
            return products;
        } else {
            return null;
        }
    }
    @Override
    public Order editOrder(Order updateOrder) throws FloorPersistenceException, FloorValidationException {
        updateOrder = dao.editOrder(updateOrder);
        if(updateOrder != null) {
            return updateOrder;
        } else {
            throw new FloorValidationException("INVALID ORDER COULD NOT EDIT");
        }
    }
    @Override
    public Order removeOrder(Order removeOrder) throws FloorPersistenceException, FloorValidationException {
        removeOrder = dao.removeOrder(removeOrder);

        if(removeOrder != null) {
            return removeOrder;
        } else {
            throw new FloorValidationException("INVALID ORDER COULD NOT REMOVE");
        }
    }

//    @Override
//    public void save(Order order) throws FloorPersistenceException{
//        dao.save(order);
//    }
}

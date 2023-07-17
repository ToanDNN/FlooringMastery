package main.com.sg.flooring.dao;

import main.com.sg.flooring.dto.Order;
import main.com.sg.flooring.dto.Product;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class FloorDAOImpl implements FloorDAO{
    private int latestOrderNum;
    //private List<Order> orders = new ArrayList<>();
    private HashMap<String, BigDecimal> stateTaxes = new HashMap<String, BigDecimal>();

    private static final String DELIMITER = ",";
    private String filePath = "orders/";

    @Override
    public List<Order> getOrders(LocalDate date) throws FloorPersistenceException {
        return loadOrders(date);
    }
    @Override
    public Order getOrder(LocalDate date, int orderNum) throws FloorPersistenceException {
        List<Order> tempOrderList = getOrders(date);
        Order tempOrder;
        for(int i = 0; i < tempOrderList.size(); i++) {
            if(tempOrderList.get(i).getOrderNum() == orderNum) {
                tempOrder = tempOrderList.get(i);
                return tempOrder;
            }
            else {
                return null;
            }
        }
        return null;
    }

    @Override
    public Order addOrder(Order order) throws FloorPersistenceException {
        getLatestOrderNum();
        latestOrderNum++;
        order.setOrderNum(latestOrderNum);
        setLatestOrderNum(latestOrderNum);
        List<Order> orders = loadOrders(order.getOrderDate());
        orders.add(order);
        writeOrdersToFile(order.getOrderDate(), orders);
        return order;
    }

//    @Override
//    public Order orderCalc(Order order) throws FloorPersistenceException {
//        //String state = order.getState();
//        HashMap<String, BigDecimal> orderStateTax = LoadTaxFile();
//        List<Product> productList = LoadProductsFile();
//
//        if(orderStateTax.containsKey(order.getState())) {
//            order.setTaxRate(orderStateTax.get(order.getState()).setScale(2, RoundingMode.HALF_UP));
//        }
//        for (Product p : productList) {
//            if(p.getProductType().equals(order.getProductType())) {
//                order.setCostPerSquareFoot(p.getCostPerSqFt());
//                order.setLaborCostPerSquareFoot(p.getLaborCostPerSqFt().setScale(2, RoundingMode.HALF_UP));
//            }
//        }
//
//        order.setMaterialCost(order.getCostPerSquareFoot().multiply(order.getArea()).setScale(2,RoundingMode.HALF_UP));
//        order.setLaborCost(order.getLaborCostPerSquareFoot().multiply(order.getArea()).setScale(2, RoundingMode.HALF_UP));
//        order.setTax(order.getTaxRate().divide(new BigDecimal("100.00")).multiply((order.getMaterialCost().add(order.getLaborCost()))).setScale(2, RoundingMode.HALF_UP));
//        order.setTotal(order.getMaterialCost().add(order.getLaborCost()).add(order.getTax()));
//        return order;
//    }

    @Override
    public Order editOrder(Order updateOrder) throws FloorPersistenceException {
        updateOrder = cleanOrder(updateOrder);
        int orderNum = updateOrder.getOrderNum();

        List<Order> orders = loadOrders(updateOrder.getOrderDate());
        Order newOrder = orders.stream().filter(o -> o.getOrderNum() == orderNum).findFirst().orElse(null);

        if(newOrder != null) {
            int index = orders.indexOf(newOrder);
            orders.set(index, updateOrder);
            writeOrdersToFile(updateOrder.getOrderDate(), orders);
            return updateOrder;
        } else {
            return null;
        }
    }

    private Order cleanOrder(Order order) {
        String newCustomerName = order.getCustomerName().replace(DELIMITER, "");
        String newState = order.getState().replace(DELIMITER, "");
        String newProduct = order.getProductType().replace(DELIMITER, "");

        order.setCustomerName(newCustomerName);
        order.setState(newState);
        order.setProductType(newProduct);

        return order;
    }
    @Override
    public void save(Order order) throws FloorPersistenceException {
        PrintWriter pw;
        LocalDate date = order.getOrderDate();
        String fileDate = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
        File file = new File(String.format(filePath + "Orders_%s.txt", fileDate));
        List<Order> orders = loadOrders(order.getOrderDate());

        try {
            pw = new PrintWriter(new FileWriter(file));
        } catch (IOException e) {
            throw new FloorPersistenceException("Could not write/save to file", e);
        }
        pw.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");
        for(Order current : orders) {
            pw.println(current.getOrderNum() + DELIMITER + current.getCustomerName() + DELIMITER + current.getState() + DELIMITER + current.getTaxRate() + DELIMITER + current.getProductType() + DELIMITER + current.getArea() + DELIMITER + current.getCostPerSquareFoot()
                    + DELIMITER + current.getLaborCostPerSquareFoot() + DELIMITER + current.getMaterialCost() + DELIMITER + current.getLaborCost() + DELIMITER + current.getTax() + DELIMITER + current.getTotal());
            pw.flush();
        }
        pw.close();
    }

    @Override
    public Order removeOrder(Order removeOrder) throws FloorPersistenceException {
        System.out.println("HIHI I AM REMOVING ORDER");
        int orderNumber = removeOrder.getOrderNum();

        List<Order> orders = loadOrders(removeOrder.getOrderDate());
        Order removedOrder = orders.stream()
                .filter(o -> o.getOrderNum() == orderNumber)
                .findFirst().orElse(null);

        if (removedOrder != null) {
            orders.remove(removedOrder);
            writeOrdersToFile(removeOrder.getOrderDate(), orders);
            return removedOrder;
        } else {
            return null;
        }
    }

    private void getLatestOrderNum() throws FloorPersistenceException {
        Scanner sc;

        try {
            sc = new Scanner(new BufferedReader(new FileReader(filePath + "OrderNum.txt")));
        } catch (FileNotFoundException e) {
            throw new FloorPersistenceException("Could not load/find file.", e);
        }
        this.latestOrderNum = Integer.parseInt(sc.nextLine());
        sc.close();
    }
    private void setLatestOrderNum(int num) throws FloorPersistenceException {
        PrintWriter pw;

        try {
            pw = new PrintWriter(new FileWriter(filePath + "OrderNum.txt"));
        } catch (IOException e) {
            throw new FloorPersistenceException("Could not write/save to file.", e);
        }
        pw.println(num);
        pw.flush();
        pw.close();
    }

    private void writeOrdersToFile(LocalDate date, List<Order> orderList) throws FloorPersistenceException {
        PrintWriter pw;
        String fileDate = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
        File file = new File(String.format(filePath + "Orders_%s.txt", fileDate));

        try {
            pw = new PrintWriter(new FileWriter(file));
        } catch (IOException e) {
            throw new FloorPersistenceException("Could not write/save to file", e);
        }
        pw.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");
        for(Order current : orderList) {
            pw.println(current.getOrderNum() + DELIMITER + current.getCustomerName() + DELIMITER + current.getState() + DELIMITER + current.getTaxRate() + DELIMITER + current.getProductType() + DELIMITER + current.getArea() + DELIMITER + current.getCostPerSquareFoot()
                    + DELIMITER + current.getLaborCostPerSquareFoot() + DELIMITER + current.getMaterialCost() + DELIMITER + current.getLaborCost() + DELIMITER + current.getTax() + DELIMITER + current.getTotal());
            pw.flush();
        }
        pw.close();
    }

    private List<Order> loadOrders(LocalDate chosenDate) throws FloorPersistenceException {
        Scanner scanner;
        String fileDate = chosenDate.format(DateTimeFormatter.ofPattern("MMddyyyy"));

        File f = new File(String.format(filePath + "Orders_%s.txt", fileDate));

        List<Order> orders = new ArrayList<>();

        if (f.isFile()) {
            try {
                scanner = new Scanner(
                        new BufferedReader(
                                new FileReader(f)));
            } catch (FileNotFoundException e) {
                throw new FloorPersistenceException(
                        "Could not load order file.", e);
            }
            String currentLine;
            String[] currentTokens;
            scanner.nextLine();//Skips scanning document headers
            while (scanner.hasNextLine()) {
                currentLine = scanner.nextLine();
                currentTokens = currentLine.split(DELIMITER);
                if (currentTokens.length == 12) {
                    Order currentOrder = new Order();
                    currentOrder.setOrderDate(LocalDate.parse(fileDate, DateTimeFormatter.ofPattern("MMddyyyy")));
                    currentOrder.setOrderNum(Integer.parseInt(currentTokens[0]));
                    currentOrder.setCustomerName(currentTokens[1]);
                    currentOrder.setState(currentTokens[2]);
                    currentOrder.setTaxRate(new BigDecimal(currentTokens[3]));
                    currentOrder.setProductType(currentTokens[4]);
                    currentOrder.setArea(new BigDecimal(currentTokens[5]));
                    currentOrder.setCostPerSquareFoot(new BigDecimal(currentTokens[6]));
                    currentOrder.setLaborCostPerSquareFoot(new BigDecimal(currentTokens[7]));
                    currentOrder.setMaterialCost(new BigDecimal(currentTokens[8]));
                    currentOrder.setLaborCost(new BigDecimal(currentTokens[9]));
                    currentOrder.setTax(new BigDecimal(currentTokens[10]));
                    currentOrder.setTotal(new BigDecimal(currentTokens[11]));
                    orders.add(currentOrder);
                } else {

                }
            }
            scanner.close();
            return orders;
        } else {
            return orders;
        }
    }
//    public HashMap<String, BigDecimal> LoadTaxFile() throws FloorPersistenceException {
//        Scanner sc;
//        File file = new File("Data/Taxes.txt");
//        if (file.isFile()) {
//            try {
//                sc = new Scanner(new BufferedReader(new FileReader(file)));
//            } catch (FileNotFoundException e) {
//                throw new FloorPersistenceException("Could not load Taxes file", e);
//            }
//            String currentLine;
//            String[] lineSplit;
//            sc.nextLine();
//            while (sc.hasNextLine()) {
//                currentLine = sc.nextLine();
//                lineSplit = currentLine.split(DELIMITER);
//
//                stateTaxes.put(lineSplit[0], new BigDecimal(lineSplit[2]));
//            }
//        }
//        return stateTaxes;
//    }

//    private List<Product> LoadProductsFile() throws FloorPersistenceException {
//        Scanner sc;
//        List<Product> products = new ArrayList<>();
//
//        try {
//            sc = new Scanner(new BufferedReader(new FileReader("Data/Products.txt")));
//        } catch (FileNotFoundException e) {
//            throw new FloorPersistenceException(
//                    "Could not load products data into memory.", e);
//        }
//
//        String currentLine;
//        String[] lineSplit;
//        while (sc.hasNextLine()) {
//            currentLine = sc.nextLine();
//            lineSplit = currentLine.split(DELIMITER);
//            if (lineSplit.length == 3) {
//                Product currentProduct = new Product();
//                currentProduct.setProductType(lineSplit[0]);
//                currentProduct.setCostPerSqFt(new BigDecimal(lineSplit[1]));
//                currentProduct.setLaborCostPerSqFt(new BigDecimal(lineSplit[2]));
//                products.add(currentProduct);
//            } else {
//
//            }
//        }
//        sc.close();
//
//        if (!products.isEmpty()) {
//            return products;
//        } else {
//            return null;
//        }
//    }
}

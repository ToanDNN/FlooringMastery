package main.com.sg.flooring.controller;

import main.com.sg.flooring.dao.FloorPersistenceException;
import main.com.sg.flooring.dto.Order;
import main.com.sg.flooring.service.FloorService;
import main.com.sg.flooring.service.FloorValidationException;
import main.com.sg.flooring.ui.FloorView;

import java.time.LocalDate;
import java.util.List;

public class FlooringMasteryController {
    private FloorService service;
    private FloorView view;

    public FlooringMasteryController(FloorService service, FloorView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        view.displayFloorBanner();

        try {
            while(true) {
                int choice = view.menuSelection();

                switch(choice) {
                    case 1: //display
                        getOrders();
                        break;
                    case 2: //add order
                        addOrder();
                        break;
                    case 3: //edit order
                        editOrder();
                        break;
                    case 4: //remove order
                        removeOrder();
                        break;
                    case 5: //quit
                        System.out.println("EXIT");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("UNKNOWN COMMAND");
                }
            }
        } catch (FloorPersistenceException e) {
            System.out.println("ERROR");
        }
    }

    private void getOrders() throws FloorPersistenceException {
        view.displayOrderBanner();
        LocalDate inputDate = view.getDateGeneral();
        view.displayDateBanner(inputDate);
        try {
            List<Order> test = service.getOrders(inputDate);
            view.displayOrdersByDate(test);
            view.displayContinue();
        } catch (FloorValidationException e){
            throw new FloorValidationException("ERROR Could not find date", e);
        }
        view.displayContinue();
    }

    private void addOrder() throws FloorPersistenceException {
        view.displayAddOrderBanner();
        try {
            Order order = view.getOrder();
            service.orderCalc(order);
            boolean checkInput = false;
            while(!checkInput) {
                String input = view.save();
                if(input.length() <= 1) {
                    if (input.equalsIgnoreCase("Y")) {
                        service.addOrder(order);
                        view.displayAddOrderSuccess();
                        view.displayContinue();
                        checkInput = true;
                    } else if (input.equalsIgnoreCase("N")) {
                        view.displayContinue();
                        checkInput = true;
                    }
                } else {
                    System.out.println("Invalid Input, must enter Y/N");
                }
            }
        } catch (FloorPersistenceException e) {
            throw new FloorPersistenceException("ERROR Could not add order", e);
        }
    }

    private void editOrder() throws FloorPersistenceException {
        view.displayEditOrderBanner();
        try {
            LocalDate date = view.getDate();
            view.displayOrdersByDate(service.getOrders(date));
            int orderNum = view.inputOrderNum();
            Order savedOrder = service.getOrder(date,orderNum);
            Order editedOrder = view.editOrder(savedOrder);
            view.displayEditOrderBanner();
            view.displayOrder(editedOrder);
            String input = view.save();
            if(input.equalsIgnoreCase("Y")) {
                service.editOrder(editedOrder);
                view.displayEditSuccess();
            } else if (input.equalsIgnoreCase("N")){
                view.displayEditFail();
            } else {
                view.displayUnknownCommand();
            }
        } catch (FloorPersistenceException e) {
            throw new FloorPersistenceException("ERROR could not edit order", e);
        }
    }

    private void removeOrder() throws FloorPersistenceException {
        view.displayRemoveOrderBanner();
        LocalDate date = view.getDate();
        boolean checkInput = false;
        try {
            view.displayOrdersByDate(service.getOrders(date));
            view.displayRemoveOrderBanner();
            view.displayOrdersByDate(service.getOrders(date));
            int orderNum = view.inputOrderNum();
            Order order = service.getOrder(date,orderNum);
            view.displayRemoveOrderBanner();
            view.displayOrder(order);
            String input = view.remove();
            if(input.equalsIgnoreCase("Y")) {
                service.removeOrder(order);
            } else if (input.equalsIgnoreCase("N")) {
                view.displayRemoveFail();
            } else {
                view.displayUnknownCommand();
            }
        } catch (FloorPersistenceException e) {
            throw new FloorPersistenceException("ERROR could not remove order", e);
        }
    }
}

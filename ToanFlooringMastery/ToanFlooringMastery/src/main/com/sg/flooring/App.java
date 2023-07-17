package main.com.sg.flooring;

import main.com.sg.flooring.controller.FlooringMasteryController;
import main.com.sg.flooring.dao.FloorDAO;
import main.com.sg.flooring.dao.FloorDAOImpl;
import main.com.sg.flooring.service.FloorService;
import main.com.sg.flooring.ui.FloorView;
import main.com.sg.flooring.ui.UserIO;
import main.com.sg.flooring.ui.UserIOConsoleImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
//        UserIO io = new UserIOConsoleImpl();
//        FloorView view = new FloorView(io);
//
//        FloorDAO dao = new FloorDAOImpl();
//        FloorService service = new FloorService(dao);
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        FlooringMasteryController controller = context.getBean("controller", FlooringMasteryController.class);
        controller.run();
    }
}

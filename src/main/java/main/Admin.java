package main;

import dao.UserDao;
import menu.Menu;
import service.restaurant.RestaurantAdminManager;
import exception.DAOLayerException;
import static constant.Constants.*;

public class Admin {

    public static void adminFunc() {

        int choice = Menu.adminMenu();
        switch (choice) {
            case USER_MANAGEMENT:
                Admin.displayUsers();
                break;

            case RESTAURANT_MANAGEMENT:
                RestaurantAdminManager.restAdminFunc();
                break;

            case ADMIN_LOGOUT:
                OnlineFoodDelivery.main(null);
                break;

            case EXIT:
                ProgramTerminator.exit();
                break;

            default:
                System.out.println("Invalid choice!!");
                Admin.adminFunc();
        }
    }

    public static void displayUsers() {
        try {
            int choice = Menu.displayUserMenu();

            switch (choice) {
                case VIEW_ADMIN:
                    UserDao.getUsersDetails(1);
                    break;

                case VIEW_RESTAURANT_OWNER:
                    UserDao.getUsersDetails(2);
                    break;

                case VIEW_CUSTOMER:
                    UserDao.getUsersDetails(3);
                    break;

                case VIEW_DELIVERY_AGENT:
                    UserDao.getUsersDetails(4);
                    break;

                case VIEW_BACK_ADMIN_MENU:
                    adminFunc();
                    break;

                case EXIT:
                    ProgramTerminator.exit();
                    break;

                default:
                    System.out.println("Invalid choice!!");
                    System.out.println("Please select valid option from below");
            }
            Admin.displayUsers();
        } catch (DAOLayerException e) {
               e.printStackTrace();
        }
    }
}

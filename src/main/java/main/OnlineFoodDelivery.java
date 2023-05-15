package main;

import enums.UserRoles;
import menu.Menu;
import service.user.UserService;
import service.restaurant.RestaurantAdminManager;
import session.CurrentUserCredentials;

import static constant.Constants.*;

public class OnlineFoodDelivery {

    static {
        System.out.println("\n-------Welcome to Foodies-------\n");
    }

    public static void main(String[] args) {
        int choice = Menu.loginSignupMenu();

        switch (choice) {
            case MENU_SIGNUP:
                UserService.signUpUser();
                break;

            case MENU_LOGIN:
                UserService.loginUser();
                break;

            case EXIT:
                ProgramTerminator.exit();
                break;

            default:
                System.out.println("Invalid Choice!!");
                main(null);
        }
    }
}

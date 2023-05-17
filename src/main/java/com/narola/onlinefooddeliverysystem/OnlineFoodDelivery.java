package com.narola.onlinefooddeliverysystem;

import com.narola.onlinefooddeliverysystem.menu.Menu;
import com.narola.onlinefooddeliverysystem.model.service.user.UserManager;
import com.narola.onlinefooddeliverysystem.utility.ProgramTerminator;

import static com.narola.onlinefooddeliverysystem.constant.Constants.*;

public class OnlineFoodDelivery {

    static {
        System.out.println("\n-------Welcome to Foodies-------\n");
    }

    public static void main(String[] args) {
        int choice = Menu.displayMainMenuAndTakeInput();

        switch (choice) {
            case MENU_SIGNUP:
                UserManager.signUpUser();
                break;

            case MENU_LOGIN:
                UserManager.loginUser();
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

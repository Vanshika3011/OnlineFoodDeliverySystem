package com.narola.onlinefooddeliverysystemV1;

import com.narola.onlinefooddeliverysystem.menu.Menu;
import com.narola.onlinefooddeliverysystem.utility.ProgramTerminator;
import com.narola.onlinefooddeliverysystemV1.manager.UserManager;

import static com.narola.onlinefooddeliverysystem.constant.Constants.*;

public class OnlineFoodDelivery {

    static {
        System.out.println("\n-------Welcome to Foodies-------\n");
    }

    public static void main(String[] args) {
        int choice = Menu.displayMainMenuAndTakeInput();
        UserManager userManager = new UserManager();
        switch (choice) {
            case MENU_SIGNUP:
                userManager.doSignup();
                break;
            case MENU_LOGIN:
                userManager.doLogin();
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

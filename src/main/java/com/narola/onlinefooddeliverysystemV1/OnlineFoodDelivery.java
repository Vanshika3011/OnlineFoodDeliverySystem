package com.narola.onlinefooddeliverysystemV1;

import com.narola.onlinefooddeliverysystemV1.menu.AppMenu;
import com.narola.onlinefooddeliverysystemV1.utility.ProgramTerminator;
import com.narola.onlinefooddeliverysystemV1.manager.UserManager;

import static com.narola.onlinefooddeliverysystemV1.constant.Constants.*;

public class OnlineFoodDelivery {

    public static UserManager userManager = new UserManager();

    static {
        System.out.println("\n-------Welcome to Foodies-------\n");
    }

    public static void main(String[] args) {
        int choice = AppMenu.displayMainMenuAndTakeInput();
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
        main(null);
    }
}

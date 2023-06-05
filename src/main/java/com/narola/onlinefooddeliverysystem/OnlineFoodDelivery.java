/*
package com.narola.onlinefooddeliverysystem;

import com.narola.onlinefooddeliverysystemV1.menu.Menu;
import com.narola.onlinefooddeliverysystemV1.utility.ProgramTerminator;

import static com.narola.onlinefooddeliverysystemV1.constant.Constants.*;

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
*/

/*
package com.narola.onlinefooddeliverysystem.service;

import com.narola.onlinefooddeliverysystemV1.constant.Constants;
import com.narola.onlinefooddeliverysystem.dao.UserDao;
import com.narola.onlinefooddeliverysystemV1.exception.DAOLayerException;
import com.narola.onlinefooddeliverysystemV1.menu.Menu;
import com.narola.onlinefooddeliverysystem.OnlineFoodDelivery;
import com.narola.onlinefooddeliverysystemV1.model.User;
import com.narola.onlinefooddeliverysystemV1.utility.ProgramTerminator;


import java.util.List;

import static com.narola.onlinefooddeliverysystemV1.constant.Constants.*;
import static com.narola.onlinefooddeliverysystemV1.view.UserView.printUsersDetails;

public class Admin {

    public static void adminFunc() {

        int choice = Menu.adminMenu();
        switch (choice) {
            case Constants.USER_MANAGEMENT:
                int input = Menu.displayUserManagementMenuAndTakeInput();
                if(input==VIEW_USERS_DETAILS_BY_ROLE){
                    try {
                        int userRoleID = Menu.displayUserDetailsMenuAndTakeInput();
                        printUserDetails(userRoleID);
                    } catch (DAOLayerException e) {
                        e.printStackTrace();
                    }
                }
                else if(input==BACK_TO_ADMIN_MAIN_MENU){
                    Admin.adminFunc();
                }
                else if(input == EXIT){
                    ProgramTerminator.exit();
                }
                break;

            case Constants.RESTAURANT_MANAGEMENT:
//                RestaurantAdminManager.restAdminFunc();
                break;

            case Constants.ADMIN_LOGOUT:
                OnlineFoodDelivery.main(null);
                break;

            case EXIT:
                ProgramTerminator.exit();
                break;

            default:
                System.out.println("Invalid choice!!");
        }
        Admin.adminFunc();
    }


    public static void printUserDetails(int userRoleID) throws DAOLayerException {
        List<User> userList = UserDao.getUsersDetails(userRoleID);
        printUsersDetails(userList);
    }
}
*/

package com.narola.onlinefooddeliverysystem.model.service.restaurant;

import com.narola.onlinefooddeliverysystem.constant.Constants;
import com.narola.onlinefooddeliverysystem.dao.RestaurantDao;
import com.narola.onlinefooddeliverysystem.dao.UserDao;
import com.narola.onlinefooddeliverysystem.enums.UserRoles;
import com.narola.onlinefooddeliverysystem.exception.DAOLayerException;
import com.narola.onlinefooddeliverysystem.input.InputHandler;
import com.narola.onlinefooddeliverysystem.menu.Menu;
import com.narola.onlinefooddeliverysystem.model.service.Admin;
import com.narola.onlinefooddeliverysystem.session.CurrentUserCredentials;
import com.narola.onlinefooddeliverysystem.OnlineFoodDelivery;
import com.narola.onlinefooddeliverysystem.utility.ProgramTerminator;

public class RestaurantAdminManager {

    public static void restAdminFunc() {
        try {
            int adminChoice = Menu.restaurantAdminMenu();

            switch (adminChoice) {
                case Constants.REGISTER_REST:
                    RestaurantService.addRestaurant();
                    break;

                case Constants.VIEW_REST_OWNED:
                    RestaurantDao.viewCurrentUsersRestaurants(CurrentUserCredentials.currentUser.getUserId());
                    break;

                case Constants.VIEW_REST_DETAILS:
                    if (CurrentUserCredentials.getCurrentUser().getRoleId() == UserRoles.ADMIN.getRoleIdValue()) {
                        RestaurantDao.getAllRestaurants();
                    } else {
                        System.out.println("List of restaurants owned");
                        RestaurantDao.viewCurrentUsersRestaurants(CurrentUserCredentials.currentUser.getUserId());
                    }
                    System.out.println("Enter Restaurant id for viewing its details");
                    int restaurantId = InputHandler.getNumberInput();
                    RestaurantDao.viewRestaurantDetails(restaurantId);
                    break;

                case Constants.EDIT_OWNED_REST_DETAILS:
                    System.out.println("List of restaurants owned");
                    RestaurantDao.viewCurrentUsersRestaurants(CurrentUserCredentials.currentUser.getUserId());
                    RestaurantService.editRestaurantDetails(CurrentUserCredentials.currentUser.getUserId(), CurrentUserCredentials.getCurrentUser().getRoleId());
                    break;

                case Constants.EDIT_REST_DETAILS_OR_REST_ADMIN_LOGOUT:
                    if (CurrentUserCredentials.getCurrentUser().getRoleId() == UserRoles.ADMIN.getRoleIdValue()) {
                        System.out.println("Edit Restaurant details");
                        RestaurantDao.getAllRestaurants();
                        RestaurantService.editRestaurantDetails(CurrentUserCredentials.currentUser.getUserId(), CurrentUserCredentials.getCurrentUser().getRoleId());
                    } else {
                        OnlineFoodDelivery.main(null);
                        break;
                    }
                    break;

                case Constants.VIEW_ALL_REST:
                    if (CurrentUserCredentials.getCurrentUser().getRoleId() == UserRoles.ADMIN.getRoleIdValue()) {
                        System.out.println("View All Restaurant details");
                        RestaurantDao.getAllRestaurants();
                    } else {
                        System.out.println("Invalid choice!!");
                        RestaurantAdminManager.restAdminFunc();
                    }
                    break;

                case Constants.VIEW_REST_OWNED_BY_ID:
                    int viewRest = 0;
                    if (CurrentUserCredentials.getCurrentUser().getRoleId() == UserRoles.ADMIN.getRoleIdValue()) {
                        UserDao.getUsersDetails(2);
                        System.out.println("Enter id of restaurant owner : ");
                        viewRest = InputHandler.getNumberInput();
                        RestaurantDao.viewCurrentUsersRestaurants(viewRest);
                    } else {
                        System.out.println("Please select valid choice");
                        RestaurantAdminManager.restAdminFunc();
                    }
                    break;

                case Constants.BACK_TO_ADMIN_MENU:
                    if (CurrentUserCredentials.getCurrentUser().getRoleId() == UserRoles.ADMIN.getRoleIdValue()) {
                        Admin.adminFunc();
                    } else {
                        System.out.println("Please select valid choice");
                        RestaurantAdminManager.restAdminFunc();
                    }
                    break;

                case Constants.EXIT:
                    ProgramTerminator.exit();
                    break;

                default:
                    System.out.println("Invalid choice!\n");
                    System.out.println("Please select valid choice");
            }
            RestaurantAdminManager.restAdminFunc();
        } catch (DAOLayerException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

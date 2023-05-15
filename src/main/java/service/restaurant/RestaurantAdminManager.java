package service.restaurant;

import dao.RestaurantDao;
import dao.UserDao;
import enums.UserRoles;
import input.InputHandler;
import main.Admin;
import main.OnlineFoodDelivery;
import main.ProgramTerminator;
import menu.Menu;
import session.CurrentUserCredentials;
import exception.DAOLayerException;

import static constant.Constants.*;

public class RestaurantAdminManager {

    public static void restAdminFunc() {
        try {
            int adminChoice = Menu.restaurantAdminMenu();

            switch (adminChoice) {
                case REGISTER_REST:
                    RestaurantService.addRestaurant();
                    break;

                case VIEW_REST_OWNED:
                    RestaurantDao.viewCurrentUsersRestaurants(CurrentUserCredentials.currentUser.getUserId());
                    break;

                case VIEW_REST_DETAILS:
                    if (CurrentUserCredentials.getCurrentUser().getRoleId() == UserRoles.ADMIN.getRoleIdValue()) {
                        RestaurantDao.viewAllRestaurants();
                    } else {
                        System.out.println("List of restaurants owned");
                        RestaurantDao.viewCurrentUsersRestaurants(CurrentUserCredentials.currentUser.getUserId());
                    }
                    System.out.println("Enter Restaurant id for viewing its details");
                    int restaurantId = InputHandler.getNumberInput();
                    RestaurantDao.viewRestaurantDetails(restaurantId);
                    break;

                case EDIT_OWNED_REST_DETAILS:
                    System.out.println("List of restaurants owned");
                    RestaurantDao.viewCurrentUsersRestaurants(CurrentUserCredentials.currentUser.getUserId());
                    RestaurantService.editRestaurantDetails(CurrentUserCredentials.currentUser.getUserId(), CurrentUserCredentials.getCurrentUser().getRoleId());
                    break;

                case EDIT_REST_DETAILS_OR_REST_ADMIN_LOGOUT:
                    if (CurrentUserCredentials.getCurrentUser().getRoleId() == UserRoles.ADMIN.getRoleIdValue()) {
                        System.out.println("Edit Restaurant details");
                        RestaurantDao.viewAllRestaurants();
                        RestaurantService.editRestaurantDetails(CurrentUserCredentials.currentUser.getUserId(), CurrentUserCredentials.getCurrentUser().getRoleId());
                    } else {
                        OnlineFoodDelivery.main(null);
                        break;
                    }
                    break;

                case VIEW_ALL_REST:
                    if (CurrentUserCredentials.getCurrentUser().getRoleId() == UserRoles.ADMIN.getRoleIdValue()) {
                        System.out.println("View All Restaurant details");
                        RestaurantDao.viewAllRestaurants();
                    } else {
                        System.out.println("Invalid choice!!");
                        RestaurantAdminManager.restAdminFunc();
                    }
                    break;

                case VIEW_REST_OWNED_BY_ID:
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

                case BACK_TO_ADMIN_MENU:
                    if (CurrentUserCredentials.getCurrentUser().getRoleId() == UserRoles.ADMIN.getRoleIdValue()) {
                        Admin.adminFunc();
                    } else {
                        System.out.println("Please select valid choice");
                        RestaurantAdminManager.restAdminFunc();
                    }
                    break;

                case EXIT:
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

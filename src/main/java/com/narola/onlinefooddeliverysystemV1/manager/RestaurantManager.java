package com.narola.onlinefooddeliverysystemV1.manager;

import com.narola.onlinefooddeliverysystemV1.OnlineFoodDelivery;
import com.narola.onlinefooddeliverysystemV1.exception.DAOLayerException;
import com.narola.onlinefooddeliverysystemV1.menu.AppMenu;
import com.narola.onlinefooddeliverysystemV1.service.RestaurantService;
import com.narola.onlinefooddeliverysystemV1.session.CurrentUserCredentials;
import com.narola.onlinefooddeliverysystemV1.utility.ProgramTerminator;
import com.narola.onlinefooddeliverysystemV1.view.RestaurantView;
import com.narola.onlinefooddeliverysystemV1.view.UserView;

import static com.narola.onlinefooddeliverysystemV1.constant.Constants.*;
import static com.narola.onlinefooddeliverysystemV1.menu.AppMenu.displayEditRestaurantMenuAndTakeInputFromRestaurantAdmin;

public class RestaurantManager {

    private RestaurantService restaurantService = new RestaurantService();
    private RestaurantMenuManager restaurantMenuManager = new RestaurantMenuManager();
    private RestaurantView restaurantView = new RestaurantView();
    private UserView userView = new UserView();
    private AppMenu appMenu = new AppMenu();

    public void handleRestaurantAdminActions() {
        int adminChoice = appMenu.displayRestaurantManagementMenuAndTakeInputFromRestaurantAdmin();
        try {
            switch (adminChoice) {
                case REGISTER_REST:
                    restaurantService.addRestaurant();
                    break;

                case VIEW_REST_OWNED:
                    restaurantService.getRestaurantList(CurrentUserCredentials.currentUser.getUserId());
                    break;

                case VIEW_REST_DETAILS:
                    restaurantService.getRestaurantDetails();
                    break;

                case EDIT_OWNED_REST_DETAILS:
                    handleEditRestaurantDetailsByRestaurantAdmin();
                    break;

                case ADD_RESTAURANT_MENU_ITEMS:
                    restaurantMenuManager.addMenu();
                    break;

                case VIEW_RESTAURANT_MENU_ITEMS:
                    restaurantMenuManager.viewMenu();
                    break;

                case GO_BACK_TO_LOGIN_SIGNUP:
                    OnlineFoodDelivery.main(null);
                    break;

                case EXIT:
                    ProgramTerminator.exit();
                    break;
                default:
                    restaurantView.displayErrorMessage("Invalid choice!\n");
                    restaurantView.displayInfoMessage("Please select valid choice");
            }
            handleRestaurantAdminActions();
        } catch (Exception e) {
            System.out.println("Invalid Choice");
        }
    }

    public void handleEditRestaurantDetailsByRestaurantAdmin() {
        try {
            restaurantService.getRestaurantDetails();
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        int choice = appMenu.displayEditRestaurantMenuAndTakeInputFromRestaurantAdmin();
        switch (choice) {
            case EDIT_RESTAURANT_DETAILS:
                restaurantService.editRestaurantDetails();
                break;
            case EDIT_RESTAURANT_ADDRESS_DETAILS:
                restaurantService.editRestaurantAddressDetails();
                break;
            case BACK_TO_RESTAURANT_ADMIN_MAIN_MENU:
                handleRestaurantAdminActions();
                break;
            case EXIT:
                ProgramTerminator.exit();
                break;
            default:
                restaurantView.displayErrorMessage("Invalid Choice");
        }
    }

    public void getRestaurantListByOwnerId() {
        int ownerID = userView.displayRestaurantAdminsAndGetOwnerIdFromAdmin();
        restaurantService.getRestaurantList(ownerID);
    }

    public void getRestaurantDetailsByRestaurantId() {
        int restaurantId = restaurantView.displayRestaurantAndGetRestaurantIdFromAdmin();
        restaurantService.getRestaurantDetails(restaurantId);
    }

    public void getAllRestaurantList() {
        restaurantService.getRestaurantList();
    }
}

package com.narola.onlinefooddeliverysystemV1.session;

import com.narola.onlinefooddeliverysystemV1.dao.RestaurantDao;
import com.narola.onlinefooddeliverysystemV1.enums.UserRoles;
import com.narola.onlinefooddeliverysystemV1.exception.DAOLayerException;
import com.narola.onlinefooddeliverysystemV1.model.User;

public class CurrentUserCredentials {
    private static RestaurantDao restaurantDao = new RestaurantDao();

    public static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        CurrentUserCredentials.currentUser = currentUser;
    }

    public static boolean isRestaurantAdmin() {
        return currentUser.getRoleId() == UserRoles.RESTAURANTADMIN.getRoleIdValue();
    }

    public static int getRestaurantIdOfUser() throws DAOLayerException {
        int restaurantId = 0;
        if (isRestaurantAdmin()) {
            restaurantId = restaurantDao.getRestaurantIdOfCurrentUser(CurrentUserCredentials.getCurrentUser().getUserId());
        } return restaurantId;
    }
}

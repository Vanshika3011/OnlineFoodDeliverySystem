package com.narola.onlinefooddeliverysystemV1.service;

import com.narola.onlinefooddeliverysystemV1.dao.LocationDao;
import com.narola.onlinefooddeliverysystemV1.exception.DAOLayerException;
import com.narola.onlinefooddeliverysystemV1.model.City;
import com.narola.onlinefooddeliverysystemV1.model.Restaurant;
import com.narola.onlinefooddeliverysystemV1.model.State;
import com.narola.onlinefooddeliverysystemV1.session.CurrentUserCredentials;
import com.narola.onlinefooddeliverysystemV1.dao.RestaurantDao;
import com.narola.onlinefooddeliverysystemV1.view.RestaurantView;

import java.util.Collections;
import java.util.List;

public class RestaurantService {

    private RestaurantDao restaurantDao = new RestaurantDao();
    private static RestaurantView restaurantView = new RestaurantView();
    private LocationDao locationDao = new LocationDao();

    public void getRestaurantDetails() throws DAOLayerException {
        try {
            int restaurantId = restaurantDao.getRestaurantIdOfCurrentUser(CurrentUserCredentials.getCurrentUser().getUserId());
            Restaurant restaurant = restaurantDao.getAllRestaurantsDetailsByRestaurantID(restaurantId);

            if (restaurant != null) {
                restaurantView.printRestaurantDetails(restaurant);
            } else
                restaurantView.displayErrorMessage("Restaurant doesn't exist");
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    public void addRestaurant() {
        try {
            if (restaurantDao.doesUserOwnRestaurant()) {
                restaurantView.displayErrorMessage("Cannot add another Restaurant.");
            } else {
                Restaurant restaurant = restaurantView.getRestaurantDetailsToRegister();
                restaurant = restaurantView.getRestaurantAddressToRegister(restaurant);
                int addressID = getAddressIdForRestaurant(restaurant);

                int isAdded = restaurantDao.doAddRestaurant(restaurant, addressID);
                if (isAdded > 0) {
                    restaurantView.displayInfoMessage("Restaurant registered successfully");
                } else
                    restaurantView.displayErrorMessage("Restaurant registration failed.");
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    public int getAddressIdForRestaurant(Restaurant restaurant) {
        int generatedId = 0;
        try {
            generatedId = restaurantDao.doAddRestaurantAddressAndGetAddressId(restaurant);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    public boolean doesUserOwnsRestaurant() {
        try {
            return restaurantDao.doesUserOwnRestaurant();
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void editRestaurantDetails() {
        if (!doesUserOwnsRestaurant()) {
            restaurantView.displayErrorMessage("Restaurant does not Exist.PLease register a restaurant.");
            return;
        }
        try {
            int restaurantId = CurrentUserCredentials.getRestaurantIdOfUser();
            Restaurant restaurant = restaurantDao.getAllRestaurantsDetailsByRestaurantID(restaurantId);

            restaurantView.getRestUpdateDetails(restaurant);

            int isUpdate = restaurantDao.editRestaurantDetails(restaurant, restaurantId, CurrentUserCredentials.currentUser.getUserId());
            if (isUpdate > 0)
                restaurantView.displayInfoMessage("Restaurant details updated successfully.");
            else
                restaurantView.displayErrorMessage("Restaurant details not updated");
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    public void editRestaurantAddressDetails() {
        if (!doesUserOwnsRestaurant()) {
            restaurantView.displayErrorMessage("Restaurant does not Exist.PLease register a restaurant.");
            return;
        }
        try {
            int restaurantId = CurrentUserCredentials.getRestaurantIdOfUser();
            Restaurant restaurant = restaurantDao.getAllRestaurantsDetailsByRestaurantID(restaurantId);

            restaurant = restaurantView.getRestAddUpdateDetails(restaurant);

            int isUpdate = restaurantDao.editRestaurantAddress(restaurant);
            if (isUpdate > 0)
                restaurantView.displayInfoMessage("Restaurant Address details updated successfully.");
            else
                restaurantView.displayErrorMessage("Restaurant Address details not updated");
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    public void getRestaurantList() {
        restaurantView.displayInfoMessage("Restaurants list");
        List<Restaurant> restaurantList = null;
        try {
            restaurantList = restaurantDao.getAllRestaurantsDetails();
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        restaurantView.printRestaurantList(restaurantList);
    }

    public void getRestaurantList(int ownerID) {
        restaurantView.displayInfoMessage("Restaurants list");
        List<Restaurant> restaurantList = null;
        try {
            restaurantList = restaurantDao.getAllRestaurantsDetails(ownerID);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        restaurantView.printRestaurantList(restaurantList);
    }

    public void getRestaurantDetails(int restaurantID) {
        Restaurant restaurant = null;
        try {
            restaurant = restaurantDao.getAllRestaurantsDetailsByRestaurantID(restaurantID);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        if (restaurant != null) {
            restaurantView.printRestaurantDetails(restaurant);
        } else
            restaurantView.displayErrorMessage("Invalid Restaurant Id.");
    }

    public List<State> getListOfState() {
        try {
            return locationDao.getStates();
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<City> getListOfCity(int stateId) {
        try {
            return locationDao.getCities(stateId);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
















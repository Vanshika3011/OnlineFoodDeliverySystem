package com.narola.onlinefooddeliverysystemV1.view;

import com.narola.onlinefooddeliverysystemV1.dao.LocationDao;
import com.narola.onlinefooddeliverysystemV1.input.InputHandler;
import com.narola.onlinefooddeliverysystemV1.model.City;
import com.narola.onlinefooddeliverysystemV1.model.Restaurant;
import com.narola.onlinefooddeliverysystemV1.model.RestaurantAddress;
import com.narola.onlinefooddeliverysystemV1.model.State;
import com.narola.onlinefooddeliverysystemV1.service.RestaurantService;
import com.narola.onlinefooddeliverysystemV1.validation.Validation;

import java.util.List;
import java.util.Map;

public class RestaurantView {

    private Restaurant restaurant = new Restaurant();
    private RestaurantAddress restaurantAddress = new RestaurantAddress();
    private RestaurantService restaurantService = new RestaurantService();
    private LocationDao locationDao = new LocationDao();

    public void printRestaurantList(List<Restaurant> restaurantList) {
        if (restaurantList == null || restaurantList.isEmpty()) {
            System.out.println("No restaurants exist.");
            return;
        }
        System.out.println("Restaurant ID\tRestaurant Name");
        for (Restaurant restaurant : restaurantList) {
            System.out.println(restaurant.getRestaurantId() + "\t\t\t\t" + restaurant.getRestaurantName());
        }
    }

    public void printRestaurantDetails(Restaurant restaurant) {
        System.out.println("Details of " + restaurant.getRestaurantName());
        System.out.println("Name                : " + restaurant.getRestaurantName());
        System.out.println("Contact No          : " + restaurant.getRestaurantContact());
        System.out.println("Email Id            : " + restaurant.getRestaurantEmail());
        System.out.println("Address             : " + restaurant.getRestaurantAddress().getRestaurantAddLine1() + ", " + restaurant.getRestaurantAddress().getRestaurantAddLine2());
        System.out.println("City                : " + restaurant.getRestaurantAddress().getCity().getCityName() + ", " + restaurant.getRestaurantAddress().getPincode());
        System.out.println("State               : " + restaurant.getRestaurantAddress().getState().getStateName());
        System.out.println("Hours of Operation  : " + restaurant.getOpensAt() + " to " + restaurant.getClosesAt());
        System.out.println("GST number          : " + restaurant.getGstNo());
    }

    public int displayRestaurantAndGetRestaurantIdFromAdmin() {
        restaurantService.getRestaurantList();
        System.out.println("Enter Restaurant Id : ");
        return InputHandler.getNumberInput();
    }

    public void displayErrorMessage(String message) {
        System.out.println("ERROR:" + message);
    }

    public void displayInfoMessage(String message) {
        System.out.println("INFO:" + message);
    }

    public Restaurant getRestaurantDetailsToRegister() {

        System.out.println("Enter restaurant name : ");
        restaurant.setRestaurantName(InputHandler.getStringInput());

        System.out.println("Enter restaurant contact number : ");
        String contactNumber = InputHandler.getStringInput();
        boolean isContactValid = false;
        while (!isContactValid) {
            if (Validation.isEmpty(contactNumber)) {
                contactNumber = InputHandler.getStringInput();
            }
            if (!Validation.isDigitsOnly(contactNumber)) {
                displayErrorMessage("Enter valid contact details");
                contactNumber = InputHandler.getStringInput(true);
                continue;
            }
            if (contactNumber != null && !Validation.validateMobileNo(contactNumber)) {
                displayErrorMessage("Contact number should be of exactly 10 digits.");
                contactNumber = InputHandler.getStringInput(true);
            } else {
                isContactValid = true;
            }
        }
        restaurant.setRestaurantContact(contactNumber);

        System.out.println("Enter restaurant email : ");
        String email = InputHandler.getStringInput();
        while (!Validation.validateEmail(email)) {
            displayErrorMessage("Enter valid email. Please Enter email.");
            email = InputHandler.getStringInput();
        }
        restaurant.setRestaurantEmail(email);

        System.out.println("Enter restaurant opening time (hh:mm): ");
        String openingTimeInput = InputHandler.getStringInput(true);
        while (!Validation.validateTime(openingTimeInput)) {
            displayErrorMessage("Enter valid time format as hh:mm");
            openingTimeInput = InputHandler.getStringInput(true);
        }
        restaurant.setOpensAt(openingTimeInput);

        System.out.println("Enter restaurant closing time (hh:mm): ");
        String closingTimeInput = InputHandler.getStringInput(true);
        while (!Validation.validateTime(closingTimeInput)) {
            displayErrorMessage("Enter valid time format as hh:mm");
            closingTimeInput = InputHandler.getStringInput(true);
        }
        restaurant.setClosesAt(closingTimeInput);

        System.out.println("Enter restaurant GST number : ");
        restaurant.setGstNo(InputHandler.getStringInput());

        return restaurant;
    }

    public Restaurant getRestaurantAddressToRegister(Restaurant restaurant) {
        try {
            System.out.println("Enter restaurant address : ");
            restaurantAddress.setRestaurantAddLine1(InputHandler.getStringInput());

            System.out.println("Enter restaurant address line2 : ");
            restaurantAddress.setRestaurantAddLine2(InputHandler.getStringInput());

            List<State> stateList = restaurantService.getListOfState();
            printStateList(stateList);
            System.out.println("Enter state id : ");
            int stateId = InputHandler.getNumberInput();
            while (!locationDao.isStateExists(stateId)) {
                displayErrorMessage("Enter valid state id");
                stateId = InputHandler.getNumberInput();
            }
            restaurantAddress.setStateId(stateId);

            List<City> cityList = restaurantService.getListOfCity(stateId);
            printCityList(cityList);

            System.out.println("Enter city id : ");
            int city = InputHandler.getNumberInput();
            while (!locationDao.isCityExists(city, stateId)) {
                displayErrorMessage("Enter valid city id");
                city = InputHandler.getNumberInput();
            }
            restaurantAddress.setCityId(city);

            System.out.println("Enter pincode : ");
            String newPincode = InputHandler.getStringInput();
            boolean isPincodeValid = false;
            while (!isPincodeValid) {
                if (!Validation.isDigitsOnly(newPincode)) {
                    displayErrorMessage("Enter valid Pincode ");
                    newPincode = InputHandler.getStringInput();
                    continue;
                }
                if (newPincode != null && !Validation.validatePincode(newPincode)) {
                    displayErrorMessage("Pincode number should be of exactly 6 digits.");
                    newPincode = InputHandler.getStringInput();
                } else {
                    isPincodeValid = true;
                }
            }
            restaurantAddress.setPincode(Integer.parseInt(newPincode));
            restaurant.setRestaurantAddress(restaurantAddress);

        } catch (Exception e) {
            e.getMessage();
        }
        return restaurant;
    }

    public void printStateList(List<State> stateList) {
        System.out.println("id\t\tstate");
        for (State state : stateList) {
            System.out.println(state.getStateId() + "\t\t" + state.getStateName());
        }
    }

    public void printCityList(List<City> cityList) {
        System.out.println("id\t\tcity");
        for (City city : cityList) {
            System.out.println(city.getCityId() + "\t\t" + city.getCityName());
        }
    }

    public void getRestUpdateDetails(Restaurant restaurant) {

        System.out.println("Enter updated Restaurant Name else press enter");
        String newRestName = InputHandler.getStringInput(true);
        if (!newRestName.isEmpty()) {
            restaurant.setRestaurantName(newRestName);
        }
        System.out.println("Enter updated Restaurant Contact number else press enter");
        String newRestContact = InputHandler.getStringInput(true);
        if (!newRestContact.isEmpty()) {
            restaurant.setRestaurantContact(newRestContact);
        }

        System.out.println("Enter updated Restaurant Email else press enter");
        String newEmail = InputHandler.getStringInput(true);
        if (!newEmail.isEmpty()) {
            restaurant.setRestaurantEmail(newEmail);
        }

        System.out.println("Enter updated opening hours");
        String openingTimeInput = InputHandler.getStringInput(true);
        while (!Validation.validateTime(openingTimeInput)) {
            displayErrorMessage("Enter valid time format as hh:mm");
            openingTimeInput = InputHandler.getStringInput(true);
        }
        if (!openingTimeInput.isEmpty()) {
            restaurant.setOpensAt(openingTimeInput);
        }

        System.out.println("Enter updated closing hours");
        String closingTimeInput = InputHandler.getStringInput(true);
        while (!Validation.validateTime(closingTimeInput)) {
            displayErrorMessage("Enter valid time format as hh:mm");
            closingTimeInput = InputHandler.getStringInput(true);
        }
        if (!closingTimeInput.isEmpty()) {
            restaurant.setOpensAt(closingTimeInput);
        }
    }

    public Restaurant getRestAddUpdateDetails(Restaurant restaurant) {

        System.out.println("Enter updated Restaurant address  else press enter");
        String newRestAddress = InputHandler.getStringInput(true);
        if (!newRestAddress.isEmpty()) {
            restaurant.getRestaurantAddress().setRestaurantAddLine1(newRestAddress);
        }

        System.out.println("Enter updated Restaurant landmark/street name  else press enter");
        String newRestLandmark = InputHandler.getStringInput(true);
        if (!newRestLandmark.isEmpty()) {
            restaurant.getRestaurantAddress().setRestaurantAddLine2(newRestLandmark);
        }

        System.out.println("Enter updated Restaurant pincode else press enter");
        String newPincode = InputHandler.getStringInput(true);
        boolean isPinCodeValid = false;
        while (!isPinCodeValid) {
            if (Validation.isEmpty(newPincode)) {
                break;
            }
            if (!Validation.isDigitsOnly(newPincode)) {
                displayErrorMessage("Enter valid Pincode ");
                newPincode = InputHandler.getStringInput(true);
                continue;
            }
            if (newPincode != null && !Validation.validatePincode(newPincode)) {
                displayErrorMessage("Pincode number should be of exactly 6 digits.");
                newPincode = InputHandler.getStringInput(true);
            } else {
                isPinCodeValid = true;
            }
        }
        if (!newPincode.isEmpty()) {
            restaurant.getRestaurantAddress().setPincode(Integer.parseInt(newPincode));
        }
        return restaurant;
    }

    public void printRestaurants(Map<Integer, String> map) {
        System.out.println("Restaurant Id \tRestaurant Name");
        for (Integer key : map.keySet()) {
            System.out.println(key + "\t\t\t\t" + map.get(key));
        }
    }
}

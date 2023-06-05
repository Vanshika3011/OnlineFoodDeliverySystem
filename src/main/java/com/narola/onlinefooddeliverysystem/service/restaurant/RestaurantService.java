/*
package com.narola.onlinefooddeliverysystem.service.restaurant;

import com.narola.onlinefooddeliverysystemV1.dao.LocationDao;
import com.narola.onlinefooddeliverysystem.dao.RestaurantDao;
import com.narola.onlinefooddeliverysystemV1.exception.DAOLayerException;
import com.narola.onlinefooddeliverysystemV1.input.InputHandler;
import com.narola.onlinefooddeliverysystemV1.validation.Validation;
import com.narola.onlinefooddeliverysystemV1.model.City;
import com.narola.onlinefooddeliverysystemV1.model.Restaurant;
import com.narola.onlinefooddeliverysystemV1.model.RestaurantAddress;
import com.narola.onlinefooddeliverysystemV1.model.State;

import java.util.List;


public class RestaurantService {

    public static void addRestaurant() {


        Restaurant rest = new Restaurant();

        System.out.println("Enter restaurant name : ");
        rest.setRestaurantName(InputHandler.getStringInput());

        System.out.println("Enter restaurant contact number : ");
        String contactNumber = InputHandler.getStringInput();
        boolean isContactValid = false;
        while (!isContactValid) {
            if (Validation.isEmpty(contactNumber)) {
                contactNumber = InputHandler.getStringInput();
            }
            if (!Validation.isDigitsOnly(contactNumber)) {
                System.out.println("Enter valid contact details");
                contactNumber = InputHandler.getStringInput(true);
                continue;
            }
            if (contactNumber != null && !Validation.validateMobileNo(contactNumber)) {
                System.out.println("Contact number should be of exactly 10 digits.");
                contactNumber = InputHandler.getStringInput(true);
            } else {
                isContactValid = true;
            }
        }
        rest.setRestaurantContact(contactNumber);

        System.out.println("Enter restaurant email : ");
        String email = InputHandler.getStringInput();
        while (!Validation.validateEmail(email)) {
            System.out.println("Enter valid email. Please Enter email.");
            email = InputHandler.getStringInput();
        }
        rest.setRestaurantEmail(email);

        System.out.println("Enter restaurant opening time (hh:mm): ");
        String openingTimeInput = InputHandler.getStringInput(true);
        while (!Validation.validateTime(openingTimeInput)) {
            System.out.println("Enter valid time format as hh:mm");
            openingTimeInput = InputHandler.getStringInput(true);
        }
        rest.setOpensAt(openingTimeInput);

        System.out.println("Enter restaurant closing time (hh:mm): ");
        String closingTimeInput = InputHandler.getStringInput(true);
        while (!Validation.validateTime(closingTimeInput)) {
            System.out.println("Enter valid time format as hh:mm");
            closingTimeInput = InputHandler.getStringInput(true);
        }
        rest.setClosesAt(closingTimeInput);

        System.out.println("Enter restaurant GST number : ");
        rest.setGstNo(InputHandler.getStringInput());

        int generatedId = addRestaurantAddress();

        try {
            RestaurantDao.registerRestaurant(rest, generatedId);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    public static int addRestaurantAddress() {
        try {
            RestaurantAddress restAdd = new RestaurantAddress();

            System.out.println("Enter restaurant address : ");
            restAdd.setRestaurantAddLine1(InputHandler.getStringInput());

            System.out.println("Enter restaurant address line2 : ");
            restAdd.setRestaurantAddLine2(InputHandler.getStringInput());

            List<State> stateList = LocationDao.getStates();
            printStateList(stateList);
            System.out.println("Enter state id : ");
            int stateId = InputHandler.getNumberInput();
            while (!LocationDao.isStateExists(stateId)) {
                System.out.println("Enter valid state id");
                stateId = InputHandler.getNumberInput();
            }
            restAdd.setStateId(stateId);

            List<City> cityList = LocationDao.getCities(stateId);
            printCityList(cityList);

            System.out.println("Enter city id : ");
            int city = InputHandler.getNumberInput();
            while (!LocationDao.isCityExists(city, stateId)) {
                System.out.println("Enter valid city id");
                city = InputHandler.getNumberInput();
            }
            restAdd.setCityId(city);

            System.out.println("Enter pincode : ");
            String newPincode = InputHandler.getStringInput(true);
            boolean isPincodevalid = false;
            while (!isPincodevalid) {
                if (!Validation.isDigitsOnly(newPincode)) {
                    System.out.println("Enter valid Pincode ");
                    newPincode = InputHandler.getStringInput(true);
                    continue;
                }
                if (newPincode != null && !Validation.validatePincode(newPincode)) {
                    System.out.println("Pincode number should be of exactly 6 digits.");
                    newPincode = InputHandler.getStringInput(true);
                } else {
                    isPincodevalid = true;
                }
            }
            restAdd.setPincode(Integer.parseInt(newPincode));

//            return RestaurantDao.insertRestDetails(restAdd);N111MA



        } catch (Exception e) {
            e.getMessage();
        }
        return 0;
    }

    public static void printStateList(List<State> stateList) {
        System.out.println("id\t\tstate");
        for(State state : stateList){
            System.out.println(state.getStateId() + "\t\t" + state.getStateName());
        }
    }
    public static void printCityList(List<City> cityList) {
        System.out.println("id\t\tcity");
        for (City city : cityList) {
            System.out.println(city.getCityId() + "\t\t" + city.getCityName());
        }
    }

    public static int editRestaurantDetails(int ownerId, int roleId) {
        try {
            System.out.println("Select restaurant id to update");
            int restId = InputHandler.getNumberInput();

            System.out.println("Select the field to be updated");
            System.out.println("1. To update restaurant name, contact details, hours of operation");
            System.out.println("2. To update restaurant address details");

            int choiceToUpdate = InputHandler.getNumberInput();

            switch (choiceToUpdate) {
                case 1:
                    int isRestUpdated = RestaurantDao.editRestaurantContact(restId, ownerId);
                    if (isRestUpdated > 0) {
                        System.out.println("Restaurant Details Updated Successfully!");
                    } else
                        System.out.println("Please enter valid details");
                    break;

                case 2:
                    RestaurantDao.editRestaurantAddress(restId, ownerId);
                    break;

                default:
                    System.out.println("Invalid choice!");
                    editRestaurantDetails(ownerId, roleId);
            }
            return restId;
        } catch (DAOLayerException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


}

*/

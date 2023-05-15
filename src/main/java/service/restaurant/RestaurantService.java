package service.restaurant;

import dao.LocationDao;
import dao.RestaurantDao;
import input.InputHandler;
import model.Restaurant;
import model.RestaurantAddress;
import exception.DAOLayerException;
import validation.Validation;


public class RestaurantService {

    public static void addRestaurant() {

        model.Restaurant rest = new model.Restaurant();

        System.out.println("Enter restaurant name : ");
        rest.setRestaurantName(InputHandler.getStringInput());

        System.out.println("Enter restaurant contact number : ");
        String contactNumber = InputHandler.getStringInput();
        boolean isContactValid = false;
        while (!isContactValid) {
            if (Validation.isEmpty(contactNumber)) {
                contactNumber = InputHandler.getStringInput();
                ;
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

            LocationDao.getStates();
            System.out.println("Enter state id : ");
            int state = InputHandler.getNumberInput();
            while (!LocationDao.doStateExists(state)) {
                System.out.println("Enter valid state id");
                state = InputHandler.getNumberInput();
            }
            restAdd.setStateId(state);

            LocationDao.getCities(state);
            System.out.println("Enter city id : ");
            int city = InputHandler.getNumberInput();
            while (!LocationDao.doCityExists(city, state)) {
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

            return RestaurantDao.insertRestDetails(restAdd);

        } catch (Exception e) {
            e.getMessage();
        }
        return 0;
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

    public static void getRestUpdateDetails(Restaurant restDetails) {

        System.out.println("Enter updated Restaurant Name else press enter");
        String newRestName = InputHandler.getStringInput(true);
        if (!newRestName.isEmpty()) {
            restDetails.setRestaurantName(newRestName);
        }
        System.out.println("Enter updated Restaurant Contact number else press enter");
        String newRestContact = InputHandler.getStringInput(true);
        if (!newRestContact.isEmpty()) {
            restDetails.setRestaurantContact(newRestContact);
        }

        System.out.println("Enter updated Restaurant Email else press enter");
        String newEmail = InputHandler.getStringInput(true);
        if (!newEmail.isEmpty()) {
            restDetails.setRestaurantEmail(newEmail);
        }

        System.out.println("Enter updated opening hours");
        String openingTimeInput = InputHandler.getStringInput(true);
        while (!Validation.validateTime(openingTimeInput)) {
            System.out.println("Enter valid time format as hh:mm");
            openingTimeInput = InputHandler.getStringInput(true);
        }
        if (!openingTimeInput.isEmpty()) {
            restDetails.setOpensAt(openingTimeInput);
        }

        System.out.println("Enter updated closing hours");
        String closingTimeInput = InputHandler.getStringInput(true);
        while (!Validation.validateTime(closingTimeInput)) {
            System.out.println("Enter valid time format as hh:mm");
            closingTimeInput = InputHandler.getStringInput(true);
        }
        if (!closingTimeInput.isEmpty()) {
            restDetails.setOpensAt(closingTimeInput);
        }
    }

    public static void getRestAddUpdateDetails(RestaurantAddress restAdd) {


        System.out.println("Enter updated Restaurant address  else press enter");
        String newRestAddress = InputHandler.getStringInput(true);
        if (!newRestAddress.isEmpty()) {
            restAdd.setRestaurantAddLine1(newRestAddress);
        }

        System.out.println("Enter updated Restaurant landmark/street name  else press enter");
        String newRestLandmark = InputHandler.getStringInput(true);
        if (!newRestLandmark.isEmpty()) {
            restAdd.setRestaurantAddLine2(newRestLandmark);
        }

        System.out.println("Enter updated Restaurant pincode else press enter");
        String newPincode = InputHandler.getStringInput(true);
        boolean isPincodevalid = false;
        while (!isPincodevalid) {
            if (Validation.isEmpty(newPincode)) {
                break;
            }
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
        if (!newPincode.isEmpty()) {
            restAdd.setPincode(Integer.parseInt(newPincode));
        }
    }
}


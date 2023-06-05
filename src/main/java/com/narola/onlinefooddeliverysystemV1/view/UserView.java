package com.narola.onlinefooddeliverysystemV1.view;

import com.narola.onlinefooddeliverysystemV1.dao.UserDao;
import com.narola.onlinefooddeliverysystemV1.enums.UserRoles;
import com.narola.onlinefooddeliverysystemV1.exception.DAOLayerException;
import com.narola.onlinefooddeliverysystemV1.input.InputHandler;
import com.narola.onlinefooddeliverysystemV1.model.User;
import com.narola.onlinefooddeliverysystemV1.service.UserService;
import com.narola.onlinefooddeliverysystemV1.validation.Validation;

import java.util.List;

public class UserView {

    private UserDao userDao = new UserDao();
    private static UserService userService = new UserService();

    public void displayUserRoleForSignUp() {
        UserRoles[] roles = UserRoles.values();
        for (UserRoles role : roles) {
            if (role.getRoleIdValue() > 1) {
                System.out.println("Enter " + role.getRoleIdValue() + " to signup as " + role.getRoleName() + ".");
            }
        }
    }

    public User getSignUpInformationFromUser() {

        User user = new User();

        System.out.println("Please select your role ");
        this.displayUserRoleForSignUp();
        int roleId = InputHandler.getNumberInput();
        while (roleId == UserRoles.ADMIN.getRoleIdValue()) {
            System.out.println("Invalid Role Id selected. Enter valid role id!");
            roleId = InputHandler.getNumberInput();
        }
        if (roleId == UserRoles.CUSTOMER.getRoleIdValue()) {
            System.out.println("You are signing in as a Customer.\n");
        } else if (roleId == UserRoles.RESTAURANTADMIN.getRoleIdValue()) {
            System.out.println("You are signing in as a Restaurant admin.\n");
        } else if (roleId == UserRoles.DELIVERYAGENT.getRoleIdValue()) {
            System.out.println("You are signing in as a Delivery Agent.\n");
        } else {
            System.out.println("Enter valid input.");
        }

        user.setRoleId(roleId);

        System.out.println("Enter your Username");
        String username = InputHandler.getStringInput();
        boolean isUsernameValid = false;
        while (!isUsernameValid) {
            if (!Validation.validateUserNameLength(username)) {
                System.out.println("Length of Username must be minimum 8 characters and maximum 20 characters.");
                System.out.println("Enter your Username");
                username = InputHandler.getStringInput();
                continue;
            }
            try {
                if (!userDao.isUsernameExist(username)) break;
            } catch (DAOLayerException e) {
                e.printStackTrace();
            }
            System.out.println("Username already exist. PLease enter username.");
            username = InputHandler.getStringInput();
        }
        user.setUsername(username);

        System.out.println("Enter password : ");
        String password = InputHandler.getStringInput();
        Validation.validatePassword(password);
        user.setPassword(password);

        System.out.println("Enter your First Name : ");
        user.setFirstName(InputHandler.getStringInput());

        System.out.println("Enter your Last Name : ");
        user.setLastName(InputHandler.getStringInput());

        System.out.println("Enter your Contact Number : ");
        String contactNumber = InputHandler.getStringInput(true);
        boolean isContactValid = false;
        while (!isContactValid) {
            if (Validation.isEmpty(contactNumber)) {
                break;
            }
            if (!Validation.isDigitsOnly(null)) {
                System.out.println("Enter valid contact details.");
                contactNumber = InputHandler.getStringInput(true);
                continue;
            }
            if (!Validation.validateMobileNo(contactNumber)) {
                System.out.println("Contact number should be of exactly 10 digits.");
                contactNumber = InputHandler.getStringInput(true);
            } else {
                isContactValid = true;
            }
        }
        user.setContact(contactNumber);

        System.out.println("Enter your Email : ");
        String email = InputHandler.getStringInput();
        boolean isEmailValid = false;
        while (!isEmailValid) {
            if (!Validation.validateEmail(email)) {
                System.out.println("Enter valid email. Please Enter email.");
                email = InputHandler.getStringInput();
                continue;
            }
            try {
                if (userDao.isUserEmailExist(email)) {
                    System.out.println("Email already exist. Please Enter email.");
                    email = InputHandler.getStringInput();
                } else {
                    isEmailValid = true;
                }
            } catch (DAOLayerException e) {
                e.printStackTrace();
            }
        }
        user.setEmail(email);
        return user;
    }

    public User getLoginInformationFromUser() {
        User user = new User();
        System.out.println("Please enter your username : ");
        user.setUsername(InputHandler.getStringInput());
        System.out.println("Please enter your password : ");
        user.setPassword(InputHandler.getStringInput());
        return user;
    }

    public int getVerificationCodeFromUser() {
        System.out.println("Please enter your verification code : ");
        return InputHandler.getNumberInput();
    }

    public void displayErrorMessage(String message) {
        System.out.println("ERROR:" + message);
    }

    public void displayInfoMessage(String message) {
        System.out.println("INFO:" + message);
    }

    public void displayLoginWelcomeMessage(User user) {
        System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName() + ".");
    }

    public void printUsersDetails(List<User> userList) {
        if (userList == null || userList.isEmpty()) {
            System.out.println("No user present.");
            return;
        }
        System.out.println("Id\tUsername\tContact No\tEmail\t\t\t\tName\t\tSurname");
        for (User user : userList) {
            System.out.println(user.getUserId() + "\t" + user.getUsername() + " \t" + user.getContact() + "\t" + user.getEmail() + "\t" + user.getFirstName() + "\t" + user.getLastName());
        }
    }

    public int displayRestaurantAdminsAndGetOwnerIdFromAdmin() {
        List<User> userList = userService.getUserDetailsListByRoleID(UserRoles.RESTAURANTADMIN.getRoleIdValue());
        printUsersDetails(userList);
        System.out.println("Enter owner ID : ");
        return InputHandler.getNumberInput();
    }

    public String getItemNameFromCustomer() {
        System.out.println("Enter food item name : ");
        return InputHandler.getStringInput();
    }

}

/*
package com.narola.onlinefooddeliverysystem.service.user;

import com.narola.onlinefooddeliverysystemV1.dao.UserDao;
import com.narola.onlinefooddeliverysystemV1.email.MailSender;
import com.narola.onlinefooddeliverysystemV1.enums.UserRoles;
import com.narola.onlinefooddeliverysystemV1.exception.DAOLayerException;
import com.narola.onlinefooddeliverysystemV1.exception.MailException;
import com.narola.onlinefooddeliverysystemV1.input.InputHandler;
import com.narola.onlinefooddeliverysystemV1.model.User;
import com.narola.onlinefooddeliverysystemV1.session.CurrentUserCredentials;
import com.narola.onlinefooddeliverysystem.OnlineFoodDelivery;
import com.narola.onlinefooddeliverysystemV1.utility.Utility;
import com.narola.onlinefooddeliverysystemV1.validation.Validation;



public class UserManager {


    public static void signUpUser() {
        User user = new User();

        System.out.println("Please select your role ");
        UserRoles.getUserRole();
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
            System.out.println("You are signing in as a Customer.\n");
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
                if (!com.narola.onlinefooddeliverysystemV1.dao.UserDao.isUsernameExist(username)) break;
            } catch (DAOLayerException e) {
                e.printStackTrace();
            }
            System.out.println("Username already exist.");
            username = InputHandler.getStringInput();
        }
        user.setUsername(username);

        System.out.println("Enter password");
        String password = InputHandler.getStringInput();
        Validation.validatePassword(password);
        user.setPassword(password);

        System.out.println("Enter your First Name");
        user.setFirstName(InputHandler.getStringInput());

        System.out.println("Enter your Last Name");
        user.setLastName(InputHandler.getStringInput());

        System.out.println("Enter your Contact Number");
        String contactNumber = InputHandler.getStringInput(true);
        boolean isContactValid = false;
        while (!isContactValid) {
            if (Validation.isEmpty(contactNumber)) {
                break;
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
        user.setContact(contactNumber);

        System.out.println("Enter your Email");
        String email = InputHandler.getStringInput();
        boolean isEmailValid = false;
        while (!isEmailValid) {
            if (!Validation.validateEmail(email)) {
                System.out.println("Enter valid email. Please Enter email.");
                email = InputHandler.getStringInput();
                continue;
            }
            try {
                if (com.narola.onlinefooddeliverysystemV1.dao.UserDao.isUserEmailExist(email)) {
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

        user.setVerificationCode(Utility.generateVerificationCode());

        String subject = "Verification code for your Foodies account";
        String body = "Hello " + user.getFirstName() + ",\nYour verification code is " + user.getVerificationCode() + ".";
        try {
            MailSender mailSender = new MailSender();
            mailSender.sendEmail(user.getEmail(), subject, body);
            UserDao.createUser(user); //dao
            OnlineFoodDelivery.main(null);
        } catch (MailException e) {
            e.printStackTrace();
            signUpUser();
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    public static void loginUser() {
        User user = new User();

        System.out.println("Please enter your username");
        user.setUsername(InputHandler.getStringInput());

        System.out.println("Please enter your password");
        user.setPassword(InputHandler.getStringInput());

        UserVerification.verifyUser(user);

        if (CurrentUserCredentials.currentUser.getRoleId() == UserRoles.ADMIN.getRoleIdValue()) {
//            Admin.adminFunc();

        } else if (CurrentUserCredentials.currentUser.getRoleId() == UserRoles.RESTAURANTADMIN.getRoleIdValue()) {
//            RestaurantAdminManager.restAdminFunc();
        }
    }

}
*/

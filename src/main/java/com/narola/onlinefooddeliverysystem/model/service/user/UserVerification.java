package com.narola.onlinefooddeliverysystem.model.service.user;

import com.narola.onlinefooddeliverysystem.dao.UserDao;
import com.narola.onlinefooddeliverysystem.exception.DAOLayerException;
import com.narola.onlinefooddeliverysystem.model.User;
import com.narola.onlinefooddeliverysystem.session.CurrentUserCredentials;
import com.narola.onlinefooddeliverysystem.input.InputHandler;

public class UserVerification {

    public static void verifyUser(User user) {
        try {
            user = UserDao.validateUser(user);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        if (user != null) {
            if (!user.isVerified()) {
                verifyUserCode(user);
            } else {
                CurrentUserCredentials.setCurrentUser(user);
                System.out.println("Logged in Successfully");
                System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName() + ".\n");
            }
        } else {
            System.out.println("Please login Again!");
            UserManager.loginUser();
        }
    }

    public static void verifyUserCode(User user) {
        if (user.isVerified() == false) {
            System.out.println("Please enter your verification code.");
            int verificationCode = InputHandler.getNumberInput();
            if (verificationCode == user.getVerificationCode()) {
                try {
                    UserDao.updateIsVerified(user.getUsername());
                } catch (DAOLayerException e) {
                    System.out.println("Error occurred while verifying user.");
                }
                System.out.println("Verification successful!");
                CurrentUserCredentials.setCurrentUser(user);
                    System.out.println("Logged in Successfully.");
                System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName() + ".");
            } else {
                System.out.println("Invalid verification code.");
                System.out.println("Please login again.");
                UserManager.loginUser();
            }
        }
    }
}

package service.user;

import dao.UserDao;
import exception.DAOLayerException;
import input.InputHandler;
import model.User;
import session.CurrentUserCredentials;

public class UserVerification {

    public static void verifyUser(User user) {
        try {
            user = UserDao.validateUser(user);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        if (user != null) {
            if (user.isVerified() == false) {
                verifyUserCode(user);
            } else {
                CurrentUserCredentials.setCurrentUser(user);
                System.out.println("Logged in Successfully");
                System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName() + "./n");
            }
        } else {
            System.out.println("Please login Again!");
            UserService.loginUser();
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
                UserService.loginUser();
            }
        }
    }
}

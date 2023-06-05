package com.narola.onlinefooddeliverysystemV1.service;

import com.narola.onlinefooddeliverysystemV1.OnlineFoodDelivery;
import com.narola.onlinefooddeliverysystemV1.dao.UserDao;
import com.narola.onlinefooddeliverysystemV1.email.MailSender;
import com.narola.onlinefooddeliverysystemV1.exception.DAOLayerException;
import com.narola.onlinefooddeliverysystemV1.exception.MailException;
import com.narola.onlinefooddeliverysystemV1.manager.RoleManager;
import com.narola.onlinefooddeliverysystemV1.manager.UserManager;
import com.narola.onlinefooddeliverysystemV1.model.User;
import com.narola.onlinefooddeliverysystemV1.session.CurrentUserCredentials;
import com.narola.onlinefooddeliverysystemV1.utility.Utility;
import com.narola.onlinefooddeliverysystemV1.view.UserView;

import java.util.List;

import static com.narola.onlinefooddeliverysystemV1.constant.Constants.*;

public class UserService {

    private static UserView userView = new UserView();
    private MailSender mailSender = new MailSender();
    private UserDao userDao = new UserDao();
    private static RoleManager roleManager = new RoleManager();
    private UserManager userManager = new UserManager();

    private static int count = 1;

    public void doSignUp(User user) {
        user.setVerificationCode(Utility.generateVerificationCode());
        try {
            sendMail(user);
            int isAdded = userDao.createUser(user);
            if (isAdded > 0) {
                userView.displayInfoMessage("You have signed up successfully.");
            } else {
                userView.displayErrorMessage("Failed to Sign-up.Please try again.");
                OnlineFoodDelivery.main(null);
            }
        } catch (MailException e) {
            e.printStackTrace();
            OnlineFoodDelivery.main(null);
        } catch (DAOLayerException e) {
            e.printStackTrace();
            OnlineFoodDelivery.main(null);
        }
    }

    private void sendMail(User user) throws MailException {
        String subject = "Verification code for your Foodies account";
        String body = "Hello " + user.getFirstName() + ",\nYour verification code is " + user.getVerificationCode() + ".";
        mailSender.sendEmail(user.getEmail(), subject, body);
    }

    public void doLogin(User user) {
        try {
            user = userDao.validateUser(user);
        } catch (DAOLayerException ex) {
            ex.printStackTrace();
            userView.displayErrorMessage("Ops, Something went wrong. please try after sometime");
            return;
        }
        if (user == null) {
            if (count == MAX_TRIALS_FOR_LOGIN) {
                userView.displayErrorMessage("Try again Later");
                count = 1;
            } else {
                userView.displayErrorMessage("Wrong credentials.");
                count++;
                userManager.doLogin();
            }
        } else {
            try {
                if (checkAccountVerification(user)) {
                    userView.displayInfoMessage("Logged in Successfully.");
                    userView.displayLoginWelcomeMessage(user);
                } else {
                    checkForVerificationAttempt(user);
                }
            } catch (DAOLayerException ex) {
                ex.printStackTrace();
                userView.displayErrorMessage("Ops, Something went wrong. Please try again after sometime");
            }
            CurrentUserCredentials.setCurrentUser(user);
            roleManager.assignTaskAsPerRole();
        }
    }

    private boolean checkAccountVerification(User user) throws DAOLayerException {
        if (!user.isVerified()) {
            int verificationCode = userView.getVerificationCodeFromUser();
            if (verificationCode == user.getVerificationCode()) {
                userDao.updateIsVerified(user.getUsername());
            } else {
                return false;
            }
        }
        return true;
    }

    public void checkForVerificationAttempt(User user) throws DAOLayerException {
        int maxTrialsForVerificationCode = 1;
        while (maxTrialsForVerificationCode < MAX_TRIALS_FOR_VERIFICATION) {
            if (checkAccountVerification(user)) {
                userView.displayInfoMessage("Logged in Successfully.");
                userView.displayLoginWelcomeMessage(user);
                break;
            } else {
                maxTrialsForVerificationCode++;
                if (maxTrialsForVerificationCode == MAX_TRIALS_FOR_VERIFICATION) {
                    OnlineFoodDelivery.main(null);
                }
            }
        }
    }

    public List<User> getUserDetailsListByRoleID(int userRoleID) {
        List<User> userList = null;
        try {
            userList = userDao.getUsersDetails(userRoleID);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        return userList;
    }
}

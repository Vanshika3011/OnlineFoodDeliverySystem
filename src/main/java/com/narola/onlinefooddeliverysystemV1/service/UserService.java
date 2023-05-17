package com.narola.onlinefooddeliverysystemV1.service;

import com.narola.onlinefooddeliverysystem.email.MailSender;
import com.narola.onlinefooddeliverysystem.exception.DAOLayerException;
import com.narola.onlinefooddeliverysystem.exception.MailException;
import com.narola.onlinefooddeliverysystem.model.User;
import com.narola.onlinefooddeliverysystem.utility.Utility;
import com.narola.onlinefooddeliverysystemV1.OnlineFoodDelivery;
import com.narola.onlinefooddeliverysystemV1.dao.UserDao;
import com.narola.onlinefooddeliverysystemV1.view.UserView;

public class UserService {

    private UserView userView = new UserView();
    private MailSender mailSender = new MailSender();
    private UserDao userDao = new UserDao();

    public void doSignUp(User user) {
        user.setVerificationCode(Utility.generateVerificationCode());
        try {
            sendMail(user);
            userDao.createUser(user);
            OnlineFoodDelivery.main(null);
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
            //Redirect to MainMenu
        }
        if (user == null) {
            userView.displayErrorMessage("Wrong credentials");
            doLogin(userView.getLoginInformationFromUser());
        }
        try {
            if (checkAccountVerification(user)) {
                userView.displayInfoMessage("Verification successful!");
                userView.displayInfoMessage("Logged in Successfully.");
                userView.displayLoginWelcomeMessage(user);
            } else {
                //TODO : Retry max 4 times and Redirect to MainMenu

            }
        } catch (DAOLayerException ex) {
            ex.printStackTrace();
            userView.displayErrorMessage("Ops, Something went wrong. please try after sometime");
            //Redirect to MainMenu
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
}



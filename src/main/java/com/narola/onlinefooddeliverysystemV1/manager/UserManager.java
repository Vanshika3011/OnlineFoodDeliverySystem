package com.narola.onlinefooddeliverysystemV1.manager;

import com.narola.onlinefooddeliverysystem.model.User;
import com.narola.onlinefooddeliverysystemV1.service.UserService;
import com.narola.onlinefooddeliverysystemV1.view.UserView;

public class UserManager {

    private UserView userView = new UserView();
    private UserService userService = new UserService();

    public void doSignup() {
        User user = userView.getSignUpInformationFromUser();
        userService.doSignUp(user);
    }

    public void doLogin() {
        User user = userView.getLoginInformationFromUser();
        userService.doLogin(user);
    }


}

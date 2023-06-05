package com.narola.onlinefooddeliverysystemV1.manager;

import com.narola.onlinefooddeliverysystemV1.menu.AppMenu;
import com.narola.onlinefooddeliverysystemV1.model.User;
import com.narola.onlinefooddeliverysystemV1.service.UserService;
import com.narola.onlinefooddeliverysystemV1.view.UserView;

import java.util.List;

public class UserManager {

    private UserView userView = new UserView();
    private static UserService userService = new UserService();

    public void doSignup() {
        User user = userView.getSignUpInformationFromUser();
        userService.doSignUp(user);
    }

    public void doLogin() {
        User user = userView.getLoginInformationFromUser();
        userService.doLogin(user);
    }

    public void getUserListByRole() {
        int userRoleID = AppMenu.displayUserDetailsMenuAndTakeInput();
        List<User> userList = userService.getUserDetailsListByRoleID(userRoleID);
        userView.printUsersDetails(userList);
    }

}

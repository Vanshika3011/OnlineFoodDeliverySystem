package com.narola.onlinefooddeliverysystem.session;

import com.narola.onlinefooddeliverysystem.model.User;

public class CurrentUserCredentials {

    public static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        CurrentUserCredentials.currentUser = currentUser;
    }
}

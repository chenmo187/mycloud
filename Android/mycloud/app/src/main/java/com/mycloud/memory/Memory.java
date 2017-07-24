package com.mycloud.memory;

import com.mycloud.pojo.User;

/**
 * Created by clarkchen on 08/12/2016.
 */

public class Memory {

    private static User user;
    private static String token;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Memory.user = user;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Memory.token = token;
    }




}

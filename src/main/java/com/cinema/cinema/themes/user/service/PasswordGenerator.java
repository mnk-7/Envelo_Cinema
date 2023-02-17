package com.cinema.cinema.themes.user.service;

import com.cinema.cinema.themes.user.model.StandardUser;

import java.util.Random;

public class PasswordGenerator {
    private static int counter = 0;

    public static String generatePassword(StandardUser user) {
        return "p-" + user.hashCode() / (new Random().nextInt(5) + 1) + ++counter;
    }

}

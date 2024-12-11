package com.example.java7;

import jakarta.ejb.Stateless;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Stateless
public class UserCounterBean {

    private int userCount;

    @PostConstruct
    public void init() {
        userCount = 0;
    }

    public void userLoggedIn() {
        userCount++;
    }

    public void userLoggedOut() {
        userCount--;
    }

    public int getUserCount() {
        return userCount;
    }

    @PreDestroy
    public void cleanup() {
        userCount=0;
    }
}

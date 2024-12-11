package com.example.java7;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class UserService {

    @Inject
    private UserBean userBean;

    public User createUser(String name, String email) {
        return userBean.createUser(name, email);
    }

    public User getUserById(Long id) {
        return userBean.getUserById(id);
    }

    public User updateUser(Long id, String newName) {
        return userBean.updateUser(id, newName);
    }

    public void deleteUser(Long id) {
        userBean.deleteUser(id);
    }

    public List<User> getUsersByEmail(String email) {
        return userBean.getUsersByEmail(email);
    }

    public List<User> getActiveUsers() {
        return userBean.getActiveUsers();
    }
}

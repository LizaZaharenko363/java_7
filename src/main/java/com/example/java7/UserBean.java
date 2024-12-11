package com.example.java7;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@Stateless
public class UserBean {

    @Inject
    private UserRepository userRepository;

    @Transactional
    public User createUser(String name, String email) {
        User user = new User(name, email);
        return userRepository.create(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User updateUser(Long id, String newName) {
        User user = userRepository.findById(id);
        if (user != null) {
            user.setName(newName);
            return userRepository.update(user);
        }
        return null;
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    public List<User> getUsersByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getActiveUsers() {
        return userRepository.findActiveUsers();
    }
}

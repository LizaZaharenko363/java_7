package com.example.java7;

import com.example.java7.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class UserRepository {
    @PersistenceContext(unitName = "examplePU")
    private EntityManager entityManager;

    @Transactional
    public User create(User user) {
        entityManager.persist(user);
        return user;
    }

    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Transactional
    public User update(User user) {
        return entityManager.merge(user);
    }

    @Transactional
    public void delete(Long id) {
        User user = findById(id);
        if (user != null) {
            entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
        }
    }

    public List<User> findByEmail(String email) {
        return entityManager.createNamedQuery("User.findByEmail", User.class)
                .setParameter("email", email)
                .getResultList();
    }

    public List<User> findActiveUsers() {
        return entityManager.createNamedQuery("User.findActiveUsers", User.class)
                .getResultList();
    }
}
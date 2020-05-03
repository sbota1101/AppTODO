package com.sb.repository;

import com.sb.model.User;
import javax.persistence.EntityManager;
import java.util.List;



public class UserRepository implements CrudRepository<User, Integer> {
    private EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<User> findAll() {
      //done:Add implementation
        // JPQL query - Java Persistence Query Language
        return entityManager.createQuery("SELECT u FROM User u").getResultList();
    }

    public void save(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    public void deleteById(Integer id) {
        User user = findById(id);
        if (user != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(user);
            entityManager.getTransaction().commit();
        }
    }

    public User findById(Integer id) {
        // done: Add try catch
        try {
            User user = entityManager.find(User.class, id);
            return user;
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        return null;
    }

    public User findByUsername(String username) {
//        int id = entityManager
//                .createQuery("SELECT u  FROM User u WHERE u.username =: username")
//                .setParameter("username", username)
//                .getFirstResult();
//        return findById(id);
        try {
            User user = (User)
                    entityManager
                            .createQuery("SELECT u FROM User u WHERE u.username =: username")
                            .setParameter("username", username)
                            .getResultList().get(0);
            return user;        }
        catch (Exception ex) {        }
        return null;

        }
    }


package org.example.plantillaexamen.dao;

import jakarta.persistence.EntityManager;
import org.example.plantillaexamen.model.User;
import org.example.plantillaexamen.util.ConnectionManager;

import java.util.List;
import java.util.Optional;

public class UserDaoOrm implements UserDao {

    @Override
    public List<User> findAll() {
        EntityManager em = ConnectionManager.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u", User.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<User> findById(long id) {
        EntityManager em = ConnectionManager.getEntityManager();
        try {
            User u = em.find(User.class, id);
            return Optional.ofNullable(u);
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        EntityManager em = ConnectionManager.getEntityManager();
        try {
            List<User> list = em.createQuery(
                            "SELECT u FROM User u WHERE u.username = :username",
                            User.class)
                    .setParameter("username", username)
                    .getResultList();
            return list.stream().findFirst();
        } finally {
            em.close();
        }
    }

    @Override
    public void insert(User user) {
        EntityManager em = ConnectionManager.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public void update(User user) {
        EntityManager em = ConnectionManager.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public void delete(long id) {
        EntityManager em = ConnectionManager.getEntityManager();
        try {
            em.getTransaction().begin();
            User u = em.find(User.class, id);
            if (u != null) {
                em.remove(u);
            }
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }
}
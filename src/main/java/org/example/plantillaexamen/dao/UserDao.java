package org.example.plantillaexamen.dao;

import org.example.plantillaexamen.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> findAll();

    Optional<User> findById(long id);

    Optional<User> findByUsername(String username);

    void insert(User user);

    void update(User user);

    void delete(long id);
}

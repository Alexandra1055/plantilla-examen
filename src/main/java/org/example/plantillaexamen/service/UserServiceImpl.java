package org.example.plantillaexamen.service;

import org.example.plantillaexamen.dao.UserDao;
import org.example.plantillaexamen.dto.UserDto;
import org.example.plantillaexamen.model.User;
import org.example.plantillaexamen.security.PasswordUtil;
import org.example.plantillaexamen.util.DaoFactory;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl() {
        // Usa la implementación configurada en DaoFactory (JDBC / ORM)
        this.userDao = DaoFactory.getUserDao();
    }

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDto authenticate(String username, String password) {
        if (username == null || password == null) {
            return null;
        }

        Optional<User> optUser = userDao.findByUsername(username);
        if (optUser.isEmpty()) {
            return null;
        }

        User user = optUser.get();

        // Comprobamos el hash SHA-256 guardado en BD
        if (PasswordUtil.matches(password, user.getPasswordHash())) {
            return UserDto.fromEntity(user);
        }

        return null;
    }

    @Override
    public UserDto register(String username, String password) {
        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {
            return null;
        }

        // ¿Ya existe el username?
        Optional<User> existing = userDao.findByUsername(username);
        if (existing.isPresent()) {
            return null;
        }

        // Generamos hash y guardamos
        String hashedPassword = PasswordUtil.hash(password);

        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(hashedPassword);

        userDao.insert(user);

        return UserDto.fromEntity(user);
    }
}

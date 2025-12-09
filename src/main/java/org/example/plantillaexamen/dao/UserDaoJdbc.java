package org.example.plantillaexamen.dao;

import org.example.plantillaexamen.model.User;
import org.example.plantillaexamen.util.JdbcConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoJdbc implements UserDao{

    // Versión con password_hash, de esta forma solo cambiaria el password_hash por password normal si no pidiera pere hash
    private static final String SELECT_ALL =
            "SELECT id, username, password_hash FROM users";

    private static final String SELECT_BY_ID =
            "SELECT id, username, password_hash FROM users WHERE id = ?";

    private static final String SELECT_BY_USERNAME =
            "SELECT id, username, password_hash FROM users WHERE username = ?";

    private static final String INSERT =
            "INSERT INTO users(username, password_hash) VALUES (?, ?)";

    private static final String UPDATE =
            "UPDATE users SET username = ?, password_hash = ? WHERE id = ?";

    private static final String DELETE =
            "DELETE FROM users WHERE id = ?";

    @Override
    public List<User> findAll() {
        List<User> result = new ArrayList<>();
        try (Connection con = JdbcConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error en findAll()", e);
        }
        return result;
    }

    @Override
    public Optional<User> findById(long id) {
        try (Connection con = JdbcConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en findById()", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try (Connection con = JdbcConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_USERNAME)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en findByUsername()", e);
        }
        return Optional.empty();
    }

    @Override
    public void insert(User user) {
        try (Connection con = JdbcConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    user.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en insert()", e);
        }
    }

    @Override
    public void update(User user) {
        try (Connection con = JdbcConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setLong(3, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error en update()", e);
        }
    }

    @Override
    public void delete(long id) {
        try (Connection con = JdbcConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error en delete()", e);
        }
    }

    private User mapRow(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getLong("id"));
        u.setUsername(rs.getString("username"));
        u.setPasswordHash(rs.getString("password_hash"));
        return u;
    }
}
/*    ejemplo sin password hasheado
    private static final String SELECT_ALL = "SELECT id, username, password FROM users";
    private static final String SELECT_BY_ID = "SELECT id, username, password FROM users WHERE id = ?";
    private static final String SELECT_BY_USERNAME = "SELECT id, username, password FROM users WHERE username = ?";
    private static final String INSERT = "INSERT INTO users(username, password) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE users SET username = ?, password = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM users WHERE id = ?";

    @Override
    public List<User> findAll() {
        List<User> result = new ArrayList<>();
        try (Connection con = JdbcConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error en findAll()", e);
        }
        return result;
    }

    @Override
    public Optional<User> findById(long id) {
        try (Connection con = JdbcConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en findById()", e);
        }
        return Optional.empty();
    }


    @Override
    public Optional<User> findByUsername(String username) {
        try (Connection con = JdbcConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_USERNAME)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en findByUsername()", e);
        }
        return Optional.empty();
    }



    @Override
    public void insert(User user) {
        try (Connection con = JdbcConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    user.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en insert()", e);
        }
    }


    @Override
    public void update(User user) {
        try (Connection con = JdbcConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setLong(3, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error en update()", e);
        }
    }

    @Override
    public void delete(long id) {
        try (Connection con = JdbcConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error en delete()", e);
        }
    }

    private User mapRow(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getLong("id"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        return u;
    }
}*/

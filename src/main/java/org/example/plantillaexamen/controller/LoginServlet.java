package org.example.plantillaexamen.controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.plantillaexamen.dao.UserDao;
import org.example.plantillaexamen.dto.UserDto;
import org.example.plantillaexamen.model.User;
import org.example.plantillaexamen.security.PasswordUtil;
import org.example.plantillaexamen.service.UserService;
import org.example.plantillaexamen.service.UserServiceImpl;
import org.example.plantillaexamen.util.DaoFactory;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        this.userService = new UserServiceImpl();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UserDto user = userService.authenticate(username, password);

        if (user != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user); // DTO en sesión

            resp.sendRedirect(req.getContextPath() + "/game");
        } else {
            req.setAttribute("loginError", "Usuari o contrasenya incorrectes");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}

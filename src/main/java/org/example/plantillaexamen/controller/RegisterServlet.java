package org.example.plantillaexamen.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.plantillaexamen.dto.UserDto;
import org.example.plantillaexamen.service.UserService;
import org.example.plantillaexamen.service.UserServiceImpl;

import java.io.IOException;

@WebServlet(name = "registerServlet", value = "/register")
public class RegisterServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        userService = new UserServiceImpl(); // Usa DaoFactory por debajo
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UserDto created = userService.register(username, password);

        if (created == null) {
            req.setAttribute("error",
                    "No s'ha pogut registrar l'usuari. El nom pot estar en ús o les dades són invàlides.");
            req.setAttribute("username", username); // para rellenar de nuevo el campo
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            return;
        }

        // Redirigimos al login indicando que acaba de registrarse
        resp.sendRedirect(req.getContextPath() + "/login?registered=true");
    }
}

package com.codegym.controller;

import com.codegym.dao.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/login.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = "";
        String password = "";
        boolean rememberme = false;
        if(req.getParameter("username")!=null){
            username = req.getParameter("username");
        }
        if(req.getParameter("password")!=null){
            password = req.getParameter("password");
        }
        if(req.getParameter("rememberme")!=null){
            if(req.getParameter("rememberme")!=null){
                rememberme = Boolean.parseBoolean(req.getParameter("rememberme"));
            }

        }
        System.out.println("rememberme: " + rememberme);
        System.out.println("username: " + username);
        System.out.println("password: " + password);
        if(UserDAO.checkUserExists(username, password)){
            if(rememberme){
                Cookie cookieUserName = new Cookie("username", username);
                cookieUserName.setMaxAge(24*60*60);
                Cookie cookiePassword = new Cookie("password", password);
                cookiePassword.setMaxAge(24*60*60);

                resp.addCookie(cookieUserName);
                resp.addCookie(cookiePassword);
            }
            resp.sendRedirect("/index");
        }else{
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/login.jsp");
            requestDispatcher.forward(req, resp);
        }

    }

}

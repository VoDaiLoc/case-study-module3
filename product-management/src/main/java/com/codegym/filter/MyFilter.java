package com.codegym.filter;

import com.codegym.dao.UserDAO;
import com.codegym.security.UrlPatternUtils;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@WebFilter(urlPatterns = "/*")
public class MyFilter implements Filter {
    private static final String ATT_NAME_USER_NAME = "username";
    private static final String ATT_NAME_USER_PASS = "password";


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");

        ServletContext servletContext = request.getServletContext();
        String servletPath = req.getServletPath();
        String pathInfo = req.getPathInfo();

        String urlPattern = null;
        if (pathInfo != null) {
            urlPattern = servletPath + "/*";
        }
        urlPattern = servletPath;
        boolean has = UrlPatternUtils.hasUrlPattern(servletContext, urlPattern);

        //Servlet: localhost:8080/login, localhost:8080/product, localhost:8080/users
        //Resource: localhost:8080/assets/css.... ; localhost:8080/assets/js....

        if(has){
            String username = "";
            String password = "";
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (ATT_NAME_USER_NAME.equals(cookie.getName())) {
                        username = cookie.getValue();
                    }
                    if (ATT_NAME_USER_PASS.equals(cookie.getName())) {
                        password = cookie.getValue();
                    }
                }
            }
            System.out.println("Cookie username:" + username);
            System.out.println("Cookie password:" + password);
            if(!req.getServletPath().equals("/login")){
                if(!username.equals("")&&!password.equals("")&& UserDAO.checkUserExists(username, password)){
                    HttpSession httpSession = req.getSession();
                    httpSession.setAttribute("username", username);
                    chain.doFilter(req, res);
                    return;
                }else{
                    res.sendRedirect("/login");
                    return;
                }
            }else{
                chain.doFilter(req, res);
            }
        }else{
            System.out.println("Resource info: " + req.getRequestURI());
            chain.doFilter(req, res);

        }

    }

}

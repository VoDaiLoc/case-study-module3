package com.codegym.controller;

import com.codegym.dao.UserDAO;
import com.codegym.model.Product;
import com.codegym.model.User;
import com.codegym.utils.ValidateUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = "/users")
public class UserServlet extends HttpServlet {

    private  UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    insertUser(request, response);
                    break;
                case "edit":
                    updateUser(request, response);
                    break;
                case "delete":
                    deleteUser(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "create":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteUser(request, response);
                    break;
                default:
                    listNumberPage(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

//    private void listUser(HttpServletRequest request, HttpServletResponse response)
//            throws SQLException, IOException, ServletException {
//        List<User> listUser = userDAO.selectAllUsers();
//        request.setAttribute("listUser", listUser);
//        System.out.println(listUser);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/user/list.jsp");
//        dispatcher.forward(request, response);
//    }

    private void listNumberPage(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ClassNotFoundException, ServletException, IOException {
        System.out.println("numberPage");
        int page = 1;
        int recordsPerPage = 7;
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        };
        String name = "";
        if (req.getParameter("searchuser") != null) {
            name = req.getParameter("searchuser");
        }
        List<User> listUser = userDAO.getNumberPage((page - 1) * recordsPerPage, recordsPerPage, name);
        int noOfRecords = userDAO.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("listUser", listUser);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        req.setAttribute("searchuser" , name);



        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/user/list.jsp");
        requestDispatcher.forward(req, resp);

    }
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/user/add.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String id = request.getParameter("id");
        User existingUser = userDAO.selectUser(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/user/edit.jsp");
        request.setAttribute("user", existingUser);
        dispatcher.forward(request, response);

    }

//    private void showDeleteForm(HttpServletRequest request, HttpServletResponse response)
//            throws SQLException, ServletException, IOException {
//        int id = Integer.parseInt(request.getParameter("id"));
//        User existingUser = userDAO.selectUser(id);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("user/delete.jsp");
//        request.setAttribute("user", existingUser);
//        dispatcher.forward(request, response);
//
//    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        String name = request.getParameter("fullname");
//        String phone = request.getParameter("phone");
//        String email = request.getParameter("email");
//        String address = request.getParameter("address");
//        User newUser = new User(username, password, name,phone,email,address);
//        System.out.println("alo");
//        System.out.println(newUser);
//        userDAO.insertUser(newUser);
//
//
//
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/user/add.jsp");
//        dispatcher.forward(request, response);

        User user;
        String userName = request.getParameter("username").replaceAll(" ", "").toLowerCase();
        String password = request.getParameter("password").trim();
        String fullName = request.getParameter("fullname").trim();
        String phone = request.getParameter("phone").trim();
        String email = request.getParameter("email").trim();
        String address = request.getParameter("address").trim();
        List<String> errors = new ArrayList<>();
        boolean isPassword = ValidateUtils.isPasswordVailid(password);
        boolean isPhone = ValidateUtils.isNumberPhoneVailid(phone);
        boolean isEmail = ValidateUtils.isEmailValid(email);
//        boolean isUserName = ValidateUtils.isUserNameVailid(userName);

        user = new User(userName, password, fullName, phone, email, address);
        if (userName.isEmpty() ||
                password.isEmpty() ||
                fullName.isEmpty() ||
                phone.isEmpty() ||
                email.isEmpty() ||
                address.isEmpty() ){
            errors.add("Vui lòng điền đầy đủ thông tin");
        }
        if (userName.isEmpty()) {
            errors.add("UserName không được để trống");
        }
        if (password.isEmpty()) {
            errors.add("Password không được để trống");
        }
        if (fullName.isEmpty()) {
            errors.add("Fullname không được để trống");
        }
        if (phone.isEmpty()) {
            errors.add("Phone Nhập vào không đúng");
        }
        if (!isPhone) {
            errors.add("Phone không đúng định dạng");
        }
        if (userDAO.existByPhone(phone)) {
            errors.add("Phone đã tồn tại!");
        }
        if (email.isEmpty()) {
            errors.add("Email nhập vào không đúng");
        }
        if (!isEmail) {
            errors.add("Email nhập vào không đúng dịnh dạng");
        }
        if (address.isEmpty()) {
            errors.add("Address không được để trống");
        }

        if (userDAO.existsByEmail(email)) {
            errors.add("Email đã tồn tại");
        }
//        if (!isUserName) {
//            errors.add("UserName không đúng định dạng (không có khoảng cách) ");
//        }
        if (userDAO.existByUsername(userName)) {
            errors.add("Username này đã tồn tại!");
        }
        if (!isPassword) {
            errors.add("Password không đúng định dạng");
        }

        if (errors.size() == 0) {
            user = new User(userName, password, fullName, phone, email, address);
            boolean success = false;
            success = userDAO.insertUser(user);

            if (success) {
                request.setAttribute("success", true);
            } else {
                request.setAttribute("errors", true);
                errors.add("Dữ liệu không hợp lệ, Vui lòng kiểm tra lại!");
            }

        }
        if (errors.size() > 0) {
            request.setAttribute("errors", errors);
            request.setAttribute("inserUser", user);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/user/add.jsp");
        dispatcher.forward(request, response);
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
//        int id = Integer.parseInt(request.getParameter("id"));
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        String fullName = request.getParameter("fullname");
//        String phone = request.getParameter("phone");
//        String email = request.getParameter("email");
//        String address = request.getParameter("address");
//        User newUser = new User(id,username, password, fullName,phone,email,address);
//        userDAO.updateUser(newUser);
////        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/user/list.jsp");
////        dispatcher.forward(request, response);
//        response.sendRedirect("/users");
        List<String> errors = new ArrayList<>();
        User user = null;
        String id= request.getParameter("id");
        boolean isId = ValidateUtils.isIntValid(id);
        int checkId=0;
        if (isId) {
            checkId=Integer.parseInt(id);
        }else {
            errors.add("ID phải là số nguyên dương !");
        }
        if (!userDAO.existByUserId(checkId)) {
            errors.add("ID phải có thật!");
        }

//        if (!isId) {
//            errors.add("ID phải là số nguyên dương !");
//        }
//        String userName = request.getParameter("username");
////                replaceAll(" ", "").toLowerCase();
//        System.out.println("usernam"+userName);
//        String password = request.getParameter("password");
        String fullName = request.getParameter("fullname");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        boolean isPhone = ValidateUtils.isNumberPhoneVailid(phone);
        boolean isEmail = ValidateUtils.isEmailValid(email);
        user = new User(id, fullName, phone,email, address);
        User userEmail = userDAO.selectUser(id);
        String checkEmail = userEmail.getEmail();
        User userPhone = userDAO.selectUser(id);
        String checkPhone = userPhone.getPhone();
        if (fullName.isEmpty() ||
                phone.isEmpty() ||
                email.isEmpty() ||
                address.isEmpty()) {
            errors.add("Hãy nhập đầy đủ thông tin");
        }
//        try {
//           userId = Long.parseLong(userIdRaw);
//        user = new User(id, fullName, phone, address);
//        }catch (NumberFormatException e){
//            errors.add("ID không tồn tại");
//        }
//        if (userName==null || password == null) {
//            errors.add("username và password không được null!");
//        }
        if (fullName.isEmpty()) {
            errors.add("Full name không được để trống");
        }
        if (phone.isEmpty()) {
            errors.add("Phone không được để trống");
        }
        if (email.isEmpty()) {
            errors.add("Email không được bỏ trống");
        }
        if (!isEmail) {
            errors.add("Email không đúng định dạng");
        }

        if (userDAO.existsByEmail(email) && !email.equals(checkEmail)){
            errors.add("Email đã tồn tại");
        }
        if (!isPhone) {
            errors.add("Phone không đúng định dạng");
        }
        if (userDAO.existByPhone(phone) && !phone.equals(checkPhone)){
            errors.add("Phone đã tồn tại");
        }
        if (address.isEmpty()) {
            errors.add("Address không được để trống");
        }


        if (errors.size() == 0) {
            user = new User(id, fullName, phone,email, address);
            boolean success = false;
            success = userDAO.updateUser(user);
            if (success) {
                request.setAttribute("success", true);
            } else {
                request.setAttribute("errors", true);
                errors.add("Invalid data, Please check again!");
            }
        }
        else {
            request.setAttribute("errors", errors);
            request.setAttribute("user", user);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/user/edit.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        userDAO.deleteUser(id);
        response.sendRedirect("/users");
//        List<User> listUser = userDAO.selectAllUsers();
//        request.setAttribute("listUser", listUser);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/user/list.jsp");
//        dispatcher.forward(request, response);
    }

    private void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> listUser = null;
        String name = "";
        if (req.getParameter("searchuser") != null) {
            name = req.getParameter("searchuser");

            listUser = userDAO.searchUser(name);
        } else {
            listUser = userDAO.selectAllUsers();
        }
        req.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/user/list.jsp");
        dispatcher.forward(req, resp);
    }
}

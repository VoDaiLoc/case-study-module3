package com.codegym.controller;

import com.codegym.dao.ProductDAO;
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

@WebServlet(name = "ProductServlet", urlPatterns = "/product")
public class ProductServlet extends HttpServlet {
    private ProductDAO productDAO;

    public void init() {
        productDAO = new ProductDAO();
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
                    insertProduct(request, response);
                    break;
                case "edit":
                    updateProduct(request, response);
                    break;
                case "delete":
                    deleteProduct(request, response);
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
                    deleteProduct(request, response);
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

//    private void listProduct(HttpServletRequest request, HttpServletResponse response)
//            throws SQLException, IOException, ServletException {
//        List<Product> listProduct = productDAO.selectAllProduct();
//        request.setAttribute("listProduct", listProduct);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/product/list.jsp");
//        dispatcher.forward(request, response);
//    }

    private void listNumberPage(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ClassNotFoundException, ServletException, IOException {
        System.out.println("numberPage");
        int page = 1;
        int recordsPerPage = 5;
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        };
        String name = "";
        if (req.getParameter("searchproduct")!=null) {
            name = req.getParameter("searchproduct");
        }
        List<Product> listProduct = productDAO.getNumberPage((page - 1) * recordsPerPage, recordsPerPage, name);
        int noOfRecords = productDAO.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
//        System.out.println("noOfPages" + noOfPages);
//        System.out.println(noOfRecords);
        req.setAttribute("listProduct", listProduct);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        req.setAttribute("searchproduct", name);


        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/product/list.jsp");
        requestDispatcher.forward(req, resp);

    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/product/add.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String id = request.getParameter("id");
        Product existingProduct = productDAO.selectProduct(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/product/edit.jsp");
        request.setAttribute("product", existingProduct);
        dispatcher.forward(request, response);

    }

//    private void showDeleteForm(HttpServletRequest request, HttpServletResponse response)
//            throws SQLException, ServletException, IOException {
//        int id = Integer.parseInt(request.getParameter("id"));
//        Product existingUser = productDAO.selectProduct(id);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/product/delete.jsp");
//        request.setAttribute("product", existingUser);
//        dispatcher.forward(request, response);
//
//    }

    private void insertProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        Product product;
        String name = request.getParameter("name").trim();
        String price = request.getParameter("price").trim();
        String quantity = request.getParameter("quantity").trim();
        String image = request.getParameter("image").trim();
        List<String> errors = new ArrayList<>();
        boolean isPrice = ValidateUtils.isNumberVailid(price);
        boolean isQuantity = ValidateUtils.isNumberVailid(quantity);


        product = new Product(name, price, quantity,image);


        if (
                name.isEmpty() ||
                        price.isEmpty() ||
                        quantity.isEmpty()
        ) {
            errors.add("Vui lòng điền đầy đủ thông tin");
        }

        if (name.isEmpty()) {
            errors.add("Name Product không được để trống");
        }
        if (price.isEmpty()) {
            errors.add("Price Product không được để trống");
        }
        if (quantity.isEmpty()) {
            errors.add("Quantity Product không được để trống");
        }

//        if (!isQuantity) {
//            errors.add("Quantity phải là một số thực >= 0");
//        }
//        if (!isPrice) {
//            errors.add("Price phải là một số thực");
//        }
        double checkPrice=1.0;
        try {
            if (isPrice)
                checkPrice = Double.parseDouble(price);
        } catch (Exception e) {
            errors.add("Định dạng giá không hợp lệ");
        }
        if (!isPrice || checkPrice<=0 || checkPrice>500000) {
            errors.add("Price phải là số thực lớn hơn 0 và nhỏ hơn 500000");
        }

        double checkQuantity=1.0;
        try {
            if (isQuantity)
                checkQuantity = Double.parseDouble(quantity);
        } catch (Exception e) {
            errors.add("Định dạng số lượng không hợp lệ");
        }
        if (!isQuantity || checkQuantity < 0 || checkQuantity>1000) {
            errors.add("Quantity phải là số thực >= 0 và <1000");
        }
//        try{
//
//            if (checkPrice >  500000){
//                errors.add("Price phải <= 500000 đồng!");
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            errors.add("Định dạng giá không hợp lệ");
//        }


        if (errors.size() == 0) {
            product = new Product(name, price, quantity, image);
            boolean success = false;
            success = productDAO.insertProduct(product);
            if (success) {
                request.setAttribute("success", true);
            } else {
                request.setAttribute("errors", true);
                errors.add("Invalid data, Please check again!");
            }


        }
        if (errors.size() > 0) {
            request.setAttribute("errors", errors);
            request.setAttribute("inserProduct", product);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/product/add.jsp");
        dispatcher.forward(request, response);
    }


    private void updateProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<String> errors = new ArrayList<>();
        Product product;
        String id= request.getParameter("id").trim();
        boolean isId = ValidateUtils.isIntValid(id);
        int checkId=0;
        if (isId) {
            checkId=Integer.parseInt(id);
        }
        if (!productDAO.existByProductId(checkId)) {
            errors.add("ID phải có thật!");
        }

        if (!isId) {
            errors.add("ID phải là số nguyên dương !");
        }

        String name = request.getParameter("name").trim();
        String price = request.getParameter("price").trim();
        String quantity = request.getParameter("quantity").trim();
        String image = request.getParameter("image");
        boolean isPrice = ValidateUtils.isNumberVailid(price);
        boolean isQuantity = ValidateUtils.isNumberVailid(quantity);
        product = new Product(id, name, price, quantity,image );

        if (
                name.isEmpty() ||
                        price.isEmpty() ||
                        quantity.isEmpty()
        ) {
            errors.add("Hãy nhập đầy đủ thông tin");
        }
        if (name.isEmpty()) {
            errors.add("Name không được để trống");
        }
        if (price.isEmpty()) {
            errors.add("Price không được để trống");
        }
        if (quantity.isEmpty()) {
            errors.add("Quantity không được để trống");
        }

        double checkPrice=1.0;
        try {
            if (isPrice)
                checkPrice = Double.parseDouble(price);
        } catch (Exception e) {
            errors.add("Định dạng giá không hợp lệ");
        }
        if (!isPrice || checkPrice<=0 || checkPrice>500000) {
            errors.add("Price phải là số thực lớn hơn 0 và nhỏ hơn 500000");
        }

        double checkQuantity=1.0;
        try {
            if (isQuantity)
                checkQuantity = Double.parseDouble(quantity);
        } catch (Exception e) {
            errors.add("Định dạng số lượng không hợp lệ");
        }
        if (!isQuantity || checkQuantity < 0 || checkQuantity>1000) {
            errors.add("Quantity phải là số thực >= 0 và <1000");
        }
//        double checkPrice=1.0;
//        try {
//            if (isPrice)
//                checkPrice = Double.parseDouble(price);
//        } catch (Exception e) {
//            errors.add("Price phải là một số và là một số dương");
//        }
//        if (!isPrice || checkPrice==0) {
//            errors.add("Price phải là một số và là một số dương");
//        }
//        try{
//
//            if (checkPrice >  500000){
//                errors.add("Price phải <= 500000 đồng!");
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            errors.add("Định dạng giá không hợp lệ");
//        }
//        if (!isQuantity) {
//            errors.add("Quantity phải là một số thực >= 0");
//        }
        if (errors.size() == 0) {
            product = new Product(id, name, price, quantity, image);
            boolean success = false;
            success = productDAO.updateProduct(product);
            if (success) {
                request.setAttribute("success", true);
            } else {
                request.setAttribute("errors", true);
                errors.add("Invalid data, Please check again!");
            }
        }
        else  {
            request.setAttribute("errors", errors);
            request.setAttribute("product", product);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/product/edit.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        productDAO.deleteProduct(id);

        List<Product> listProduct = productDAO.selectAllProduct();
        request.setAttribute("listProduct", listProduct);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/product/list.jsp");
//        dispatcher.forward(request, response);
        response.sendRedirect("/product");
    }
}

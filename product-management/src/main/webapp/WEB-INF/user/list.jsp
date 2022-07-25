<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 20/07/2022
  Time: 9:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Vertical layout | Zircos - Responsive Bootstrap 4 Admin Dashboard</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta content="Responsive bootstrap 4 admin template" name="description">
    <meta content="Coderthemes" name="author">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
<%--    <link href="../../assets\css\bootstrap.min.css" rel="stylesheet" type="text/css" id="bootstrap-stylesheet">--%>
<%--    <link href="../../assets\css\icons.min.css" rel="stylesheet" type="text/css">--%>
<%--    <link href="../../assets\css\app.min.css" rel="stylesheet" type="text/css" id="app-stylesheet">--%>
<%--    <link rel="stylesheet" href="../../assets\css\css.css" type="text/css" id="css-stylesheet">--%>
<%--    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />--%>


    <jsp:include page="/WEB-INF/layout/meta_css.jsp"></jsp:include>
<%--    <style>--%>
<%--        @font-face {--%>
<%--            font-family: 'Source Sans Pro';--%>
<%--            font-style: normal;--%>
<%--            font-weight: 300;--%>
<%--            font-display: swap;--%>
<%--            src: local('Source Sans Pro Light'), local('SourceSansPro-Light'), url(s/sourcesanspro/v14/6xKydSBYKcSV-LCoeQqfX1RYOo3ik4zwlxdr.ttf) format('truetype');--%>
<%--        }--%>
<%--        @font-face {--%>
<%--            font-family: 'Source Sans Pro';--%>
<%--            font-style: normal;--%>
<%--            font-weight: 400;--%>
<%--            font-display: swap;--%>
<%--            src: local('Source Sans Pro Regular'), local('SourceSansPro-Regular'), url(s/sourcesanspro/v14/6xK3dSBYKcSV-LCoeQqfX1RYOo3qOK7g.ttf) format('truetype');--%>
<%--        }--%>
<%--        @font-face {--%>
<%--            font-family: 'Source Sans Pro';--%>
<%--            font-style: normal;--%>
<%--            font-weight: 600;--%>
<%--            font-display: swap;--%>
<%--            src: local('Source Sans Pro SemiBold'), local('SourceSansPro-SemiBold'), url(s/sourcesanspro/v14/6xKydSBYKcSV-LCoeQqfX1RYOo3i54rwlxdr.ttf) format('truetype');--%>
<%--        }--%>
<%--    </style>--%>
</head>

<body>

<!-- Begin page -->
<div id="wrapper">


    <!-- Topbar Start -->
    <jsp:include page="/WEB-INF/layout/navbar-custom.jsp"></jsp:include>
    <!-- end Topbar -->

    <!-- ========== Left Sidebar Start ========== -->
    <jsp:include page="/WEB-INF/layout/left-side-menu.jsp"></jsp:include>

    <!-- Left Sidebar End -->

    <!-- ============================================================== -->
    <!-- Start Page Content here -->
    <!-- ============================================================== -->

    <div class="content-page">
        <div class="content">

            <!-- Start Content-->
            <div class="container-fluid">



                <div class="row">
                    <div class="col-xl-12">
                        <div class="card-box">
                            <div style="display: flex;justify-content: space-between">
                                <h1 class="list">List Users</h1>
                                <form method="get" action="/users?action=search" class="app-search">
                                    <div class="app-search-box">
                                        <div class="input-group">
                                            <input oninput="search(event)" style="border-radius: 20px" type="text" id="searchuser" name="searchuser" class="form-control" placeholder="Search..." value="${requestScope.searchuser}">
                                            <div class="input-group-append">
                                                <button class="btn " type="submit">
                                                    <i class="fas fa-search text-dark"></i>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                                <button type="button" class="btn-add btn-primary"><a href="/users?action=create" >Add New User</a></button>
                            </div>
                            <div class="table-responsive">
                                <table class="table table-hover table-centered m-0">
                                    <thead>
                                    <tr style="background-color: #008a00;color: white">
                                        <th>ID</th>
                                        <th>Full Name</th>
                                        <th>Phone</th>
                                        <th>Email</th>
                                        <th>Address</th>
                                        <th>Actions</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    <c:forEach var="user" items="${listUser}">
                                        <tr>
                                            <td>${user.getId()}</td>
                                            <td>${user.getFullName()}</td>
                                            <td>${user.getPhone()}</td>
                                            <td>${user.getEmail()}</td>
                                            <td>${user.getAddress()}</td>
                                            <td>
                                                <button class="edit"><a href="/users?action=edit&id=${user.getId()}"><i class="fas fa-edit"></i></a></button>
                                                <button class="delete"><a href="/users?action=delete&id=${user.getId()}" onclick="return confirm('Bạn có chắc chắn muốn xóa người dùng này không?');"><i class="fas fa-trash"></i></a></button>
<%--                                                <button style="    border: 1px solid blue;--%>
<%--    border-radius: 5px;--%>
<%--    font-size: 20px;--%>
<%--    background-color: palevioletred;--%>
<%--    margin-right: 15px;"><a href="/users?action=delete&id=${user.getId()}"><i class="fas fa-trash"></i></a></button>--%>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                    </tbody>
                                </table>

                            </div>
                            <nav aria-label="Page navigation example">
                                <ul class="pagination">

                                    <c:if test="${requestScope.currentPage != 1}">
                                        <li class="page-item"><a class="page-link"
                                                                 href="/users?page=${requestScope.currentPage - 1}&searchuser=${requestScope.searchuser}">Previous</a>
                                        </li>
                                    </c:if>
                                    <c:forEach begin="1" end="${noOfPages}" var="i">
                                        <c:choose>
                                            <c:when test="${requestScope.currentPage eq i}">
                                                <li class="page-item"><a class="page-link"
                                                                         href="/users?page=${i}&searchuser=${requestScope.searchuser}">${i}</a></li>
                                            </c:when>
                                            <c:otherwise>
                                                <li class="page-item"><a class="page-link"
                                                                         href="/users?page=${i}&searchuser=${requestScope.searchuser}">${i}</a></li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                    <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
                                        <li class="page-item"><a class="page-link"
                                                                 href="/users?page=${requestScope.currentPage + 1}&searchuser=${requestScope.searchuser}">Next</a>
                                        </li>
                                    </c:if>
                                </ul>
                            </nav>
                            <!-- table-responsive -->
                        </div>
                        <!-- end card -->
                    </div>
                    <!-- end col -->



                </div>
                <!-- end row -->

            </div>
            <!-- end container-fluid -->

        </div>
        <!-- end content -->

        <!-- Footer Start -->
        <footer class="footer">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-12">
                        2018 - 2020 &copy; Zircos theme by <a href="">Coderthemes</a>
                    </div>
                </div>
            </div>
        </footer>
        <!-- end Footer -->

    </div>
    <!-- END wrapper -->

    <!-- Right Sidebar -->

<%--    <script>--%>
<%--        function remove() {--%>
<%--            let choice;--%>
<%--            let cf = confirm("Are You Sure?");--%>
<%--            if (cf == true) {--%>
<%--                choice = "Nút OK đã được bấm!";--%>
<%--            } else {--%>
<%--                choice = "Nút Cancel đã được bấm!";--%>
<%--            }--%>
<%--        }--%>
<%--    </script>--%>

    <!-- Vendor js -->
    <jsp:include page="/WEB-INF/layout/script.jsp"></jsp:include>

</body>

</html>

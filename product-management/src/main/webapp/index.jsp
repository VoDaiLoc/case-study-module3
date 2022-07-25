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
    <jsp:include page="/WEB-INF/layout/meta_css.jsp"></jsp:include>

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



                <div  class="row">
                    <img style="width: 100%" src="https://suno.vn/blog/wp-content/uploads/2018/04/ban-trai-cay.jpg" alt="Image">
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

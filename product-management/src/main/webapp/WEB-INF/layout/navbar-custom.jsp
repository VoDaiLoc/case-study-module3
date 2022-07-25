<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="navbar-custom">
    <ul class="list-unstyled topnav-menu float-right mb-0">
        <li class="dropdown notification-list">
            <%
                String username = "";
                if(session.getAttribute("username")!=null){
                    username = (String) session.getAttribute("username");
                    System.out.println("JSP username: " + username);
                }

            %>
            <a class="nav-link dropdown-toggle nav-user mr-0 waves-effect" data-toggle="dropdown" href="#" role="button" aria-haspopup="false" aria-expanded="false">
                <img src="/assets/images/avatardefault.png" alt="user-image" class="rounded-circle">
                <span class="d-none d-sm-inline-block ml-1"><%=username%></span>
            </a>
            <div class="dropdown-menu dropdown-menu-right profile-dropdown ">
                <!-- item-->
                <div class="dropdown-header noti-title">
                    <h6 class="text-overflow m-0">Welcome !</h6>
                </div>
                <!-- item-->
                <div class="dropdown-divider"></div>
                <!-- item-->
                <a href="/logout" class="dropdown-item notify-item">
                    <i class="mdi mdi-logout-variant"></i>
                    <span>Logout</span>
                </a>

            </div>
        </li>
    </ul>

    <!-- LOGO -->
    <div class="logo-box">
        <a href="index.jsp" class="logo text-center">
                        <span class="logo-lg">
                           <img style="width: 100%;height: 100%" src="/assets/images/logo_shop.png" alt="logo" height="18">
                            <!-- <span class="logo-lg-text-light">Zircos</span> -->
                        </span>
            <span class="logo-sm">
                            <!-- <span class="logo-sm-text-dark">Z</span> -->
                            <img style="width: 100%;height: 100%" src="/assets/images/logo_shop.png" alt="" height="24">
                        </span>
        </a>
    </div>

    <ul class="list-unstyled topnav-menu topnav-menu-left m-0">
        <li>
            <button class="button-menu-mobile waves-effect">
                <i class="mdi mdi-menu"></i>
            </button>
        </li>
        <ul class="list-unstyled topnav-menu topnav-menu-left m-0">
            <li class="d-none d-sm-block">
<%--                <form method="get" action="/users?action=search" class="app-search">--%>
<%--                    <div class="app-search-box">--%>
<%--                        <div class="input-group">--%>
<%--                            <input type="text" name="search" class="form-control" placeholder="Search..." value="${requestScope.search}">--%>
<%--                            <div class="input-group-append">--%>
<%--                                <button class="btn" type="submit">--%>
<%--                                    <i class="fas fa-search"></i>--%>
<%--                                </button>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </form>--%>
            </li>
        </ul>

    </ul>
</div>
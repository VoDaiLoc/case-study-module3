//Interface
List<Customer> getNumberPage(int offset, int noOfRecords)




// USER DAO
private int noOfRecords;

public int getNoOfRecords(){
return noOfRecords;
}





public List<Customer> getNumberPage(int offset, int noOfRecords) throws ClassNotFoundException, SQLException {
Connection connection = connectionSQL.getConnection();
System.out.println("numberpage");

String query = "SELECT SQL_CALC_FOUND_ROWS * FROM customer limit " + offset + "," + noOfRecords;
List<Customer> list = new ArrayList<>();
ps = connection.prepareStatement(query);
rs = ps.executeQuery();
while (rs.next()){
Customer customer = new Customer();
customer.setIdCustomer(rs.getInt("idCustomer"));
customer.setFullName(rs.getString("fullName"));
customer.setPassword(rs.getString("password"));
customer.setAddress(rs.getString("address"));
customer.setPhone(rs.getString("phone"));
customer.setEmail(rs.getString("email"));
customer.setIdRole(rs.getInt("idRole"));
list.add(customer);
}
rs = ps.executeQuery("SELECT FOUND_ROWS()");
if (rs.next()){
this.noOfRecords = rs.getInt(1);
}
connection.close();
return list;
}






// servlet USER
private void listNumberPage(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ClassNotFoundException, ServletException, IOException {
System.out.println("numberPage");
int page = 1;
int recordsPerPage = 5;
if (req.getParameter("page") != null) {
page = Integer.parseInt(req.getParameter("page"));
};
List<Customer> customerList = customerDAO.getNumberPage((page - 1) * recordsPerPage, recordsPerPage);
int noOfRecords = customerDAO.getNoOfRecords();
int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
//        System.out.println("noOfPages" + noOfPages);
//        System.out.println(noOfRecords);
req.setAttribute("listCustomer", customerList);
req.setAttribute("noOfPages", noOfPages);
req.setAttribute("currentPage", page);


RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/view/admin/index.jsp");
requestDispatcher.forward(req, resp);

}


/// JSP

<nav aria-label="Page navigation example">
    <ul class="pagination">

        <c:if test="${requestScope.currentPage != 1}">
            <li class="page-item"><a class="page-link"
                                     href="customer?page=${requestScope.currentPage - 1}">Previous</a>
            </li>
        </c:if>
        <c:forEach begin="1" end="${noOfPages}" var="i">
            <c:choose>
                <c:when test="${requestScope.currentPage eq i}">
                    <li class="page-item"><a class="page-link"
                                             href="customer?page=${i}">${i}</a></li>
                </c:when>
                <c:otherwise>
                    <li class="page-item"><a class="page-link"
                                             href="customer?page=${i}">${i}</a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
            <li class="page-item"><a class="page-link"
                                     href="customer?page=${requestScope.currentPage + 1}">Next</a>
            </li>
        </c:if>
    </ul>
</nav>

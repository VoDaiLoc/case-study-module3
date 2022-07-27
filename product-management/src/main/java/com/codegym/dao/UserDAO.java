package com.codegym.dao;

import com.codegym.connection.ConnectionMySQL;
import com.codegym.connection.IConnectionMySQL;
import com.codegym.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO{

    private IConnectionMySQL connectionMySQL = new ConnectionMySQL();

    private static final String INSERT_USERS_SQL = "INSERT INTO user (username, password, fullName, phone, email, address) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String SELECT_USER_BY_ID = "select id,username,password,fullName,phone,email,address from user where id =?";
    private static final String SELECT_ALL_USERS = "select * from user";
    private static final String DELETE_USERS_SQL = "delete from user where id = ?;";
    private static final String UPDATE_USERS_SQL = "update user set fullName= ?, phone= ?, email= ?, address= ? where id = ?;";
    private static final String SEARCH_BY_NAME_TYPE = "SELECT * FROM user  WHERE  fullName LIKE ? OR phone LIKE ? OR email LIKE ? ; ";
    private static final String USER_EXIST_BY_PHONE = "" +
            "SELECT COUNT(*) AS COUNT " +
            "FROM user AS u " +
            "WHERE u.phone = ?;";
    private static String USER_EXIST_BY_EMAIL = "" +
            "SELECT COUNT(*) AS COUNT " +
            "FROM user AS u " +
            "WHERE u.email = ?;";


    private static String USER_EXIST_BY_USERNAME = "" +
            "SELECT COUNT(*) AS COUNT " +
            "FROM user AS u " +
            "WHERE u.username = ?;";

    private static String USER_EXIST_BY_ID = "" +
            "SELECT COUNT(*) AS COUNT " +
            "FROM user AS u " +
            "WHERE u.id = ?;";
    private int noOfRecords;


    public int getNoOfRecords(){
        return noOfRecords;
    }
    @Override
    public boolean insertUser(User user) throws SQLException {
        boolean rowInsert = false;
        System.out.println(INSERT_USERS_SQL);
        // try-with-resource statement will auto close the connection.
        try (Connection connection = connectionMySQL.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFullName());
            preparedStatement.setString(4, user.getPhone());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getAddress());
            rowInsert =  preparedStatement.executeUpdate()>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowInsert;
    }
    public List<User> getNumberPage(int offset, int noOfRecords, String name) throws ClassNotFoundException, SQLException {
        Connection connection = connectionMySQL.getConnection();
        System.out.println("numberpage");

        String query = "SELECT SQL_CALC_FOUND_ROWS * FROM user where fullName LIKE ? OR phone LIKE ? OR email LIKE ? limit " + offset + "," + noOfRecords;
        List<User> list = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, '%' + name + '%');
        ps.setString(2, '%' + name + '%');
        ps.setString(3, '%' + name + '%');

        System.out.println(this.getClass() + " getNumberPage() query: " + ps);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            User user = new User();
            user.setId(rs.getString("id"));
            user.setUsername(rs.getString("username"));
            user.setFullName(rs.getString("fullname"));
            user.setPassword(rs.getString("password"));
            user.setAddress(rs.getString("address"));
            user.setPhone(rs.getString("phone"));
            user.setEmail(rs.getString("email"));
            list.add(user);
        }
        rs = ps.executeQuery("SELECT FOUND_ROWS()");
        if (rs.next()){
            this.noOfRecords = rs.getInt(1);
        }
        connection.close();
        return list;
    }

    @Override
    public boolean existsByEmail(String email) {
        boolean exist = false;
        try {
            Connection connection = connectionMySQL.getConnection();
            PreparedStatement statement = connection.prepareCall(USER_EXIST_BY_EMAIL);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int count = rs.getInt("count");
                if (count > 0) {
                    exist = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }

    @Override
    public boolean existByUsername(String username) {
        boolean exist = false;
        try {
            Connection connection = connectionMySQL.getConnection();
            PreparedStatement statement = connection.prepareCall(USER_EXIST_BY_USERNAME);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int count = rs.getInt("count");
                if (count > 0) {
                    exist = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }



    @Override
    public boolean existByUserId(int userId) {
        boolean exist = false;
        try {
            Connection connection = connectionMySQL.getConnection();
            PreparedStatement statement = connection.prepareCall(USER_EXIST_BY_ID);
            statement.setLong(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int count = rs.getInt("count");
                if (count > 0) {
                    exist = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }

    @Override
    public boolean existByPhone(String phone) {
        boolean exist = false;
        try {
            Connection connection = connectionMySQL.getConnection();
            PreparedStatement statement = connection.prepareCall(USER_EXIST_BY_PHONE);
            statement.setString(1, phone);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int count = rs.getInt("count");
                if (count > 0) {
                    exist = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }

    @Override
    public User selectUser(String id) {
        User user = null;
        try {
            Connection connection = connectionMySQL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String userName = rs.getString("username");
                String password = rs.getString("password");
                String name = rs.getString("fullname");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getString("address");
                user = new User(id, userName, password, name, phone, email , address);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }


    @Override
    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        // Step 1: Establishing a Connection
        try {
            Connection connection = connectionMySQL.getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String id = rs.getString("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String name = rs.getString("fullname");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getString("address");

                users.add(new User(id,username,password,name,phone,email,address));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = connectionMySQL.getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = connectionMySQL.getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
//            statement.setString(1, user.getUsername());
//            statement.setString(2, user.getPassword());
            statement.setString(1, user.getFullName());
            statement.setString(2, user.getPhone());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getAddress());
            statement.setString(5,user.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    @Override
    public List<User> searchUser(String name) {
        List<User> listUser = new ArrayList<>();
        try {
            Connection connection = connectionMySQL.getConnection();
            PreparedStatement preparedStatement = connection.prepareCall(SEARCH_BY_NAME_TYPE);
            preparedStatement.setString(1, '%' + name + '%');
            preparedStatement.setString(2, '%' + name + '%');
            preparedStatement.setString(3, '%' + name + '%');
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String fullName = rs.getString("fullName");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getString("address");
                listUser.add(new User(id, fullName, phone, email, address));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listUser;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
    public static boolean checkUserExists(String username, String password){
        if(username.equals("admin")&&password.equals("admin")){
            return true;
        }
        List<User> list = new ArrayList<>();
        for (User user:list) {
            if (user.getUsername().equals(username)&&user.getPassword().equals(password))
                return true;
        }
        return false;
    }
}

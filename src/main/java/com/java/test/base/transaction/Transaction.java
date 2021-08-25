package com.java.test.base.transaction;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author yzm
 * @date 2021/8/17 - 9:27
 */
public class Transaction {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/db_test";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) throws SQLException {
        Connection conn = getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

        insert("123", conn);
//        conn.commit();
        select("123", conn);

        conn.close();
    }

    public static Connection getConnection() {
        //加载到虚拟机里
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection con = null;
        try {
            //获取数据库连接
            con = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        finally {
//            try {
//                //关闭数据库，finally是无论如何都会执行的
//                con.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
        return con;
    }

    public static void select(String name, Connection connection) throws SQLException {
        String sql = "select * from db_test.test_user where user_name = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            System.out.println(resultSet.getString(1) + "  " + resultSet.getString(2));
        }
    }

    public static void insert(String name, Connection connection) throws SQLException {
        String sql = "insert into db_test.test_user (user_name) values (?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        int resultSet = statement.executeUpdate();
    }

}

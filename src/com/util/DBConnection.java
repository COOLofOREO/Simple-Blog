package com.util;
/**
 * 建立数据库连接！！
 */

import java.sql.*;
public class DBConnection {

    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url="jdbc:mysql://localhost:3306/mydb?characterEncoding=UTF-8&useSSL=false";
    private static final String username = "root";// 数据库的用户名
    private static final String password = "123456";// 数据库的密码:这个是自己安装数据库的时候设置的，每个人不同。
    private static Connection conn = null; // 声明数据库连接对象
    static {
        try {
            Class.forName(driver);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    // 连接数据库
    public static Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            } // 连接数据库
            return conn;
        }
        return conn;
    }
    // 关闭数据库连接
    public static void close(ResultSet rs, PreparedStatement ps,Connection connection){//栈式关闭（最先连接，最后关闭连接）
        try{//关闭结果集
            if(rs!=null) rs.close();
            if(ps!=null) ps.close();
            if(connection!=null) connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}

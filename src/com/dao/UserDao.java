package com.dao;
import com.pojo.User;
import com.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class UserDao {
    Connection conn = null; // 连接对象
    PreparedStatement ps = null; // 预编译
    ResultSet rs = null; // 结果集
    /**
     * 功能 断用户名是否存在
     * 参数 userName
     * 返回值 boolean 存在返回 true，不存在返回 flase
     */
    public boolean checkUser(String userName) {
        String sql="select userId from t_user where userName=?";
        conn=DBConnection.getConnection();
        boolean bool=false;
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1,userName);
            rs= ps.executeQuery();
            if(rs.next()){
                System.out.println("用户名已存在");
                bool=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }/*finally {
            DBConnection.close(rs,ps,conn);
        }*/
        return bool;
    }
    /**
     *
     * 参数 user
     * 返回值 boolean 注册返回 true，失败返回 flase
     */
    public boolean register(User user) {
        if(checkUser(user.getUserName())) return false;
        String sql="insert into t_user (userName, passWord, phone) values (?,?,?);";
        conn=DBConnection.getConnection();
        boolean bool=false;
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2,user.getPassWord());
            ps.setString(3, user.getPhone());
            int n=ps.executeUpdate();
            if(n>=1) bool=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }

    /**
     * 功能 用户登录验证
     * 参数 user
     * 返回值 User 对象
     */
    public User login(String userName, String passWord){
        String sql="select * from t_user where userName=? and passWord=?;";
        conn=DBConnection.getConnection();
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1, userName);
            ps.setString(2,passWord);
            rs= ps.executeQuery();
            if(rs.next()){
                int userId=rs.getInt("userId");
                String username = rs.getString("userName");
                String password=rs.getString("passWord");
                String phone=rs.getString("phone");
                if(username==null||username=="") return null;
                else return new User(userId,username,password,phone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 功能 注销自己的账号
     * 参数 user
     */
    public void deleteUser(User user) {
        // 删除自己发布的评论和删除自己博客中的评论
        deleteCommentByUserId(user.getUserId());
        deleteBlogCommendByUserId(user.getUserId());
        // 删除博客
        deleteBlogByUserId(user.getUserId());
        // 删除账号
        String sql="delete from t_user where userId=?;";
        conn=DBConnection.getConnection();
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,user.getUserId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean deleteCommentByUserId(int userId){
        boolean bool=false;
        String sql="delete from t_comment where userId=?;";
        conn=DBConnection.getConnection();
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,userId);
            ps.executeUpdate();
            bool=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }

    private boolean deleteBlogCommendByUserId(int userId){
        boolean bool=false;
        String sql="delete from t_comment where blogId in (select blogId from t_blog where t_blog.userId=?);";
        conn=DBConnection.getConnection();
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,userId);
            ps.executeUpdate();
            bool=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }

    private boolean deleteBlogByUserId(int userId){
        boolean bool=false;
        String sql="delete from t_blog where userId=?;";
        conn=DBConnection.getConnection();
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,userId);
            ps.executeUpdate();
            bool=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }
}

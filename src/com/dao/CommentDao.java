package com.dao;

import com.pojo.Comment;
import com.pojo.User;
import com.util.DBConnection;

import java.sql.*;

public class CommentDao {
    static Connection conn = null; // 连接对象
    static PreparedStatement ps = null; // 预编译
    static ResultSet rs = null; // 结果集

    /**
     * 功能 添加评论
     * 参数 comment
     */
    public void addComment(Comment comment) {
        // 添加评论
        String sql="insert into t_comment (commentContent, blogId, createTime, userId) values (?,?,?,?);";
        conn=DBConnection.getConnection();
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1, comment.getCommentContent());
            ps.setInt(2,comment.getBlog().getBlogId());
            ps.setDate(3,new java.sql.Date(comment.getDate().getTime()));
            ps.setInt(4,comment.getUser().getUserId());
            // 判断是否添加成功
            int n=ps.executeUpdate();
            if(n>=1){
                System.out.println("评论发布成功！");
            }else {
                System.out.println("评论发布失败！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能 查询自己发布的评论
     * @param user
     */
    public void findMyComments(User user) {
        System.out.println("我发布的评论如下：");
        System.out.println("blogId\tblogTitle\tblogContent\tcommentContent");
        // 通过三表关联查询，查询自己发步的评论
        String sql="select t_blog.blogId,t_blog.blogTitle,t_blog.blogContent,t_comment.commentContent from t_blog,t_comment where t_comment.userId=? and t_blog.blogId=t_comment.blogId;";
        conn=DBConnection.getConnection();
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,user.getUserId());
            rs= ps.executeQuery();
            while (rs.next()){
                int blogId=rs.getInt("blogId");
                String blogTitle=rs.getString("blogTitle");
                String blogContent=rs.getString("blogContent");
                String commentContent=rs.getString("commentContent");
                // 输出查询结果
                System.out.println(blogId+"\t"+blogTitle+"\t"+blogContent+"\t"+commentContent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能 查询博客的所有评论
     * 参数 blogId
     */
    public void findCommentById(int blogId) {
        System.out.println("博客的评论如下：");
        System.out.println("blogId\tblogTitle\tblogContent\tcommentContent\tuserName");
        // 根据 ID 查询博客的所有评论
        String sql="select b.blogId,b.blogTitle,b.blogContent,c.commentContent,u.userName from t_comment c,t_blog b,t_user u where b.blogId=? and c.userId=u.userId and c.blogId=b.blogId order by c.blogId;";
        conn=DBConnection.getConnection();
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,blogId);
            rs= ps.executeQuery();
            while (rs.next()){
                String blogTitle=rs.getString("blogTitle");
                String blogContent=rs.getString("blogContent");
                String commentContent=rs.getString("commentContent");
                String userName=rs.getString("userName");
                // 输出查询结果
                System.out.println(blogId+"\t"+blogTitle+"\t"+blogContent+"\t"+commentContent+"\t"+userName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能 删除评论
     * @param commentId
     * @param userId
     * @param createUserId
     */
    public void deleteComment(int commentId,int userId,int createUserId) {
        // 删除评论
        String sql="delete from t_comment where commentId=? and (userId =? or blogId in (select blogId from t_blog where t_blog.userId=?));";
        // 判断是否删除成功
        conn=DBConnection.getConnection();
        int n=0;
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,commentId);
            ps.setInt(2,userId);
            ps.setInt(3,userId);
            n=ps.executeUpdate();
            // 判断是否删除成功
            if(n==1){
                System.out.println("评论删除成功！");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("评论删除失败！");
    }

}

package com.dao;

import com.pojo.Blog;
import com.pojo.BlogType;
import com.pojo.User;
import com.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BlogDao {
    static Connection conn = null; // 连接对象
    static PreparedStatement ps = null; // 预编译
    static ResultSet rs = null; // 结果集

    public boolean addBlog(Blog blog) {
        // 插入博客数据到博客表中
        String sql="insert into t_blog (blogTitle, blogContent, userId, typeId) values (?,?,?,?);";
        conn=DBConnection.getConnection();
        boolean bool=false;
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1, blog.getBlogTitle());
            ps.setString(2, blog.getBlogContent());
            ps.setInt(3,blog.getUser().getUserId());
            ps.setInt(4,blog.getBlogType().getTypeId());
            int n=ps.executeUpdate();
            if(n>=1) bool=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 判断是否添加成功
        return bool;
    }

    /**
     * 功能：查询所有博客
     */
    public void findAllBlogs() {
        System.out.println("blogId\tblogTitle\tblogContent\ttypeName\tuserName");
        // 通过三表关联查询，查询所有的博客
        String sql="select t_blog.blogId,t_blog.blogTitle,t_blog.blogContent,blog_type.typeName,t_user.userName from t_blog,t_user,blog_type where t_blog.typeId=blog_type.typeId and t_blog.userId=t_user.userId order by t_blog.blogId;";
        conn=DBConnection.getConnection();
        try {
            ps=conn.prepareStatement(sql);
            rs= ps.executeQuery();
            while (rs.next()){
                int blogId=rs.getInt("blogId");
                String blogTitle=rs.getString("blogTitle");
                String blogContent=rs.getString("blogContent");
                String typeName=rs.getString("typeName");
                String userName=rs.getString("userName");
                // 输出查询结果
                System.out.println(blogId+"\t"+blogTitle+"\t"+blogContent+"\t"+typeName+"\t"+userName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能 查询自己创建的博客
     * 参数 user
     */
    public void findMyBlogs(User user) {
        System.out.println("我创建的博客如下：");
        System.out.println("blogId\tblogTitle\tblogContent\ttypeName");
        // 通过三表关联查询，查询自己创建的博客
        String sql="select t_blog.blogId,t_blog.blogTitle,t_blog.blogContent,blog_type.typeName from t_blog,blog_type where t_blog.typeId=blog_type.typeId and t_blog.userId=?;";
        conn=DBConnection.getConnection();
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,user.getUserId());
            rs= ps.executeQuery();
            while (rs.next()){
                int blogId=rs.getInt("blogId");
                String blogTitle=rs.getString("blogTitle");
                String blogContent=rs.getString("blogContent");
                String typeName=rs.getString("typeName");
                // 输出查询结果
                System.out.println(blogId+"\t"+blogTitle+"\t"+blogContent+"\t"+typeName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能 根据指定博客 ID 删除自己所创建的博客
     * 参数 blogId
     * 参数 user
     */
    public void deleteBlog(int blogId, User user) {
        if(!deleteBlogComment(blogId,user.getUserId())){
            System.out.println("博客删除失败！");
            return;
        }
        // 根据用户 ID 和 博客 ID 删除对应的博客信息
        String sql="delete from t_blog where blogId=? and userId=?;";
        // 判断是否删除成功
        conn=DBConnection.getConnection();
        int n=0;
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,blogId);
            ps.setInt(2,user.getUserId());
            n=ps.executeUpdate();
            if(n>=1){
                System.out.println("博客删除成功！");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("博客删除失败！");
    }

    /**
     * 删除博客下的所有评论
     * @param blogId
     * @return
     */
    private boolean deleteBlogComment(int blogId,int userId){
        List<Blog> blogs=getBlogByUserId(userId);
        if(blogs==null||blogs.size()==0) return false;
        //删除博客下所有的评论
        String sql="delete from t_comment where blogId=?";
        conn=DBConnection.getConnection();
        boolean bool=false;
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,blogId);
            ps.executeUpdate();
            bool=true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bool;
    }

    private List<Blog> getBlogByUserId(int userId){
        List<Blog> blogs=new ArrayList<>();
        String sql="select * from t_blog where userId=?";
        conn=DBConnection.getConnection();
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs=ps.executeQuery();
            while (rs.next()){
                int blogId=rs.getInt("blogId");
                String blogTitle=rs.getString("blogTitle");
                String blogContent=rs.getString("blogContent");
                int typeId=rs.getInt("typeId");
                User user=new User(userId,null,null,null);
                BlogType blogType=new BlogType(typeId,null);
                Blog blog=new Blog(blogId,blogTitle,blogContent,user,blogType);
                blogs.add(blog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blogs;
    }

    /**
     * 功能 根据博客 ID 修改自己创建的博客
     * 参数 blog
     */
    public void updateBlog(Blog blog) {
        String sql="update t_blog set blogTitle=?,blogContent=?,typeId =(select typeId from blog_type where typeName=?) where blogId=? and userId=?;";
        // 判断是否删除成功
        conn=DBConnection.getConnection();
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1, blog.getBlogTitle());
            ps.setString(2, blog.getBlogContent());
            ps.setString(3,blog.getBlogType().getTypeName());
            ps.setInt(4,blog.getBlogId());
            ps.setInt(5,blog.getUser().getUserId());
            int n=ps.executeUpdate();
            if(n>=1){
                System.out.println("博客修改成功！");
            }else {
                System.out.println("博客修改失败！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能 根据博客 ID 查询指定博客
     * @param blogId
     */
    public void findBlogById(int blogId) {
        System.out.println("blogId\tblogTitle\tblogContent\ttypeName\tuserName");
        // 根据博客 ID 查询指定博客
        String sql="select t_blog.blogId,t_blog.blogTitle,t_blog.blogContent,blog_type.typeName,t_user.userName from t_blog,t_user,blog_type where t_blog.typeId=blog_type.typeId and t_blog.userId=t_user.userId and t_blog.blogId=?;";
        // 输出查询结果
        conn=DBConnection.getConnection();
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,blogId);
            rs= ps.executeQuery();
            while (rs.next()){
                String blogTitle=rs.getString("blogTitle");
                String blogContent=rs.getString("blogContent");
                String typeName=rs.getString("typeName");
                String userName=rs.getString("userName");
                // 输出查询结果
                System.out.println(blogId+"\t"+blogTitle+"\t"+blogContent+"\t"+typeName+"\t"+userName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能 根据博客标题模糊查询博客
     * 参数 blogTitle
     */
    public void findBlogByTitle(String blogTitle) {
        System.out.println("blogId\tblogTitle\tblogContent\ttypeName\tuserName");
        // 根据博客标题模糊查询博客
        String sql="select t_blog.blogId,t_blog.blogTitle,t_blog.blogContent,blog_type.typeName,t_user.userName from t_blog,t_user,blog_type where t_blog.typeId=blog_type.typeId and t_blog.userId=t_user.userId and t_blog.blogTitle like \"%\"?\"%\";";
        conn=DBConnection.getConnection();
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1,blogTitle);
            rs= ps.executeQuery();
            while (rs.next()){
                int blogId=rs.getInt("blogId");
                String trueblogTitle=rs.getString("blogTitle");
                String blogContent=rs.getString("blogContent");
                String typeName=rs.getString("typeName");
                String userName=rs.getString("userName");
                // 输出查询结果
                System.out.println(blogId+"\t"+trueblogTitle+"\t"+blogContent+"\t"+typeName+"\t"+userName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkBlogById(int blogId) {
        boolean result = false;
        // 根据博客 ID 查询指定博客
        try {
            conn = DBConnection.getConnection();
            String sql = "select t.blogTitle blogTitle,t.blogContent blogContent,t.typeName,t_user.userName userName from(select blog.*,blog_type.typeName from (select * from t_blog where blogId = ?)blog left join blog_type on blog.typeId = blog_type.typeId) t left join t_user on t.userId = t_user.userId;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,blogId);
            rs = ps.executeQuery();
            // 输出查询结果
            if (rs.next()){
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 功能 根据博客 ID 查询创建者 ID
     * 参数 blogId
     * 返回值 创建者 ID
     */
    public int findUserById(int blogId) {
        // 根据博客 ID 查询创建者 ID
        String sql="select userId from t_blog where blogId=?;";
        conn=DBConnection.getConnection();
        int userId=0;
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,blogId);
            rs= ps.executeQuery();
            if (rs.next()){
                userId=rs.getInt("userId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 输出查询结果
        return userId;
    }
}

package com.pojo;

public class Blog {
    private int blogId; // 博客 id
    private String blogTitle; // 博客标题
    private String blogContent; // 博客内容
    private User user; // 用户对象
    private BlogType blogType; // 博客类型对象

    // 构造方法
    public Blog() {
    }

    public Blog(int blogId, String blogTitle, String blogContent, User user, BlogType blogType) {
        this.blogId = blogId;
        this.blogTitle = blogTitle;
        this.blogContent = blogContent;
        this.user = user;
        this.blogType = blogType;
    }

    // 获取和设置博客属性
    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogContent() {
        return blogContent;
    }

    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BlogType getBlogType() {
        return blogType;
    }

    public void setBlogType(BlogType blogType) {
        this.blogType = blogType;
    }

}

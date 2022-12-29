package com.pojo;

import java.util.Date;

public class Comment {
    private int commentId; // 评论 ID
    private String commentContent; // 评论内容
    private Blog blog; // 博客对象
    private User user; // 用户对象
    private Date date; // 评论时间
    // 无参构造方法
    public Comment() {
    }

    public Comment(int commentId, String commentContent, Blog blog, User user, Date date) {
        this.commentId = commentId;
        this.commentContent = commentContent;
        this.blog = blog;
        this.user = user;
        this.date = date;
    }

    //设置和获取评论属性值
    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

package com.pojo;

public class User {

    private int userId; // 用户 id 自增
    private String userName; // 用户名称
    private String passWord; // 用户密码
    private String phone; // 用户手机号

    // User 构造方法
    public User() {
    }

    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public User(int userId, String userName, String passWord, String phone) {
        this.userId = userId;
        this.userName = userName;
        this.passWord = passWord;
        this.phone = phone;
    }
    // 获取和设置 User 属性的方法
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

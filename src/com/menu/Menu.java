package com.menu;
import com.dao.BlogDao;
import com.dao.BlogTypeDao;
import com.dao.CommentDao;
import com.dao.UserDao;
import com.pojo.Blog;
import com.pojo.BlogType;
import com.pojo.Comment;
import com.pojo.User;

import java.util.Date;
import java.util.Scanner;

/**
* Author: Zhang Ao
*/
public class Menu {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        boolean flag = true;
        User user;
        Blog blog;
        Comment comment;
        BlogType blogType;
        BlogDao blogDao = new BlogDao();
        CommentDao commentDao = new CommentDao();
        BlogTypeDao blogTypeDao = new BlogTypeDao();
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("**********欢迎进入博客系统**********");
            System.out.println("**********1. 登录**********");
            System.out.println("**********2. 注册**********");
            System.out.println("**********3. 退出系统**********");
            System.out.println("请输入你要进行的操作：");
            int x = sc.nextInt();
            String userName;
            String passWord;
            String phone;
            switch (x) {
                //登录
                case 1:
                    // 获取键盘输入的用户信息
                    System.out.println("请输入你的用户名");
                    userName = sc.next();
                    System.out.println("请输入你的密码");
                    passWord = sc.next();
                    user = userDao.login(userName,passWord);
                    if (user != null) {
                        //登录成功
                        boolean flag1 = true;
                        do{
                            System.out.println("1. 创建博客");
                            System.out.println("2. 查看博客");
                            System.out.println("3. 删除博客");
                            System.out.println("4. 修改博客");
                            System.out.println("5. 返回上一级菜单");
                            System.out.println("6. 查询自己的评论");
                            System.out.println("7. 注销账号");
                            System.out.println("请输入你要进行的操作：");
                            int y = sc.nextInt();
                            int blogId = 0;
                            String blogTitle;
                            String blogContent;
                            String typeName;
                            switch (y){
                                //创建博客
                                case 1:
                                    // 获取键盘输入的用户信息
                                    System.out.println("请输入你要创建的博客标题");
                                    blogTitle = sc.next();
                                    System.out.println("请输入你要创建的博客内容");
                                    blogContent = sc.next();
                                    System.out.println("请输入你的博客类型");
                                    typeName = sc.next();
                                    // 查询博客类型是否存在
                                    blogType = blogTypeDao.findBlogType(typeName);
                                    if (blogType != null){
                                        // 将博客信息放入博客对象中
                                        blog = new Blog();
                                        blog.setBlogTitle(blogTitle);
                                        blog.setBlogContent(blogContent);
                                        blog.setUser(user);
                                        blog.setBlogType(blogType);
                                        // 验证博客是否创建成功
                                        boolean b = blogDao.addBlog(blog);
                                        if (b){
                                            System.out.println("博客创建成功！");
                                        }else{
                                            System.out.println("博客创建失败！");
                                        }
                                    }else {
                                        System.out.println("博客类型不存在！");
                                    }
                                    break;
                                //查询博客
                                case 2:
                                    System.out.println("查询所有博客请按1，查询自己的博客请按2，查询指定博客请按3，返回请按其它键");
                                    String find = sc.next();
                                    if ("1".equals(find)){
                                        // 查询所有的博客
                                        blogDao.findAllBlogs();
                                    }else if ("2".equals(find)){
                                        // 查询自己的博客
                                        blogDao.findMyBlogs(user);
                                    }else if ("3".equals(find)){
                                        //查询指定博客
                                        // 获取键盘输入的博客 ID
                                        System.out.println("请输入你要查询的博客ID");
                                        blogId = sc.nextInt();
                                        // 查询博客
                                        blogDao.findBlogById(blogId);
                                    }else if ("4".equals(find)){
                                        //根据标题模糊查询
                                        // 获取键盘输入的博客标题
                                        System.out.println("请输入你要查询的博客标题");
                                        blogTitle = sc.next();
                                        // 查询博客
                                        blogDao.findBlogByTitle(blogTitle);
                                    }else {
                                        break;
                                    }
                                    // 获取用户是否想要发布评论
                                    System.out.println("发布评论请按1，查询所有评论请按2，请按其它键");
                                    String c = sc.next();
                                    //发布评论
                                    if ("1".equals(c)){
                                        //发布评论
                                        // 获取用户评论的博客
                                        if (blogId == 0){
                                            System.out.println("请输入你要评论的博客ID");
                                            blogId = sc.nextInt();
                                        }
                                        // 查询博客是否存在
                                        if (blogDao.checkBlogById(blogId)){
                                            // 获取用户评论的内容
                                            System.out.println("请输入你要评论的内容");
                                            String commentContent  = sc.next();
                                            Date date = new Date();
                                            blog = new Blog();
                                            blog.setBlogId(blogId);
                                            comment = new Comment();
                                            comment.setCommentContent(commentContent);
                                            comment.setBlog(blog);
                                            comment.setUser(user);
                                            comment.setDate(date);
                                            // 将评论内容添加
                                            commentDao.addComment(comment);
                                            // 评论添加成功后进入查询该博客所有评论模块
                                            commentDao.findCommentById(blogId);
                                        }else {
                                            System.out.println("博客不存在");
                                        }
                                    }
                                    //查询所有评论
                                    if ("2".equals(c)){
                                        // 判读博客 ID 是否为 0
                                        if (blogId == 0){
                                            System.out.println("请输入你要评论的博客ID");
                                            blogId = sc.nextInt();
                                        }
                                        // 查询该博客所有评论
                                        commentDao.findCommentById(blogId);

                                        System.out.println("是否删除评论：是请按1，否请按其它键");
                                        String s = sc.next();
                                        //删除评论
                                        if ("1".equals(s)){
                                            // 获取用户要删除的评论
                                            System.out.println("请输入你要删除的评论ID");
                                            int commentId=sc.nextInt();
                                            // 根据博客 ID 获取博客的创建者 ID
                                            int creatorId=blogDao.findUserById(blogId);
                                            // 删除评论
                                            commentDao.deleteComment(commentId,user.getUserId(),creatorId);
                                        }
                                    }
                                    break;
                                //删除博客
                                case 3:
                                    System.out.println("请输入你要删除的博客ID");
                                    blogId = sc.nextInt();
                                    // 删除博客
                                    blogDao.deleteBlog(blogId, user);
                                    break;
                                //修改博客
                                case 4:
                                    // 获取用户键盘输入信息
                                    System.out.println("请输入你要修改的博客ID");
                                    blogId = sc.nextInt();
                                    System.out.println("请输入修改后的博客标题");
                                    blogTitle = sc.next();
                                    System.out.println("请输入修改后的博客内容");
                                    blogContent = sc.next();
                                    System.out.println("请输入修改后的博客类型");
                                    typeName = sc.next();
                                    // 查询博客类型是否存在
                                    blogType = blogTypeDao.findBlogType(typeName);
                                    if (blogType != null){
                                        // 将博客信息存入博客对象中
                                        blog = new Blog();
                                        blog.setBlogId(blogId);
                                        blog.setBlogTitle(blogTitle);
                                        blog.setBlogContent(blogContent);
                                        blog.setUser(user);
                                        blog.setBlogType(blogType);
                                        // 修改博客
                                        blogDao.updateBlog(blog);
                                    }else {
                                        System.out.println("博客类型不存在！");
                                    }
                                    break;
                                //返回上一级
                                case 5:
                                    flag1 = false;
                                    break;
                                //查询自己的评论
                                case 6:
                                    // 调用 findMyComments 方法查询自己发布的评论
                                    commentDao.findMyComments(user);
                                    break;
                                //注销账号
                                case 7:
                                    // 删除用户
                                    userDao.deleteUser(user);
                                    // 退出登录界面，返回主菜单
                                    flag1=false;
                                    break;
                                default:
                                    System.out.println("你的输入有误，请重新输入！");
                                    break;
                            }
                        }while (flag1);
                    }else{
                        System.out.println("用户名或密码不正确！");
                    }
                    break;
                //注册用户
                case 2:
                    user = new User();
                    // 获取键盘输入的用户信息
                    System.out.println("请输入你要注册的用户名");
                    userName = sc.next();
                    System.out.println("请输入你要注册的密码");
                    passWord = sc.next();
                    System.out.println("请输入你要注册的手机号");
                    phone = sc.next();
                    // 将用户信息存入用户对象中
                    user.setUserName(userName);
                    user.setPassWord(passWord);
                    user.setPhone(phone);
                    // 调用 register 方法，将用户信息传入其中，判断注册是否成功
                    boolean result = userDao.register(user);
                    if (result) {
                        System.out.println("注册成功！");
                    }else{
                        System.out.println("注册失败！");
                    }
                    break;
                //退出
                case 3:
                    // 设置状态为 false，退出系统
                    flag = false;
                    System.out.println("退出系统！");
                    break;
                default:
                    System.out.println("你的输入有误，请重新输入！");
                    break;
            }
        } while (flag);
    }
}

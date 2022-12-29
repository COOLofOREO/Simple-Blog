drop database if EXISTS mydb;
create database if not EXISTS `mydb`;
use mydb;
CREATE TABLE `t_user` (
  `userId` int NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `userName` varchar(32) NOT NULL COMMENT '用户名',
  `passWord` varchar(32) NOT NULL COMMENT '用户密码',
  `phone` varchar(11) NOT NULL COMMENT '手机号码',
  PRIMARY KEY (`userId`)
);
INSERT INTO `t_user` VALUES (1, 'sunfeng', '123456789', '17485236945');
INSERT INTO `t_user` VALUES (2, 'yechen', '147258369', '14758962358');
CREATE TABLE `blog_type` (
  `typeId` int NOT NULL AUTO_INCREMENT COMMENT '类型ID',
  `typeName` varchar(32) NOT NULL COMMENT '类型名称',
  PRIMARY KEY (`typeId`)
);
INSERT INTO `blog_type` VALUES (1, 'Java');
INSERT INTO `blog_type` VALUES (2, 'Python');
INSERT INTO `blog_type` VALUES (3, 'Chinese');
INSERT INTO `blog_type` VALUES (4, 'Math');
INSERT INTO `blog_type` VALUES (5, 'C');
INSERT INTO `blog_type` VALUES (6, 'English');
CREATE TABLE `t_blog` (
  `blogId` int NOT NULL AUTO_INCREMENT COMMENT '博客ID',
  `blogTitle` varchar(100) NOT NULL COMMENT '博客标题',
  `blogContent` longtext NOT NULL COMMENT '博客内容',
  `userId` int DEFAULT NULL COMMENT '创建人ID',
  `typeId` int DEFAULT NULL COMMENT '类型ID',
  PRIMARY KEY (`blogId`),
  KEY `FK_type_id` (`typeId`),
  KEY `FK_user_id` (`userId`),
  CONSTRAINT `FK_type_id` FOREIGN KEY (`typeId`) REFERENCES `blog_type` (`typeId`),
  CONSTRAINT `FK_user_id` FOREIGN KEY (`userId`) REFERENCES `t_user` (`userId`)
);
INSERT INTO `t_blog` VALUES (1, 'C简介', 'C语言是一门面向过程的、抽象化的通用程序设计语言，广泛应用于底层开发。', 1, 5);
INSERT INTO `t_blog` VALUES (2, 'Java简介', 'Java是一门面向对象编程语言，不仅吸收了C++语言的各种优点，还摒弃了C++里难以理解的多继承、指针等概念，因此Java语言具有功能强大和简单易用两个特征。', 2, 1);
INSERT INTO `t_blog` VALUES (3, 'Java基础语法', '一个 Java 程序可以认为是一系列对象的集合，而这些对象通过调用彼此的方法来协同工作。', 1, 1);
INSERT INTO `t_blog` VALUES (4, 'Python简介', 'Python是一种跨平台的计算机程序设计语言。', 2, 2);
INSERT INTO `t_blog` VALUES (5, '简介', '程序设计语言。', 1, 2);

CREATE TABLE `t_comment` (
  `commentId` int NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `commentContent` varchar(500) NOT NULL COMMENT '评论内容',
  `blogId` int NOT NULL COMMENT '博客ID',
  `createTime` datetime NOT NULL COMMENT '评论时间',
  `userId` int NOT NULL COMMENT '评论人ID',
  PRIMARY KEY (`commentId`),
  KEY `FK_comment_blog_id` (`blogId`),
  KEY `FK_comment_user_id` (`userId`),
  CONSTRAINT `FK_comment_user_id` FOREIGN KEY (`userId`) REFERENCES `t_user` (`userId`),
  CONSTRAINT `FK_comment_blog_id` FOREIGN KEY (`blogId`) REFERENCES `t_blog` (`blogId`)
);

INSERT INTO `t_comment` VALUES (1, 'Java好难啊', 2, '2020-03-20 19:27:12', 1);
INSERT INTO `t_comment` VALUES (2, 'Java啊啊啊', 2, '2020-02-12 16:57:52', 2);
INSERT INTO `t_comment` VALUES (3, 'Python好难啊', 4, '2020-03-17 18:54:13', 2);
INSERT INTO `t_comment` VALUES (4, 'Java不简单', 3, '2020-03-12 17:35:56', 2);
INSERT INTO `t_comment` VALUES (5, 'aaaaa', 5, '2020-03-12 17:35:56', 2);
INSERT INTO `t_comment` VALUES (6, 'aaaaa', 5, '2020-03-12 17:35:56', 1);
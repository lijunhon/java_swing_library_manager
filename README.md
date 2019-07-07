# java_swing_library_manager
图管管理系统-粗糙完成-后续修改
## 如何使用
### 创建数据库
#### 1.创建数据库
create database class
#### 2.创建表
create table PI_Sheet(id nchar(6) primary key not null,ac_name nvarchar(12) not null ,passwd nvarchar(20) not null,name nvarchar(12) not null,role nvarchar(6) not null,bd_number tinyint not null,sex nchar(2) not null,major nvarchar(12) not null,tel nvarchar(11) not null,vi_number tinyint not null,p_remarks nvarchar(200))
create table BCI_Sheet(title nvarchar(50) not null primary key,author nvarchar(40) not null,pub_house nvarchar(50) not null,pub_date smalldatetime not null,category nvarchar(16) not null,total bigint not null,stock bigint not null,introduction nvarchar(100) not null,b_remarks nvarchar(200))
create table BI_Sheet(book_id nchar(6) primary key not null,title nvarchar(30) not null)
create table BR_Sheet(book_id nchar(6) foreign key references BI_Sheet(book_id) not null,id nchar(6) foreign key references PI_Sheet(id) not null,b_date smalldatetime not null,r_date smalldatetime,overdue bit not null,constraint XH primary key(book_id,id))

### 编译/加载
#### 编译
javac login.java
#### 打开
java login

## 一些截图

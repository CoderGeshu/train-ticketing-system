# train-ticketing-system
这是一个 Java GUI 系统设计：使用 Java Swing 编写的铁路售票应用系统。

## 技术栈

所使用到的技术：Java Swing + Java AWT + MySQL 5.5.62

***

## 运行截图

**登录界面**

![STS登录界面](https://gitee.com/CoderGeshu/pic-go-images/raw/master/img/image-20210123160236965.png)

**注册用户**

![注册用户](https://gitee.com/CoderGeshu/pic-go-images/raw/master/img/image-20210123155941155.png)

默认的注册是乘客身份，必须要填写完整信息并确定阅读相关服务条款后才能进行注册，否则会有错误提示。

***

**普通乘客端——乘客身份登录系统**

![STS用户主页面](https://gitee.com/CoderGeshu/pic-go-images/raw/master/img/image-20210123160333694.png)

在车票业务中可以执行购票、订单信息、切换用户及退出功能。

例如，搜索上海至北京的 2020 年 1 月 22 日的车票（这里的日期没有使用日历控件，先手动输入）：

![STS查询上海到北京的车票](https://gitee.com/CoderGeshu/pic-go-images/raw/master/img/image-20210123160622240.png)

车次默认查询结果如下：

![STS上海到北京车次结果](https://gitee.com/CoderGeshu/pic-go-images/raw/master/img/image-20210123160701307.png)

可以选择指定车次并选择其座位类型进行购票：

![STS购票](https://gitee.com/CoderGeshu/pic-go-images/raw/master/img/image-20210123160759328.png)

购票成功后，可以返回主页面的 ” 订单 “ 信息栏中看到个人的订单信息：

![STS订单信息](https://gitee.com/CoderGeshu/pic-go-images/raw/master/img/image-20210123160909206.png)

在订单信息中可以进行**改签**和**退票**操作，这里就不在演示。

在查询车票时，还可以**支持分类查询**，比如：只看勾选**高铁/动车**选项搜索上海到北京的车次：

![STS搜索上海到北京的高铁和动车](https://gitee.com/CoderGeshu/pic-go-images/raw/master/img/image-20210123161108612.png)

这样就可以进行筛选显示了。（学生票功能还没有完善）

除了车票业务，用户还可以修改个人信息以及管理常用联系人的信息：

![STS联系人](https://gitee.com/CoderGeshu/pic-go-images/raw/master/img/image-20210123161345020.png)

***

**管理员端——管理员身份登录系统**

以管理员账号登录系统的后台管理：可以执行**线路管理**、**价格管理**、**用户信息管理**。

线路管理界面如下，可执行车次信息的增加、录入、修改与删除。

![STS管理员管理线路](https://gitee.com/CoderGeshu/pic-go-images/raw/master/img/image-20210123161823538.png)

价格管理可以对不同类型车次以及不同类型的座位价格进行修改管理：

![STS车次座位价格管理](https://gitee.com/CoderGeshu/pic-go-images/raw/master/img/image-20210123162053709.png)

用户信息管理可以对用户信息进行修改以及类型的修改（当然这里设计的不太合理，把所有的用户信息都显示出来了，应该只显示一些非敏感信息比较合理一点，大家可以进行修改）：

![STS用户管理](https://gitee.com/CoderGeshu/pic-go-images/raw/master/img/image-20210123162305701.png)

***

大体上的功能截图就先这么多，大家自行去探索吧。

## 如何运行

1. 把项目克隆到本地：`git clone https://github.com/CoderGeshu/train-ticketing-system.git`
2. 在 MySQL 中创建数据库 `train`，然后使用 `sql` 目录下的表创建语句进行创建表。
3. 在本地 IDE 中打开项目，添加项目依赖的 jar 包：`mysql-connector-java-8.0.15.jar`，此 jar 包在本项目的 `libs` 文件夹下。
4. 运行 `AppStarter.java` 类，即可出现登录页面，然后使用数据库中的用户信息进行登录，也可进行注册新乘客信息后登录。管理员登录：`默认账号：120，密码：123456`。
5. 详见源码和数据库表结构设计。
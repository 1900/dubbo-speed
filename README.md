# Dubbo Speed

#### 项目介绍
基于springboot、mybatisplus、shiro、dubbo、zookeeper、log4j、mysql、redis。

#### 软件架构

- 核心框架：SpringBoot
- 安全框架：Shiro,JWT
- 缓存框架：Redis
- 分布式框架：Dubbo（注册中心：Zookeeper）
- 持久层框架：MyBatisPlus
- 数据库连接池：Druid
- 日志管理：Log4j

#### 系统功能

#### 使用说明

1. 运行redis、zookeeper
2. 运行服务提供方ServiceApplication，再运行服务消费方Application
3. 导入import.sql到mysql数据库
4. 访问http://127.0.0.1:5555/

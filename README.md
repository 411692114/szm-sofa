# SZM-SOFA

### 一、简介

Szm-Sofa 是开源的基于 Spring Boot 的研发框架，它在 Spring Boot 的基础上，提供了诸如 统一数据响应机制、异常拦截捕获、Swagger文档、分布式文件服务、mybatisPlus集成、Redis缓存工具、基础订单服务、分布式任务等功能和业务能力。在增强了 Spring Boot 的同时，Szm-Sofa 提供了让用户可以在 Spring Boot 中非常方便地使用 SOFA 中间件的能力。

### 二、应用

#### 2.1.服务列表

| 清单名称 | 类型   |
|:-------|:---|
| szm-sofa       | pom   |
| [szm-sofa-boot-starter](szm-sofa-boot-starter)       | jar   |
| [szm-sofa-boot-starter-dfs](szm-sofa-boot-starter-dfs)       | jar   |
| [szm-sofa-boot-starter-jedis](szm-sofa-boot-starter-jedis)       | jar   |
| [szm-sofa-boot-starter-order](szm-sofa-boot-starter-order)       | jar   |
| [szm-sofa-boot-starter-orm](szm-sofa-boot-starter-orm)       | jar   |

#### 2.2.当前版本

```
com.sinszm.sofa:szm-sofa:pom:1.0.7

com.sinszm.sofa:szm-sofa-boot-starter:jar:1.0.7

com.sinszm.sofa:szm-sofa-boot-starter-dfs:jar:1.0.7

com.sinszm.sofa:szm-sofa-boot-starter-jedis:jar:1.0.7

com.sinszm.sofa:szm-sofa-boot-starter-order:jar:1.0.7

com.sinszm.sofa:szm-sofa-boot-starter-orm:jar:1.0.7
```

### 三、使用说明（以maven方式列举）

#### 3.1.引用方式

在maven的依赖管理器中引入pom依赖，示例如下：

```xml
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>com.sinszm.sofa</groupId>
                <artifactId>szm-sofa</artifactId>
                <version>1.0.7</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```

#### 3.2.公共组件（szm-sofa-boot-starter）使用说明

依赖引入：
```xml
    <dependencies>
        <dependency>
            <groupId>com.sinszm.sofa</groupId>
            <artifactId>szm-sofa-boot-starter</artifactId>
        </dependency>
    </dependencies>
```

配置推荐（默认yml格式，其他格式自行处理）：

```yaml
logging:
  path: ./logs
  level:
    xxx:
      xxx:
        xxx: debug  #xxx表示你的项目包名
  file:
    path: ${logging.path}


#开启接口文档（文档开关，接口文档访问地址：http://localhost:8080/doc.html）
swagger:
  enable: true


#应用名称必须要，其他配置仅供参考
spring:
  application:
    name: MS-NSP-V2
  servlet:
    multipart:
      enabled: true
      max-file-size: 1GB
      max-request-size: 1GB
      location: ${user.dir}/tmp
      resolve-lazily: true
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8
    default-property-inclusion: always
```

推荐异常外抛类：`ApiException`

推荐统一结果响应工具：`ResultUtil`

更多工具与使用方式请查看源码及示例代码（后续会补上）。

#### 3.3.文件服务（szm-sofa-boot-starter-dfs）使用说明

依赖引入：
```xml
    <dependencies>
        <dependency>
            <groupId>com.sinszm.sofa</groupId>
            <artifactId>szm-sofa-boot-starter-dfs</artifactId>
        </dependency>
    </dependencies>
```

配置推荐（默认yml格式，其他格式自行处理）：

```yaml
dfs:
  type: minio
  minio:
    endpoint: http://192.168.8.254
    port: 15002
    access-key: 0AKNN7EXAMPLE
    secret-key: 67b8347eed0cc93a83c6b7d5a0cce273
    bucket: demo

```
更多配置请参考配置类

[DfsProperties.java](szm-sofa-boot-starter-dfs/src/main/java/com/sinszm/sofa/DfsProperties.java)  
[CosProperties.java](szm-sofa-boot-starter-dfs/src/main/java/com/sinszm/sofa/CosProperties.java)  
[MinIoProperties.java](szm-sofa-boot-starter-dfs/src/main/java/com/sinszm/sofa/MinIoProperties.java)  
[OssProperties.java](szm-sofa-boot-starter-dfs/src/main/java/com/sinszm/sofa/OssProperties.java)

推荐使用注入`DfsService`接口来实现文件的上传与下载，更多工具可根据实际上传的文件服务器SDK提供情况来调用。

#### 3.4.缓存服务（szm-sofa-boot-starter-jedis）使用说明

依赖引入：
```xml
    <dependencies>
        <dependency>
            <groupId>com.sinszm.sofa</groupId>
            <artifactId>szm-sofa-boot-starter-jedis</artifactId>
        </dependency>
    </dependencies>
```

配置推荐（默认yml格式，其他格式自行处理）：

```yaml
jedis:
  model: standalone
  address: 192.168.8.196:6379
  database: 14

```
更多配置请参考配置类

[JedisProperties.java](szm-sofa-boot-starter-jedis/src/main/java/com/sinszm/sofa/JedisProperties.java)

推荐使用工具`JedisUtil`来操作缓存。

#### 3.5.基础订单服务（szm-sofa-boot-starter-order）使用说明

该服务主要用于实现业务订单的基础流程功能，具备内置数据库和指定数据源数据库的表结构还原和数据存储等。

依赖引入：
```xml
    <dependencies>
        <dependency>
            <groupId>com.sinszm.sofa</groupId>
            <artifactId>szm-sofa-boot-starter-order</artifactId>
        </dependency>
    </dependencies>
```

配置推荐（默认yml格式，其他格式自行处理）：

```yaml
order:
  datasource: dataSource
  hibernateDialect: org.hibernate.dialect.MySQL57Dialect

```
更多配置请参考配置类

[SzmOrderProperties.java](szm-sofa-boot-starter-order/src/main/java/com/sinszm/sofa/SzmOrderProperties.java)

推荐使用注入接口`OrderApplicationService`来处理订单业务及流程。

#### 3.6.数据操作ORM组件（szm-sofa-boot-starter-orm）使用说明

该服务组件主要作用与集成MybatisPlus功能，以及定义了一部分自定义工具方法，主要解决了对新手程序员集成MybatisPlus的复杂性难度。

依赖引入：
```xml
    <dependencies>
        <dependency>
            <groupId>com.sinszm.sofa</groupId>
            <artifactId>szm-sofa-boot-starter-orm</artifactId>
        </dependency>
    </dependencies>
```

配置推荐（默认yml格式，其他格式自行处理）：

```yaml
spring:
  datasource:
    platform: mysql
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://192.168.2.193:3306/dev?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2b8&useSSL=false&allowMultiQueries=true
    username: root
    password: root

orm:
  base-package: com.demo.szm.mapper
  
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl

```
更多配置请参考MybatisPlus官网参数和SpringData的配置参数。

### 四、联系方式

电子邮箱：`chenjianbo@sinszm.com`

### 五、参与贡献

> Fork 本仓库
>
> 新建 Feat_xxx 分支
>
> 提交代码
>
> 新建 Pull Request

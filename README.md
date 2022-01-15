# 图书管理系统
开发环境为Java17

## 项目结构
Springboot + H2 Database

## 如何使用

### 启动服务

- Linux/MacOS：项目目录下执行`./mvnw spring-boot:run`
- Windows：项目目录下执行`./mvnw.cmd spring-boot:run`

由于使用了 flyway migration, 不需要执行数据库脚本

### swagger使用

swagger url为：`http://localhost:11211/dev/swagger-ui/index.html`

默认账户为：`admin`
默认密码：`123456`

使用 JWT 作为登录凭证，在swagger中调用登录接口之后，复制返回数据中`data`项的数据，点击页面头部的`Authorize`按钮，把token放进去就可以了。

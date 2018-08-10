# 核心模块
    主要用于配置代理

## 使用方式
#### 基本配置
```java
SSHProxyConfig config = new SSHProxyConfig();
config.setHost("192.168.1.5");//代理服务器
config.setPort(22);//代理服务器端口
config.setUsername("username");//登录到代理服务器的用户名
config.setAuthType(SSHProxyConfig.AUTH_PRK);//设置验证类型, 可选AUTH_PWD(密码验证)/AUTH_PRK(私钥验证)
config.setsetTimeout(1000);//连接超时时间
config.setKeepAliveInterval(5 * 1000);//保持连接
```

#### 密码验证(SSHProxyConfig.AUTH_PWD)
```java
config.setPassword("password");//设置密码
```
#### 私钥验证(SSHProxyConfig.AUTH_PRK)
```java
config.setPrivateKeyPath("path");
```
##### 本地磁盘路径: _path=/home/username/.ssh/id_rsa_
##### 在项目Resource下: _path=classpath: id_rsa_






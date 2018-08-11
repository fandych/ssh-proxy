# 核心模块
    主要用于配置代理

## 使用方式
### 基本配置
```java
SSHProxyConfig config = new SSHProxyConfig();
config.setHost("192.168.1.5");//代理服务器
config.setPort(22);//代理服务器端口
config.setUsername("username");//登录到代理服务器的用户名
config.setAuthType(SSHProxyConfig.AUTH_PRK);//设置验证类型, 可选AUTH_PWD(密码验证)/AUTH_PRK(私钥验证)
config.setsetTimeout(1000);//连接超时时间
config.setKeepAliveInterval(5 * 1000);//保持连接
```
### 登录验证方式(二选一)
#### 1. 密码验证(SSHProxyConfig.AUTH_PWD)
```java
config.setPassword("password");//设置密码
```
#### 2. 私钥验证(SSHProxyConfig.AUTH_PRK)
```java
config.setPrivateKeyPath("path");
```
###### 本地磁盘路径: _path=/home/username/.ssh/id_rsa_
###### 在项目Resource下: _path=classpath: id_rsa_

#### 目标服务器配置
```java
HostProxyInfo hostProxyInfo = new HostProxyInfo();
hostProxyInfo.setRemoteHost("www.github.com");//目标主机
hostProxyInfo.setRemoteOprt(3306)//目标端口
hostProxyInfo.setLocalhost("localhost");//绑定本机的地址, 默认0.0.0.0
hostProxyInfo.setLocalPort(3306);//绑定本机端口; 设置为0是系统自动分配一个可用的端口
```

### 组装
```java
Set<HostProxyInfo> set...
set.add(hostProxyInfo)
config.setHostProxyInfos(set);
SshProxyManager.getSSHProxyManager(Arrays.asList(config));
```

### 关闭
```java
SshProxyManager.getSSHProxyManager().shutdown();
```
本机地址 192.168.1.2, 没有网络访问权限, 只能访问局域网内的192.168.1.5

192.168.1.5 可以访问外网 www.github.com

通过上面的代码设置便可以访问localhost:3306来访问www.github.com:3306






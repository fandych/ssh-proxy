package vip.iyatou.ssh.proxy;

import java.util.Map;

/**
 * @author Fandy
 * @project ssh-proxy
 * @description:
 * @create 2018-08-10 15:34
 **/
public class SSHProxyConfig {

    public static final String AUTH_PWD = "PWD";
    public static final String AUTH_PRK = "PRK";

    private String host;
    private Integer port = 22;
    private String username;
    private String authType = AUTH_PWD;
    private String password;
    private String privateKeyPath;
    private Integer timeout = 1000;
    private int keepAliveInterval = 25;
    private Map<String, Integer> proxyHosts;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public int getKeepAliveInterval() {
        return keepAliveInterval;
    }

    public void setKeepAliveInterval(int keepAliveInterval) {
        this.keepAliveInterval = keepAliveInterval;
    }

    public Map<String, Integer> getProxyHosts() {
        return proxyHosts;
    }

    public void setProxyHosts(Map<String, Integer> proxyHosts) {
        this.proxyHosts = proxyHosts;
    }

    public boolean isPrivateKeyAuth() {
        return AUTH_PRK.equals(getAuthType());
    }
}

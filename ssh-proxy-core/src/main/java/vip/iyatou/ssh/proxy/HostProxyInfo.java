package vip.iyatou.ssh.proxy;

/**
 * @author Fandy
 * @project ssh-proxy
 * @description:
 * @create 2018-08-10 17:48
 **/
public class HostProxyInfo {

    private static final int DEFAULT_PORT = 0;
    private static final String DEFAULT_HOST = "0.0.0.0";

    private String remoteHost;
    private int remotePort;
    private String localHost = DEFAULT_HOST;
    private int localPort = DEFAULT_PORT;

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public int getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(int remotePort) {
        this.remotePort = remotePort;
    }

    public String getLocalHost() {
        return localHost;
    }

    public void setLocalHost(String localHost) {
        this.localHost = localHost;
    }

    public int getLocalPort() {
        return localPort;
    }

    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }

    public boolean useRandomPort() {
        return DEFAULT_PORT == getLocalPort();
    }
}

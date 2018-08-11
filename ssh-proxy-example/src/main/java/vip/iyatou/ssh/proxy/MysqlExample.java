package vip.iyatou.ssh.proxy;

import com.jcraft.jsch.JSchException;

import java.sql.*;
import java.util.*;

/**
 * @author Fandy
 * @project ssh-proxy
 * @description:
 * @create 2018-08-11 08:47
 **/
public class MysqlExample {

    public static void main(String[] args) throws JSchException, ClassNotFoundException, SQLException {
        setProxy();

        connect();

        shutdown();
    }

    static void setProxy() throws JSchException {
        HostProxyInfo hostProxyInfo = new HostProxyInfo();

        hostProxyInfo.setRemoteHost("iyatou.vip");
        hostProxyInfo.setRemotePort(3306);
        hostProxyInfo.setLocalPort(3309);

        SshProxyConfig sshProxyConfig = new SshProxyConfig();
        sshProxyConfig.setAuthType(SshProxyConfig.AUTH_PRK);
        sshProxyConfig.setHost("iyatou.vip");
        sshProxyConfig.setUsername("fandy");
        sshProxyConfig.setKeepAliveInterval(1000);
        sshProxyConfig.setTimeout(2 * 1000);
        sshProxyConfig.setPrivateKeyPath("C:\\Users\\chqiang\\.ssh\\id_rsa");

        Set<HostProxyInfo> hostProxyInfos = new HashSet<>();
        hostProxyInfos.add(hostProxyInfo);
        sshProxyConfig.setHostProxyInfos(hostProxyInfos);

        List<SshProxyConfig> sshProxyConfigs = Arrays.asList(sshProxyConfig);
        SshProxyManager.getSSHProxyManager(sshProxyConfigs);
    }

    static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3309", "ebuyuser", "pwd!ebuy3");

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("show databases;");

        while(resultSet.next()){
            System.out.println(resultSet.getString(1));
        }

        connection.close();
    }


    static void shutdown() {
        SshProxyManager.getSSHProxyManager().shutdown();
    }


}

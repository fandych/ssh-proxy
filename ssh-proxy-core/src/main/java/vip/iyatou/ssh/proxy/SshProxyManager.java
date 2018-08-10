package vip.iyatou.ssh.proxy;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * @author Fandy
 **/
@SuppressWarnings("unused")
public class SshProxyManager {

    private static final String CLASSPATH = "classpath:";
    private static final String NONE = "";
    private static final String PASSPHRASE = "passphrase";
    private static final String PREFERRED_AUTHENTICATIONS_NAME = "PreferredAuthentications";
    private static final String PREFERRED_AUTHENTICATIONS_VALUE = "publickey,keyboard-interactive,password";
    private static final String STRICT_HOST_KEY_CHECKING_NAME = "StrictHostKeyChecking";
    private static final String STRICT_HOST_KEY_CHECKING_VALUE = "no";
    private static final String HOST_PORT = "%s:%d";
    private static final String LOCALHOST = "localhost";

    private static SshProxyManager sshProxyManager = null;

    private Set<Session> sessions = new HashSet<>();
    private Map<String, Integer> portMapping = new HashMap<>();


    public synchronized static SshProxyManager getSSHProxyManager(List<SSHProxyConfig> configs) throws JSchException {
        sshProxyManager = getSSHProxyManager();
        sshProxyManager.addConfig(configs);
        return sshProxyManager;
    }

    public synchronized static SshProxyManager getSSHProxyManager() {
        if (null == sshProxyManager) {
            sshProxyManager = new SshProxyManager();
        }
        return sshProxyManager;
    }

    public synchronized void shutdown() {
        for (Session session : sessions) {
            session.disconnect();
        }
    }

    public void addConfig(List<SSHProxyConfig> configs) throws JSchException {
        for (SSHProxyConfig config : configs) {
            addConfig(config);
        }
    }

    public void addConfig(SSHProxyConfig config) throws JSchException {
        JSch jSch = new JSch();
        Session session;
        if (config.isPrivateKeyAuth()) {
            session = connectTunnelWithPrivateKey(jSch, config);
        } else {
            session = connectTunnelWithPassword(jSch, config);
        }
        setForward(session, config.getProxyHosts());
        sessions.add(session);
    }


    private void setForward(final Session session, Map<String, Integer> proxyHosts) throws JSchException {
        Set<String> hosts = proxyHosts.keySet();

        for (String host : hosts) {
            int localPort = SocketUtils.findAvailableTcpPort();
            int remotePort = proxyHosts.get(host);
            session.setPortForwardingL(localPort, host, remotePort);
            portMapping.put(String.format(HOST_PORT, host, remotePort), localPort);
        }
    }

    private Session connectTunnelWithPrivateKey(JSch jSch, SSHProxyConfig config) throws JSchException {
        String path = config.getPrivateKeyPath();
        if (path.startsWith(CLASSPATH)) {
            path = path.replace(CLASSPATH, NONE);
            path = getClass().getResource(path).getPath();
        }
        jSch.addIdentity(path, PASSPHRASE);
        Session session = jSch.getSession(config.getUsername(), config.getHost(), config.getPort());
        session.setConfig(PREFERRED_AUTHENTICATIONS_NAME, PREFERRED_AUTHENTICATIONS_VALUE);
        setSessionConfig(session);
        session.setServerAliveInterval(config.getKeepAliveInterval());
        session.connect(config.getTimeout());
        return session;
    }

    private Session connectTunnelWithPassword(JSch jSch, SSHProxyConfig config) throws JSchException {
        Session session = jSch.getSession(config.getUsername(), config.getHost(), config.getPort());
        session.setPassword(config.getPassword());
        session.setUserInfo(new TunnelUserInfo());
        setSessionConfig(session);
        session.setServerAliveInterval(config.getKeepAliveInterval());
        session.connect(config.getTimeout());
        return session;
    }

    private void setSessionConfig(Session session) {
        java.util.Properties config = new java.util.Properties();
        config.put(STRICT_HOST_KEY_CHECKING_NAME, STRICT_HOST_KEY_CHECKING_VALUE);
        session.setConfig(config);
        session.setDaemonThread(true);
    }

    public Integer getLocalPort(String host, int port) {
        return portMapping.get(String.format(HOST_PORT, host, port));
    }

    public String getLocalUrl(String urlStr) throws MalformedURLException {
        URL url = new URL(urlStr);
        Integer localPort = getSSHProxyManager().getLocalPort(url.getHost(), url.getPort());
        if (null == localPort) {
            return urlStr;
        }
        return urlStr.replace(url.getHost(), LOCALHOST).replace(String.valueOf(url.getPort()), String.valueOf(localPort));
    }

    private static class TunnelUserInfo implements UserInfo {
        @Override
        public String getPassphrase() {
            return null;
        }

        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public boolean promptPassword(String s) {
            return false;
        }

        @Override
        public boolean promptPassphrase(String s) {
            return false;
        }

        @Override
        public boolean promptYesNo(String s) {
            return false;
        }

        @Override
        public void showMessage(String s) {
            System.out.println(s);
        }
    }

}

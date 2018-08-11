package vip.iyatou.ssh.proxy;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.*;

/**
 * The type Ssh proxy manager.
 *
 * @author Fandy
 */
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
    private static final String JDBC_URL_PREFIX = "jdbc:";

    private static SshProxyManager sshProxyManager = null;

    private Set<Session> sessions = new HashSet<>();
    private Map<String, String> portMapping = new HashMap<>();


    /**
     * Gets ssh proxy manager and init configs.
     *
     * @param configs the configs
     * @return the ssh proxy manager
     * @throws JSchException the j sch exception
     */
    public synchronized static SshProxyManager getSSHProxyManager(List<SshProxyConfig> configs) throws JSchException {
        sshProxyManager = getSSHProxyManager();
        sshProxyManager.addConfig(configs);
        return sshProxyManager;
    }

    /**
     * Gets ssh proxy manager.
     *
     * @return the ssh proxy manager
     */
    public synchronized static SshProxyManager getSSHProxyManager() {
        if (null == sshProxyManager) {
            sshProxyManager = new SshProxyManager();
        }
        return sshProxyManager;
    }

    /**
     * Shutdown.
     */
    public synchronized void shutdown() {
        for (Session session : sessions) {
            session.disconnect();
        }
    }

    /**
     * Add config.
     *
     * @param configs the configs
     * @throws JSchException the j sch exception
     */
    public void addConfig(List<SshProxyConfig> configs) throws JSchException {
        for (SshProxyConfig config : configs) {
            addConfig(config);
        }
    }

    /**
     * Add config.
     *
     * @param config the config
     * @throws JSchException the j sch exception
     */
    public void addConfig(SshProxyConfig config) throws JSchException {
        JSch jSch = new JSch();
        Session session;
        if (config.isPrivateKeyAuth()) {
            session = connectTunnelWithPrivateKey(jSch, config);
        } else {
            session = connectTunnelWithPassword(jSch, config);
        }
        setForward(session, config.getHostProxyInfos());
        sessions.add(session);
    }


    private void setForward(final Session session, Set<HostProxyInfo> hostProxyInfos) throws JSchException {
        for (HostProxyInfo proxyInfo : hostProxyInfos) {
            int localPort = proxyInfo.getLocalPort();
            if (proxyInfo.useRandomPort()) {
                localPort = SocketUtils.findAvailableTcpPort();
            }
            session.setPortForwardingL(proxyInfo.getLocalHost(), localPort, proxyInfo.getRemoteHost(), proxyInfo.getRemotePort());
            portMapping.put(String.format(HOST_PORT, proxyInfo.getRemoteHost(), proxyInfo.getRemotePort()), String.format(HOST_PORT, proxyInfo.getLocalHost(), localPort));
        }
    }

    private Session connectTunnelWithPrivateKey(JSch jSch, SshProxyConfig config) throws JSchException {
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

    private Session connectTunnelWithPassword(JSch jSch, SshProxyConfig config) throws JSchException {
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

    /**
     * Gets local host and port.
     *
     * @param host the host
     * @param port the port
     * @return the local host and port
     */
    public String getLocalHostAndPort(String host, int port) {
        return portMapping.get(String.format(HOST_PORT, host, port));
    }

    /**
     * Gets local url.
     *
     * @param urlStr the url str
     * @return the local url
     * @throws MalformedURLException the malformed url exception
     */
    public String getLocalUrl(String urlStr) throws MalformedURLException {
        URI uri = URI.create(urlStr);
        String localHostAndPort = getSSHProxyManager().getLocalHostAndPort(uri.getHost(), uri.getPort());
        if (null == localHostAndPort) {
            return urlStr;
        }
        return urlStr.replace(String.format(HOST_PORT, uri.getHost(), uri.getPort()), localHostAndPort);
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

package vip.iyatou.ssh.proxy;

import com.jcraft.jsch.JSchException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Fandy
 * @project ssh-proxy
 * @description:
 * @create 2018-08-11 15:22
 **/
public class DatabaseProxyExample {

    protected  static void setProxy() throws JSchException {
        HostProxyInfo hostProxyInfo = new HostProxyInfo();

        hostProxyInfo.setRemoteHost("172.19.55.128");
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

    protected static void shutdown() {
        SshProxyManager.getSSHProxyManager().shutdown();
    }


}

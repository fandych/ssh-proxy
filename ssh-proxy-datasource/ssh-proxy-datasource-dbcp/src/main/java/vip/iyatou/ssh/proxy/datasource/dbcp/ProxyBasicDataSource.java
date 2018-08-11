package vip.iyatou.ssh.proxy.datasource.dbcp;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import vip.iyatou.ssh.proxy.SshProxyManager;

import java.net.MalformedURLException;

/**
 * @author Fandy
 * @project ssh-proxy
 * @description:
 * @create 2018-08-10 16:11
 **/
public class ProxyBasicDataSource extends BasicDataSource {
    private static final Log log = LogFactory.getLog(ProxyBasicDataSource.class);

    @Override
    public synchronized void setUrl(String url) {
        try {
            url = SshProxyManager.getSSHProxyManager().getLocalUrl(url);
        } catch (MalformedURLException e) {
            log.error(e);
        }
        super.setUrl(url);
    }
}

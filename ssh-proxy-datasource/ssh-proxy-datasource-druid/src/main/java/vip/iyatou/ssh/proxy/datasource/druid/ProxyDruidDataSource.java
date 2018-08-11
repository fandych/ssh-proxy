package vip.iyatou.ssh.proxy.datasource.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import vip.iyatou.ssh.proxy.SshProxyManager;

import java.net.MalformedURLException;

/**
 * @author Fandy
 * @project ssh-proxy
 * @description:
 * @create 2018-08-11 22:53
 **/
public class ProxyDruidDataSource extends DruidDataSource {
    private static final Log log = LogFactory.getLog(ProxyDruidDataSource.class);

    @Override
    public void setUrl(String jdbcUrl) {
        try {
            jdbcUrl = SshProxyManager.getSSHProxyManager().getLocalUrl(jdbcUrl);
        } catch (MalformedURLException e) {
            log.error(e.getLocalizedMessage());
        }
        super.setUrl(jdbcUrl);
    }
}

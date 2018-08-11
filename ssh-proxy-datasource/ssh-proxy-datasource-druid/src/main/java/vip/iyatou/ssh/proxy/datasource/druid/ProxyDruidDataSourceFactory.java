package vip.iyatou.ssh.proxy.datasource.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

/**
 * @author Fandy
 * @project ssh-proxy
 * @description:
 * @create 2018-08-11 22:59
 **/
public class ProxyDruidDataSourceFactory extends DruidDataSourceFactory {

    @Override
    protected DataSource createDataSourceInternal(Properties properties) throws Exception {
        DruidDataSource dataSource = new ProxyDruidDataSource();
        config(dataSource, properties);
        return dataSource;
    }

    public static DataSource createDataSource(Properties properties) throws Exception {
        return createDataSource((Map) properties);
    }

    public static DataSource createDataSource(Map properties) throws Exception {
        DruidDataSource dataSource = new ProxyDruidDataSource();
        config(dataSource, properties);
        return dataSource;
    }
}

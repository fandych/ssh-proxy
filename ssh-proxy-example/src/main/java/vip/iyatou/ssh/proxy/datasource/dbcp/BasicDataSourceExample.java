package vip.iyatou.ssh.proxy.datasource.dbcp;

import com.jcraft.jsch.JSchException;
import org.apache.commons.dbcp2.BasicDataSource;
import vip.iyatou.ssh.proxy.DatabaseProxyExample;

import java.sql.*;

/**
 * @author Fandy
 * @project ssh-proxy
 * @description:
 * @create 2018-08-11 15:21
 **/
public class BasicDataSourceExample extends DatabaseProxyExample {

    public static void main(String[] args) throws JSchException, SQLException {
        setProxy();

        connect();

        shutdown();
    }

    static void connect() throws SQLException {
        BasicDataSource basicDataSource = new ProxyBasicDataSource();
        basicDataSource.setUrl("jdbc:mysql://172.19.55.128:3306?useSSL=false");
        basicDataSource.setUsername("ebuyuser");
        basicDataSource.setPassword("pwd!ebuy3");
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        Connection connection = basicDataSource.getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("show databases;");

        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }

        connection.close();

        basicDataSource.close();
    }


}

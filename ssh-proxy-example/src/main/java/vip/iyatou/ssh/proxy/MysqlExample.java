package vip.iyatou.ssh.proxy;

import com.jcraft.jsch.JSchException;

import java.sql.*;

/**
 * @author Fandy
 * @project ssh-proxy
 * @description:
 * @create 2018-08-11 08:47
 **/
public class MysqlExample extends DatabaseProxyExample {

    public static void main(String[] args) throws JSchException, ClassNotFoundException, SQLException {
        setProxy();

        connect();

        shutdown();
    }


    static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3309?useSSL=false", "ebuyuser", "pwd!ebuy3");

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("show databases;");

        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }

        connection.close();
    }


}

package com.adchina.api.log;

import com.adchina.api.util.PropsUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库连接上下文
 *
 * @author huangyong
 * @since 1.0.0
 */
public class ConnectionContext {

    private static Logger logger = LoggerFactory.getLogger(ConnectionContext.class);

    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    private static BasicDataSource dataSource = new BasicDataSource();

    static {
        Properties config = PropsUtil.loadProps("config.properties");
        dataSource.setDriverClassName(PropsUtil.getString(config, "jdbc.driver"));
        dataSource.setUrl(PropsUtil.getString(config, "jdbc.url"));
        dataSource.setUsername(PropsUtil.getString(config, "jdbc.username"));
        dataSource.setPassword(PropsUtil.getString(config, "jdbc.password"));
    }

    public static Connection getConnection() {
        Connection connection = connectionHolder.get();
        if (connection == null) {
            try {
                connection = dataSource.getConnection();
            } catch (SQLException e) {
                logger.error("get connection failure", e);
                throw new RuntimeException(e);
            }
            if (connection != null) {
                connectionHolder.set(connection);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        Connection connection = connectionHolder.get();
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("close connection failure", e);
                throw new RuntimeException(e);
            }
            connectionHolder.remove();
        }
    }
}

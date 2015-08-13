package com.alimama.goldmine.api.log;

import com.adchina.api.log.LogAppender;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 应用日志 Appender
 *
 * @author huangyong
 * @since 1.0.0
 */
public class AppLogAppender extends LogAppender {

    @Override
    public void setColumn(PreparedStatement statement, LoggingEvent event) throws SQLException {
        statement.setString(1, getApp());
        statement.setLong(2, getTimeStamp(event));
        statement.setString(3, getLevel(event));
        statement.setString(4, getClassName(event));
        statement.setString(5, getLineNumber(event));
        statement.setString(6, getMessage(event));
        statement.setString(7, getError(event));
    }
}

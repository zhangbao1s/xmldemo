package com.alimama.sample.api.log;

import com.adchina.api.log.LogAppender;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 应用日志 Appender
 *
 * @author huangyong
 * @since 1.0.0
 */
public class AppLogAppender extends LogAppender {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss,SSS");

    @Override
    public void setColumn(PreparedStatement statement, LoggingEvent event) throws SQLException {
        long created = getTimeStamp(event);
        Date date = new Date(created);
        String createdDate = dateFormat.format(date);
        String createdTime = timeFormat.format(date);

        statement.setLong(1, created);
        statement.setString(2, createdDate);
        statement.setString(3, createdTime);
        statement.setString(4, getLevel(event));
        statement.setString(5, getClassName(event));
        statement.setString(6, getLineNumber(event));
        statement.setString(7, getMessage(event));
        statement.setString(8, getError(event));
    }
}

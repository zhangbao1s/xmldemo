package com.adchina.api.log;

import com.adchina.api.util.ArrayUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于数据库的日志 Appender
 *
 * @author huangyong
 * @since 1.0.0
 */
public abstract class LogAppender extends AppenderSkeleton {

    private static Logger logger = LoggerFactory.getLogger(LogAppender.class);

    private String sql;

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    protected final void append(final LoggingEvent event) {
        Connection connection = ConnectionContext.connect();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            setColumn(statement, event);
            statement.executeUpdate();
        } catch (Exception e) {
            logger.error("insert database failure", e);
        } finally {
            ConnectionContext.release();
        }
    }

    public abstract void setColumn(PreparedStatement statement, LoggingEvent event) throws SQLException;

    protected long getTimeStamp(LoggingEvent event) {
        return event.getTimeStamp();
    }

    protected String getLevel(LoggingEvent event) {
        return event.getLevel().toString();
    }

    protected String getClassName(LoggingEvent event) {
        return event.getLocationInformation().getClassName();
    }

    protected String getLineNumber(LoggingEvent event) {
        return event.getLocationInformation().getLineNumber();
    }

    protected String getMessage(LoggingEvent event) {
        return event.getMessage().toString();
    }

    protected String getError(LoggingEvent event) {
        String[] lines = event.getThrowableStrRep();
        StringBuilder error = new StringBuilder();
        if (ArrayUtil.isNotEmpty(lines)) {
            for (String line : lines) {
                error.append(line);
            }
        }
        return error.toString();
    }

    @Override
    public final void close() {
    }

    @Override
    public final boolean requiresLayout() {
        return false;
    }
}

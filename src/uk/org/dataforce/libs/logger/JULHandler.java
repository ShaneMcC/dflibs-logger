/*
 *  Copyright 2018 Shane Mc Cormack <shanemcc@gmail.com>.
 *  See LICENSE.txt for licensing details.
 */
package uk.org.dataforce.libs.logger;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

/**
 *
 * @author Shane Mc Cormack <shanemcc@gmail.com>
 */
public class JULHandler extends Handler {
    private final Map<Level, LogLevel> mappings = new HashMap<>();
    private final Map<LogLevel, Level> reverseMappings = new HashMap<>();

    private final LogManager manager;

    public JULHandler(final LogManager manager) {
        addMapping(Level.OFF, LogLevel.SILENT);
        addMapping(Level.ALL, LogLevel.DEBUG9);
        addMapping(Level.SEVERE, LogLevel.ERROR);
        addMapping(Level.WARNING, LogLevel.WARNING);
        addMapping(Level.INFO, LogLevel.INFO);
        addMapping(Level.FINE, LogLevel.DEBUG);
        reverseMappings.put(LogLevel.DEBUG2, Level.FINE);
        addMapping(Level.FINER, LogLevel.DEBUG3);
        reverseMappings.put(LogLevel.DEBUG4, Level.FINER);
        addMapping(Level.FINEST, LogLevel.DEBUG5);
        reverseMappings.put(LogLevel.DEBUG6, Level.FINEST);
        reverseMappings.put(LogLevel.DEBUG7, Level.FINEST);
        reverseMappings.put(LogLevel.DEBUG8, Level.FINEST);
        reverseMappings.put(LogLevel.DEBUG9, Level.ALL);
        addMapping(Level.CONFIG, LogLevel.INFO);

        this.manager = manager;
        super.setLevel(Level.ALL);
    }

    private void addMapping(final Level julLevel, final LogLevel ourLevel) {
        mappings.put(julLevel, ourLevel);
        reverseMappings.put(ourLevel, julLevel);
    }

    @Override
    public void publish(final LogRecord record) {
        final LogLevel level = mappings.getOrDefault(record.getLevel(), LogLevel.INFO);
        final Object params[] = record.getParameters();
        final String message = (params == null || params.length == 0) ? record.getMessage() : MessageFormat.format(record.getMessage(), record.getParameters());

        Logger.log(level, message);
    }

    @Override
    public void flush() { }

    @Override
    public void close() throws SecurityException { }

    public void setLogLevel(final LogLevel level) {
        final Level newLevel = reverseMappings.getOrDefault(level, Level.INFO);
        manager.getLogger("").setLevel(newLevel);
    }

    @Override
    public void setLevel(final Level level) {
        throw new UnsupportedOperationException("This handler does not allow setting the log level.");
    }

    public LogManager getManager() {
        return manager;
    }
}
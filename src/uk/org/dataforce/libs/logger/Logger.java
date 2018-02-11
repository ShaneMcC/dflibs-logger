/*
 * Copyright (c) 2006-2017 Shane Mc Cormack
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package uk.org.dataforce.libs.logger;

import java.io.Writer;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

/**
 * JISG Logger class.
 */
public class Logger {
    /** Current log level. */
    private static LogLevel logLevel = LogLevel.DEBUG2;

    /** Optional Tag for log entries. */
    private static String logTag = "";

    /** Optional Writer to write output to in addition to console. */
    private static Writer writer = null;

    /** Date format for logging entries. */
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /** Have we added the JULHandler? */
    private static JULHandler julHandler = null;

    /**
     * Change the timestamp format.
     *
     * @param newFormat New format to use.
     */
    public static void setTimestampFormat(final SimpleDateFormat newFormat) {
        sdf = newFormat;
    }

    /**
     * Change the logTag for this logger.
     *
     * @param newTag New tag to use.
     */
    public static void setTag(final String newTag) {
        logTag = (newTag == null) ? "" : newTag;
    }

    /**
     * Get the current logTag for this logger.
     *
     * @return The current log tag.
     */
    public static String getTag() {
        return logTag;
    }

    /**
     * Set a buffered writer for the logger to also log data to.
     * The Application (not Logger) is responsible for opening this and closing
     * it when no longer required.
     * Setting this to null will disable writing.
     * Logger will make no attempt to ensure that writing actually succeeds.
     *
     * @param newWriter New writer to use.
     */
    public static void setWriter(final Writer newWriter) {
        writer = newWriter;
    }

    /**
     * Get the current Writer we are using.
     *
     * @return The writer we are using.
     */
    public static Writer getWriter() {
        return writer;
    }

    /**
     * Log data at a customisable log level.
     *
     * @param level Level of this information.
     * @param data Information to log.
     */
    public static void log(final LogLevel level, final String data) {
        log(level, logTag, data);
    }

    /**
     * Log data at a customisable log level with a custom tag.
     *
     * @param level Level of this information.
     * @param tag Tag to log with.
     * @param data Information to log.
     */
    public static void log(final LogLevel level, final String tag, final String data) {
        if (level.isLoggable(logLevel) && level != LogLevel.SILENT) {
            final String timestamp = sdf != null ? "<" + sdf.format(new Date(System.currentTimeMillis())) + "> " : "";
            final String output = (data == null) ? "" : String.format("%s[%s%s] %s", timestamp, (tag == null || tag.isEmpty() ? "" : tag + ":"), level, data);

            System.out.println(output);
            if (writer != null) {
                try {
                    writer.append(output);
                    writer.append("\n");
                    writer.flush();
                } catch (final Exception e) {
                    // Writing failed, so abandon writer!
                    // This stops us trying this again every line.
                    writer = null;
                }
            }
        }
    }

    /**
     * Log data at the error level.
     *
     * @param data Information to log.
     */
    public static void error(final String data) {
        log(LogLevel.ERROR, data);
    }

    /**
     * Log data at the warning level.
     *
     * @param data Information to log.
     */
    public static void warning(final String data) {
        log(LogLevel.WARNING, data);
    }

    /**
     * Log data at the info level.
     *
     * @param data Information to log.
     */
    public static void info(final String data) {
        log(LogLevel.INFO, data);
    }

    /**
     * Log data at the debug level.
     *
     * @param data Information to log.
     */
    public static void debug(final String data) {
        log(LogLevel.DEBUG, data);
    }

    /**
     * Log data at the debug2 level.
     *
     * @param data Information to log.
     */
    public static void debug2(final String data) {
        log(LogLevel.DEBUG2, data);
    }

    /**
     * Log data at the debug3 level.
     *
     * @param data Information to log.
     */
    public static void debug3(final String data) {
        log(LogLevel.DEBUG3, data);
    }

    /**
     * Log data at the debug4 level.
     *
     * @param data Information to log.
     */
    public static void debug4(final String data) {
        log(LogLevel.DEBUG4, data);
    }

    /**
     * Log data at the debug5 level.
     *
     * @param data Information to log.
     */
    public static void debug5(final String data) {
        log(LogLevel.DEBUG5, data);
    }

    /**
     * Log data at the debug6 level.
     *
     * @param data Information to log.
     */
    public static void debug6(final String data) {
        log(LogLevel.DEBUG6, data);
    }

    /**
     * Log data at the debug7 level.
     *
     * @param data Information to log.
     */
    public static void debug7(final String data) {
        log(LogLevel.DEBUG7, data);
    }

    /**
     * Log data at the debug8 level.
     *
     * @param data Information to log.
     */
    public static void debug8(final String data) {
        log(LogLevel.DEBUG8, data);
    }

    /**
     * Log data at the debug9 level.
     *
     * @param data Information to log.
     */
    public static void debug9(final String data) {
        log(LogLevel.DEBUG9, data);
    }

    /**
     * Get the current log level.
     * Any data above the current log level will not be logged.
     *
     * @return The current LogLevel.
     */
    public static LogLevel getLevel() {
        return logLevel;
    }

    /**
     * Set the current log level.
     * Any data above the current log level will not be logged.
     *
     * @param level The new LogLevel.
     */
    public static void setLevel(final LogLevel level) {
        logLevel = level;
        if (julHandler != null) {
            julHandler.setLogLevel(getLevel());
        }
        debug2("LogLevel changed to: " + level);
    }

    /**
     * Add a JUL handler.
     */
    public static void addJULHandler() {
        if (julHandler != null) { return; }

        julHandler = new JULHandler(LogManager.getLogManager());
        julHandler.setLogLevel(getLevel());
    }

    /** Prevent instances of Logger */
    private Logger() {    }

    /** java.util.logging Handler to redirect log messages. */
    static class JULHandler extends Handler {
        private final Map<Level, LogLevel> mappings = new HashMap<>();
        private final Map<LogLevel, Level> reverseMappings = new HashMap<>();

        private final LogManager manager;

        private JULHandler(final LogManager manager) {
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
            manager.reset();
            manager.getLogger("").addHandler(this);
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
    }
}

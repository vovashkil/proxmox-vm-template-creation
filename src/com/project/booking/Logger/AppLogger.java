package com.project.booking.Logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.*;

import static com.project.booking.Constants.DataUtil.DATE_TIME_FORMAT;
import static com.project.booking.Constants.DataUtil.TIME_ZONE;
import static com.project.booking.Constants.FileUtil.LOG_FILEPATH_APP;

public class AppLogger {
    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;

    static public void setup() throws IOException {
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

//        Logger rootLogger = Logger.*getLogger*("");
//        Handler[] handlers = rootLogger.getHandlers();
//        if (handlers[0] instanceof ConsoleHandler) {
//            rootLogger.removeHandler(handlers[0]);
//        }

        logger.setLevel(Level.ALL);
        fileTxt = new FileHandler(LOG_FILEPATH_APP);

        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                return record.getLevel()
                        + " "
                        + LocalDateTime.now(ZoneId.of(TIME_ZONE))
                        .format(DateTimeFormatter
                                .ofPattern(DATE_TIME_FORMAT))
                        + " || "
                        + record.getSourceClassName().substring(
                        record.getSourceClassName().lastIndexOf(".")+1,
                        record.getSourceClassName().length())
                        + "."
                        + record.getSourceMethodName()
                        + "() : "
                        + record.getMessage() + "\n";
            }
        });
        logger.addHandler(fileTxt);

        logger.setUseParentHandlers(false);
    }
}

package com.project.booking.Logger;

import com.project.booking.Constants.DataUtil;
import com.project.booking.Constants.FileUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FlightLogger implements DataUtil, FileUtil {

    private String logMessage;
    private String logFilePath;

    public FlightLogger() {

        logFilePath = LOG_FILEPATH_FLIGHT;

    }

    public void info(String message) {

        logMessage = getLocalDateTime() + " [DEBUG] " + message;
        appendToLogFile(logMessage);

    }

    public void error(String message) {

        logMessage = getLocalDateTime() + " [ERROR] " + message;
        appendToLogFile(logMessage);

    }

    private String getLocalDateTime() {

        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));

    }

    private void appendToLogFile(String message) {

        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            File file = new File(logFilePath);

            file.getParentFile().mkdirs();

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            fw = new FileWriter(file.getAbsoluteFile(), true); // true = append file
            bw = new BufferedWriter(fw);

            bw.newLine();
            bw.write(message);

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException e) {

                e.printStackTrace();

            }
        }

    }

}

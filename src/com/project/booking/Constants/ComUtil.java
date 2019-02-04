package com.project.booking.Constants;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static com.project.booking.Constants.DataUtil.*;

public class ComUtil {
    public static long getCurrentDateTime() {
        return LocalDateTime.now(ZoneId.of(TIME_ZONE)).toInstant(getZoneOffset()).toEpochMilli();
    }

    public static ZoneOffset getZoneOffset() {
        return LocalDateTime.now(ZoneId.of(TIME_ZONE)).atZone(ZoneId.of(TIME_ZONE)).getOffset();
    }

    public static long dateTimeToLong(LocalDateTime dateTime) {
        ZoneOffset zoneOffset = dateTime.atZone(ZoneId.of(TIME_ZONE)).getOffset();
        return dateTime.toInstant(zoneOffset).toEpochMilli();
    }

    public static String dateLongToString(Long dateTime, String format) {
        return Instant.ofEpochMilli(dateTime)
                .atZone(ZoneId.of(TIME_ZONE))
                .toLocalDateTime()
                .format(DateTimeFormatter
                        .ofPattern(format));
    }

    public static String timeOfDayLongToString(Long time) {
        return LocalTime.ofNanoOfDay(time)
                .format(DateTimeFormatter.ofPattern(TIME_FORMAT));
    }

    public static long parseTime(String str) {
        LocalTime time = LocalTime.now(ZoneId.of(TIME_ZONE));
        str = str.replaceAll("[^0-9,:]", "");
        try {
            time = LocalTime.parse(str, DateTimeFormatter.ofPattern(TIME_FORMAT));
        } catch (DateTimeParseException e) {
            System.out.println(e.getStackTrace());
        }
        return time.toNanoOfDay();
    }

    public static long parseDate(String str) {
        LocalDate date = LocalDate.now(ZoneId.of(TIME_ZONE));
        ZoneOffset zoneOffset = date.atStartOfDay(ZoneId.of(TIME_ZONE)).getOffset();
        str = str.replaceAll("[^0-9,/]", "");
        try {
            date = LocalDate.parse(str, DateTimeFormatter.ofPattern(DATE_FORMAT));
        } catch (DateTimeParseException e) {
            System.out.println(e.getStackTrace());
        }
//        return date.atStartOfDay(ZoneId.of(TIME_ZONE)).toInstant().toEpochMilli();
        return date.atStartOfDay().toInstant(zoneOffset).toEpochMilli();
    }

    public static int parseAndValidateInputInteger(String message, int startRange, int endRange) {
        int result = 0;
        boolean control = true;
        System.out.print(message);
        while (control) {
            Scanner input = new Scanner(System.in);
            try {
                result = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("You entered incorrect type. ");
                result = -1;
            }
            if (result >= startRange && result <= endRange) control = false;
            else System.out.print("Enter correct number between " +
                    startRange + " and " + endRange + " : ");
        }
        return result;
    }

    public static String parseAndValidateInputString(String message, String pattern, String name, String example) {
        String result = "";
        boolean control = true;
        System.out.print(message);
        Scanner input = new Scanner(System.in);
        while (control) {
            result = input.nextLine().trim();
            if (result.trim().matches(pattern)) control = false;
            else System.out.print("Enter correct " + name + " \'" + pattern + "\' (e.g. " + example + "): ");
        }
        return result;
    }

}

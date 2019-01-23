package com.project.booking;

import com.opencsv.CSVReader;
import com.project.booking.Flight.Flight;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class Main {

    private static final String SAMPLE_CSV_FILE_PATH = "./files/db/kbp_online.csv";

    public static void main(String[] args) {

//        ConsoleApp app = new ConsoleApp();
//        app.startApp();
//test

        List<Flight> flights = new ArrayList();

        try (
                Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
                CSVReader csvReader = new CSVReader(reader);
        ) {
            // Reading Records One by One in a String array
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {

                flights.add(new Flight(nextRecord[0],
                        "Kiev Boryspil",
                        nextRecord[2],
                        100,
                        nextRecord[1].trim(),
                        "12:30"
                ));


            }
        } catch (IOException e) {

            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());

        }


        flights.stream().forEach(System.out::println);


//        LocalDate date1 = LocalDate.of(2018, 12, 13);
//
//        long dateAslong = date1.atStartOfDay(ZoneId.of(TimeZone)).
//
//        System.out.println(date1);

        final String DATE_FORMAT = "dd/MM/yyyy";
        final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";
        final String DEPARTURE_TIME_FORMAT = "HH:mm";
        final String TIME_ZONE = "Europe/Kiev";
        long result;

        LocalTime time = LocalTime.parse("01:30", DateTimeFormatter.ofPattern(DEPARTURE_TIME_FORMAT));
        System.out.println("result = " + time);

        time = LocalTime.parse("10:30", DateTimeFormatter.ofPattern(DEPARTURE_TIME_FORMAT));
        System.out.println("result = " + time);

        time = LocalTime.parse("15:30", DateTimeFormatter.ofPattern(DEPARTURE_TIME_FORMAT));
        System.out.println("result = " + time);

        //        ZoneOffset zoneOffset = ZoneOffset.of;
//        result = time.toInstant(zoneOffset).toEpochMilli();



    }


}



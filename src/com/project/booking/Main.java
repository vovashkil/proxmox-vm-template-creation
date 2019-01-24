package com.project.booking;

import com.opencsv.CSVReader;
import com.project.booking.Flight.Flight;
import com.project.booking.Persons.Person;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


public class Main {

    private static final String SAMPLE_CSV_FILE_PATH = "./files/db/kbp_online.csv";
    private Person loginUser;
    private static String DATE_FORMAT = "dd/MM/yyyy";
    private static String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";
    private static String TIME_FORMAT = "HH:mm";
    private static String TIME_ZONE = "Europe/Kiev";

    public static void main(String[] args) {

        //ConsoleApp app = new ConsoleApp();
        //app.startApp();

        //app.loginUser();//Olga :: start select login or regiter user

        List<Flight> flights = new ArrayList();

        try (
                Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
                CSVReader csvReader = new CSVReader(reader);
        ) {
            // Reading Records One by One in a String array
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {

              long result;
                String dateAsString = LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
                System.out.println("dateAsString = " + dateAsString );
                String tmp = nextRecord[1];
                System.out.println("nextRecord[1] = " + tmp );
                String tmpDateTime = dateAsString + "T" + tmp;
                System.out.println("dataTime = " +  tmpDateTime );

                int H=0;
                int m =0;
                String[] strings = tmp.split(":");

               try {
                   H = Integer.parseInt(strings[0]);
                   m = Integer.parseInt(strings[1]);
               } catch (NumberFormatException e) {
                   H = 0;
                   m=0;
               }

                StringBuffer strBuffer = new StringBuffer(dateAsString);
                strBuffer.append(" ");
                strBuffer.append(H + ":" + m);
                tmpDateTime = strBuffer.toString();

                CharSequence cs = new String(tmpDateTime);

                System.out.println("final dataTime = " +  tmpDateTime );
                System.out.println("exception at " + tmpDateTime.substring(11));

                try {





//                    dateAsString = Instant.ofEpochMilli(result).atZone(ZoneId.of(TIME_ZONE)).toLocalDateTime().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));

                    LocalDateTime dateTime = LocalDateTime.parse(cs, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
                    ZoneOffset zoneOffset = dateTime.atZone(ZoneId.of(TIME_ZONE)).getOffset();
                    result = dateTime.toInstant(zoneOffset).toEpochMilli();



                } catch (DateTimeParseException e) {

                    System.out.println("exception" + e.getMessage());
                    System.out.println("ErrorIndex" + e.getErrorIndex());
                    System.out.println("parsed" + e.getParsedString());

                    result = Long.MIN_VALUE;

                }


                flights.add(new Flight(nextRecord[0],
                        "Kiev Boryspil",
                        nextRecord[2],
                        100,
                        result,
                        54000000
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


        long result;

        LocalTime time = LocalTime.parse("01:30", DateTimeFormatter.ofPattern(TIME_FORMAT));
        System.out.println("result = " + time);

        time = LocalTime.parse("10:30", DateTimeFormatter.ofPattern(TIME_FORMAT));
        System.out.println("result = " + time);

        time = LocalTime.parse("15:30", DateTimeFormatter.ofPattern(TIME_FORMAT));
        System.out.println("result = " + time);

        //        ZoneOffset zoneOffset = ZoneOffset.of;
//        result = time.toInstant(zoneOffset).toEpochMilli();


        String dateTimeAsString = "23/01/2019" + " " + "21:23";


        try {

            LocalDateTime dateTime = LocalDateTime.parse(dateTimeAsString, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
            ZoneOffset zoneOffset = dateTime.atZone(ZoneId.of(TIME_ZONE)).getOffset();
            result = dateTime.toInstant(zoneOffset).toEpochMilli();

        } catch (DateTimeParseException e) {

            result = Long.MIN_VALUE;

        }

        System.out.println("result = " + result);
        System.out.println(Instant.ofEpochMilli(result).atZone(ZoneId.of(TIME_ZONE)).toLocalDateTime().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));


    }


}



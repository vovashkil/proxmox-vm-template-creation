package com.project.booking.Console;

import com.opencsv.CSVReader;
import com.project.booking.Booking.Flight;
import com.project.booking.Controllers.Storage;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.logging.Logger;

import static com.project.booking.Constants.ComUtil.dateTimeToLong;
import static com.project.booking.Constants.ComUtil.parseTime;
import static com.project.booking.Constants.DataUtil.TIME_ZONE;
import static com.project.booking.Constants.FileUtil.KBP_SCHEDULE_FILE_PATH;

public class CmdCreateFlights extends CommandBase implements Command {
    public CmdCreateFlights(Logger log, Storage storage) {
        super(log, storage);
    }

    @Override
    public String text() {
        return "CREATE";
    }

    @Override
    public String description() {
        return "Create new flights from schedule";
    }

    @Override
    public void doCommand() {
        //     m.method70_flightDbFromScheduleFile();
        System.out.println("Resetting/Generating new database of flights...");

        LocalTime currentTime = LocalTime.now(ZoneId.of(TIME_ZONE));
        LocalDate currentDate = LocalDate.now(ZoneId.of(TIME_ZONE));

        try (
                Reader reader = Files.newBufferedReader(Paths.get(KBP_SCHEDULE_FILE_PATH));
                CSVReader csvReader = new CSVReader(reader, ',', '\'', 1);
        ) {

            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {

                long flightDepartureTimeLong = parseTime(nextRecord[1]);
                long flightDurationTimeLong = parseTime(nextRecord[7]);
                LocalDate flightDepartureDate = currentDate;

                if (flightDepartureTimeLong <= currentTime.toNanoOfDay())
                    flightDepartureDate = currentDate.plusDays(1);

                long departureDateTimeLong = dateTimeToLong(
                        LocalDateTime.of(
                                flightDepartureDate,
                                LocalTime.ofNanoOfDay(flightDepartureTimeLong)
                        )
                );

                storage.getFlights().saveFlight(
                        new Flight(nextRecord[0],
                                departureDateTimeLong,
                                flightDurationTimeLong,
                                "Kiev Boryspil",
                                nextRecord[2],
                                150
                        ));
            }
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean isAllowToUnAuth() {
        return true;
    }
}

package com.project.booking.Console;

import com.project.booking.Controllers.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Commands {
    public static List<Command> all(Logger log, Storage storage, Auth a) {
        return new ArrayList<Command>() {{
            add(new CmdShow(log, storage));
            add(new CmdLogin(log, storage));
            add(new CmdRegister(log, storage));
            add(new CmdCloseSession(log, storage, a));
            add(new CmdExit(log, storage));
            add(new CmdCreateFlights(log, storage));
//            add(new CmdBook(log, storage));
//            add(new CmdHelp(log, storage, a));
//            add(new CmdExit(log, storage));
//            add(new CmdAutorize(log, storage, a));
        }};
    }
}

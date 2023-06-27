package com.project.helpers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static Logger logger = null;

    private final String machinesLogFile = "files/machines-log.txt";
    private final String pressLogFile = "files/press-log.txt";
    private PrintWriter machinesWriter;
    private PrintWriter pressWriter;

    public static synchronized Logger getInstance(){
        if(logger == null)
            logger = new Logger();
        return logger;
    }

    private Logger() {
        try {
            FileWriter fw = new FileWriter(machinesLogFile);
            machinesWriter = new PrintWriter(fw, true);
            FileWriter pressFw = new FileWriter(pressLogFile);
            pressWriter = new PrintWriter(pressFw, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logPressWork(String log){
        pressWriter.println(log);
    }

    public void logMachineWork(int machineNum, String log){
        machinesWriter.println(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()) +
                " [MACHINE " + machineNum + "]: " +
                log);
    }
}

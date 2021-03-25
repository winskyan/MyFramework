package com.my.library_base.logs;

import com.my.library_base.constants.Constants;
import com.my.library_base.init.BaseModuleInit;

import org.apache.log4j.Level;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.mindpipe.android.logging.log4j.LogConfigurator;

public class ConfigureLog4j {

    public static void initLog4j() {
        try {
            LogConfigurator logConfigurator = new LogConfigurator();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
            logConfigurator.setFileName(BaseModuleInit.applicationContext.getExternalFilesDir(null)
                    + File.separator + "logs"
                    + File.separator + "logs_" + Constants.TAG + "_" + format.format(new Date()) + ".txt");
            logConfigurator.setRootLevel(Level.DEBUG);
            logConfigurator.setLevel("org.apache", Level.ERROR);
            logConfigurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");
            logConfigurator.setMaxFileSize(1024 * 1024 * 5);
            logConfigurator.setImmediateFlush(true);
            logConfigurator.configure();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

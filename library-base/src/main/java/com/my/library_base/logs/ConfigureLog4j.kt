package com.my.library_base.logs

import com.my.library_base.constants.Constants
import com.my.library_base.init.BaseModuleInit
import de.mindpipe.android.logging.log4j.LogConfigurator
import org.apache.log4j.Level
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object ConfigureLog4j {
    fun initLog4j() {
        try {
            val logConfigurator = LogConfigurator()
            val format = SimpleDateFormat("yyyyMMddhhmmss")
            logConfigurator.fileName = (BaseModuleInit.Companion.applicationContext!!.getExternalFilesDir(null)
                    .toString() + File.separator + "logs"
                    + File.separator + "logs_" + Constants.TAG + "_" + format.format(Date()) + ".txt")
            logConfigurator.rootLevel = Level.DEBUG
            logConfigurator.setLevel("org.apache", Level.ERROR)
            logConfigurator.filePattern = "%d %-5p [%c{2}]-[%L] %m%n"
            logConfigurator.maxFileSize = (1024 * 1024 * 5).toLong()
            logConfigurator.isImmediateFlush = true
            logConfigurator.configure()
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }
}
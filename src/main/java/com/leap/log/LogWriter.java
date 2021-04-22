/**
 * 
 */
package com.leap.log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * @author LEAP
 *
 */
public class LogWriter {
	FileHandler fh = null;
	Logger logger = Logger.getLogger(LogWriter.class.getName());

	public void closeLogger() {

		if (fh != null) {
			fh.close();
		}

	}

	public Logger getCustomLogger(String customLogFile) {

		// This block configure the logger with handler and formatter
		try {
			fh = new FileHandler(customLogFile);
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.addHandler(fh);

		SimpleFormatter formatter = new SimpleFormatter();

		fh.setFormatter(formatter);

		logger.setLevel(Level.ALL);

		DateTimeZone zone = DateTimeZone.getDefault();
		DateTime dt = new DateTime(zone);
		int day = dt.getDayOfMonth();
		int year = dt.getYear();
		int month = dt.getMonthOfYear();
		int hours = dt.getHourOfDay();
		int minutes = dt.getMinuteOfHour();
		logger.info("Log Date : " + (day + "/" + month + "/" + year + " " + hours + ":" + minutes));
		return logger;
	}
}

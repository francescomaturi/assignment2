package assignment2.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	private static SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

	public static Date parse(String dateString) {

		Date date;
		try {
			date = format.parse(dateString);

		} catch (ParseException e) {
			date = null;
		}

		return date;
	}
}

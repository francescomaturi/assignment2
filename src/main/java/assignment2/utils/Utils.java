package assignment2.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import assignment2.model.Person;

public class Utils {

	private static SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

	public static Date parseDate(String dateString) {

		Date date;
		try {
			date = format.parse(dateString);

		} catch (ParseException e) {
			date = null;
		}

		return date;
	}

	public static ArrayList<String> parseQuery(String queryText) {

		String[] arr = queryText.split("\\s+");

		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < arr.length; i++) {
			if (!arr[i].isEmpty()) {
				list.add(arr[i]);
			}
		}

		return list;
	}

	public static ArrayList<Person> clear(ArrayList<Person>... mArrayLists) {

		Set<Person> set = new HashSet<Person>();

		for (int i = 0; i < mArrayLists.length; i++) {
			set.addAll(mArrayLists[i]);
		}

		return new ArrayList<Person>(set);
	}

}

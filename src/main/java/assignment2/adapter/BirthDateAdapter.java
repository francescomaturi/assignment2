package assignment2.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BirthDateAdapter extends XmlAdapter<String, Date> {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	@Override
	public String marshal(Date v) throws Exception {
		return dateFormat.format(v);
	}

	@Override
	public Date unmarshal(String v) throws Exception {
		return dateFormat.parse(v);
	}

	// public static void main(String[] args) {
	// Calendar c = Calendar.getInstance();
	// c.set(Calendar.YEAR, 1990);
	// c.set(Calendar.MONTH, 0);
	// c.set(Calendar.DAY_OF_MONTH, 27);
	// System.out.println(c.getTimeInMillis());
	// }
}
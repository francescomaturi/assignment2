package assignment2.utils;

import java.text.DecimalFormat;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DoubleAdapter extends XmlAdapter<String, Double> {

	private DecimalFormat decimalFormat = new DecimalFormat("##.###");

	@Override
	public String marshal(Double arg0) throws Exception {
		return decimalFormat.format(arg0.doubleValue());
	}

	@Override
	public Double unmarshal(String arg0) throws Exception {
		return (Double) decimalFormat.parse(arg0);
	}

}

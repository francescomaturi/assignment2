package assignment2.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/measures")
public class MeasuresService {

	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	public String getMeasuresTextPlain() {
		return "height,weight,bmi,date";
	}

}

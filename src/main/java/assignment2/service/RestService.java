package assignment2.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import assignment2.hibernate.PeopleCompareDB;
import assignment2.hibernate.PeopleDB;
import assignment2.model.Person;
import assignment2.utils.Utils;

@Path("/search")
public class RestService {

	/**
	 * GET
	 * 
	 * /person/birthdate?from=dd-mm-yyyy&to=dd-mm-yyyy
	 */

	@GET
	@Path("/birthdate")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ArrayList<Person> getPeopleByBirthdate(@PathParam("p_id") Long p_id,
			@QueryParam("to") String before_qp,
			@QueryParam("from") String after_qp) {

		if (after_qp != null && before_qp != null) {
			ArrayList<Person> list = PeopleCompareDB.birthdate(
					Utils.parseDate(after_qp), Utils.parseDate(before_qp));

			return !list.isEmpty() ? list : null;
		}
		return null;
	}

	/**
	 * GET
	 * 
	 * /person/profile?measure={height|weight}&min=MIN&max=MAX
	 */

	@GET
	@Path("/profile")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ArrayList<Person> getPeopleByMeasure(
			@QueryParam("measure") String measure,
			@QueryParam("max") Double max, @QueryParam("min") Double min) {

		if (measure != null) {

			if (measure.equalsIgnoreCase("height")) {
				ArrayList<Person> list = PeopleCompareDB.height(min, max);
				return !list.isEmpty() ? list : null;

			} else if (measure.equalsIgnoreCase("weight")) {
				ArrayList<Person> list = PeopleCompareDB.weight(min, max);
				return !list.isEmpty() ? list : null;
			}
		}
		return null;
	}

	/**
	 * GET
	 * 
	 * /person/search?q=TEXT_TO_SEARCH
	 */

	@GET
	@Path("/search")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Person> getPeopleByMeasure(@QueryParam("q") String query) {

		if (query != null) {

			String[] string = query.trim().split("\\s+");

			if (string.length > 0) {
				ArrayList<Person> list = PeopleCompareDB.search(string);
				return !list.isEmpty() ? list : null;
			}
		}
		return PeopleDB.getPeople();
	}

}

package assignment2.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import assignment2.hibernate.HealthProfileDB;
import assignment2.hibernate.PeopleCompareDB;
import assignment2.hibernate.PeopleDB;
import assignment2.model.HealthProfile;
import assignment2.model.Person;
import assignment2.utils.Utils;

@Path("/person")
public class RestService {

	/**
	 * GET POST
	 * 
	 * /person
	 */

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Person> getPeople() {
		return PeopleDB.getPeople();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createPerson(Person person) {
		// controllo perche magari non mi parsa la data correttamente JAXB
		if (person.getBirthdate() != null && person.getWeight() != null
				&& person.getHeight() != null && person.getFirstname() != null
				&& person.getLastname() != null) {

			person.setPerson_id(null);
			person.setLastupdate(new Date());

			person = PeopleDB.savePerson(person);

			return Response.status(Response.Status.OK).entity(person).build();

		} else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	/**
	 * GET PUT DELETE
	 * 
	 * /person/id
	 */

	@GET
	@Path("/{p_id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Person getPerson(@PathParam("p_id") Long p_id) {
		return PeopleDB.getPerson(p_id);
	}

	@PUT
	@Path("/{p_id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updatePerson(@PathParam("p_id") Long p_id, Person json) {

		Person dbPerson = PeopleDB.getPerson(p_id);

		if (dbPerson != null && json.getFirstname() != null
				&& json.getLastname() != null && json.getBirthdate() != null
				&& json.getWeight() != null && json.getHeight() != null) {

			// metto nella history il corrente perche ora lo aggiorno
			HealthProfileDB.saveHealthProfile(new HealthProfile(dbPerson
					.getPerson_id(), dbPerson.getWeight(),
					dbPerson.getHeight(), dbPerson.getLastupdate()));

			// aggionro i dati della persona
			dbPerson.setFirstname(json.getFirstname());
			dbPerson.setLastname(json.getLastname());
			dbPerson.setBirthdate(json.getBirthdate());

			// aggiorno i valori correnti
			dbPerson.setHeight(json.getHeight());
			dbPerson.setWeight(json.getWeight());
			dbPerson.setLastupdate(new Date());

			// aggiorno nel db la persona
			dbPerson = PeopleDB.updatePerson(dbPerson);

			return Response.status(Response.Status.OK).entity(dbPerson).build();

		} else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	@DELETE
	@Path("/{p_id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response deletePerson(@PathParam("p_id") Long p_id) {

		Person person = PeopleDB.deletePerson(p_id);
		if (person != null) {

			ArrayList<HealthProfile> history = HealthProfileDB
					.getPersonHealthProfileHistory(p_id);
			person.setHealthProfileHistory(history);

			HealthProfileDB.deletePersonHealthProfileHistory(history);

			return Response.status(Response.Status.OK).entity(person).build();

		} else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	/**
	 * GET POST
	 * 
	 * /person/id/healthprofile
	 */

	@GET
	@Path("/{p_id}/healthprofile")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ArrayList<HealthProfile> getPersonHealthProfileHistory(
			@PathParam("p_id") Long p_id) {

		ArrayList<HealthProfile> history = HealthProfileDB
				.getPersonHealthProfileHistory(p_id);

		return !history.isEmpty() ? history : null;
	}

	@POST
	@Path("/{p_id}/healthprofile")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ArrayList<HealthProfile> setHealthProfile(
			@PathParam("p_id") Long p_id, HealthProfile json) {

		Person dbPerson = PeopleDB.getPerson(p_id);

		if (dbPerson != null && json != null && json.getHeight() != null
				&& json.getWeight() != null) {

			// metto nella history il corrente perche ora lo aggiorno
			HealthProfileDB.saveHealthProfile(new HealthProfile(dbPerson
					.getPerson_id(), dbPerson.getWeight(),
					dbPerson.getHeight(), dbPerson.getLastupdate()));

			// aggiorno la persona col nuovo hp
			dbPerson.setLastupdate(new Date());
			dbPerson.setHeight(json.getHeight());
			dbPerson.setWeight(json.getWeight());

			dbPerson = PeopleDB.updatePerson(dbPerson);
		}

		ArrayList<HealthProfile> history = HealthProfileDB
				.getPersonHealthProfileHistory(p_id);

		return !history.isEmpty() ? history : null;
	}

	/**
	 * GET PUT DELETE
	 * 
	 * /person/p_id/healthprofile/hp_id
	 */

	@GET
	@Path("/{p_id}/healthprofile/{hp_id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public HealthProfile getHealthProfile(@PathParam("p_id") Long p_id,
			@PathParam("hp_id") Long hp_id) {

		return HealthProfileDB.getSpecificHealthProfile(p_id, hp_id);
	}

	@PUT
	@Path("/{p_id}/healthprofile/{hp_id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updateHealthProfileX(@PathParam("p_id") Long p_id,
			@PathParam("hp_id") Long hp_id, HealthProfile newHp) {

		HealthProfile dbHp = HealthProfileDB.getSpecificHealthProfile(p_id,
				hp_id);
		if (dbHp != null && newHp.getWeight() != null
				&& newHp.getHeight() != null) {

			dbHp.setHeight(newHp.getHeight());
			dbHp.setWeight(newHp.getWeight());

			dbHp = HealthProfileDB.updateHealthProfile(dbHp);

			return Response.status(Response.Status.OK).entity(dbHp).build();
		}

		return Response.status(Response.Status.BAD_REQUEST).build();
	}

	@DELETE
	@Path("/{p_id}/healthprofile/{hp_id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response deleteHealthProfileX(@PathParam("p_id") Long p_id,
			@PathParam("hp_id") Long hp_id) {

		HealthProfile hp = HealthProfileDB
				.getSpecificHealthProfile(p_id, hp_id);

		if (hp != null) {
			HealthProfileDB.deleteHealthProfile(hp_id);

			return Response.status(Response.Status.OK).entity(hp).build();
		}

		return Response.status(Response.Status.BAD_REQUEST).build();
	}

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

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import assignment2.hibernate.HealthProfileDB;
import assignment2.hibernate.PersonDB;
import assignment2.model.HealthProfile;
import assignment2.model.Person;

@Path("/person")
public class PersonService {

	/**
	 * GET POST
	 * 
	 * /person
	 */

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Person> getPeople() {

		return PersonDB.getPeople();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createPerson(Person person) {
		// controllo perche magari non mi parsa la data correttamente JAXB
		if (person.getBirthdate() != null && person.getWeight() != null
				&& person.getHeight() != null) {
			// se me l'hai passato lo annullo perche me lo genera il DB
			person.setPerson_id(null);
			person = PersonDB.savePerson(person);

			HealthProfileDB.saveHealthProfile(new HealthProfile(person
					.getPerson_id(), person.getWeight(), person.getHeight(),
					new Date()));

			person.setHealthProfileHistory(HealthProfileDB
					.getPersonHealthProfileHistory(person.getPerson_id()));

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

		return PersonDB.getPerson(p_id);
	}

	@PUT
	@Path("/{p_id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updatePerson(@PathParam("p_id") Long p_id, Person person) {

		Person p = PersonDB.getPerson(p_id);

		if (p != null && p.getPerson_id().equals(person.getPerson_id())
				&& person.getBirthdate() != null && person.getWeight() != null
				&& person.getHeight() != null) {

			person = PersonDB.updatePerson(person);

			HealthProfileDB.saveHealthProfile(new HealthProfile(person
					.getPerson_id(), person.getWeight(), person.getHeight(),
					new Date()));

			person.setHealthProfileHistory(HealthProfileDB
					.getPersonHealthProfileHistory(p_id));

			return Response.status(Response.Status.OK).entity(person).build();

		} else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	@DELETE
	@Path("/{p_id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response deletePerson(@PathParam("p_id") Long p_id) {

		Person person = PersonDB.deletePerson(p_id);
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
	public Person getPersonHealthProfileHistory(@PathParam("p_id") Long p_id) {

		Person person = PersonDB.getPerson(p_id);
		if (person != null) {

			person.setHealthProfileHistory(HealthProfileDB
					.getPersonHealthProfileHistory(p_id));
		}
		return person;
	}

	@POST
	@Path("/{p_id}/healthprofile")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Person setHealthProfile(@PathParam("p_id") Long p_id,
			HealthProfile hp) {

		Person p = PersonDB.getPerson(p_id);

		if (p != null && hp != null && hp.getHeight() != null
				&& hp.getWeight() != null) {

			hp.setPerson_id(p_id);
			hp.setDate(new Date());

			HealthProfileDB.saveHealthProfile(hp);

			p.setHealthProfileHistory(HealthProfileDB
					.getPersonHealthProfileHistory(p_id));
		}

		return p;
	}

	/**
	 * PUT DELETE
	 * 
	 * /person/p_id/healthprofile/hp_id
	 */

	@PUT
	@Path("/{p_id}/healthprofile/{hp_id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updateHealthProfileX(@PathParam("p_id") Long p_id,
			@PathParam("hp_id") Long hp_id, HealthProfile hp) {

		Person person = PersonDB.getPerson(p_id);
		if (person != null && hp.getWeight() != null && hp.getHeight() != null) {

			HealthProfile old = HealthProfileDB.getSpecificHealthProfile(p_id,
					hp_id);
			if (old != null) {

				old.setHeight(hp.getHeight());
				old.setWeight(hp.getWeight());

				HealthProfileDB.updateHealthProfile(old);

				person.setHealthProfileHistory(HealthProfileDB
						.getPersonHealthProfileHistory(p_id));

				return Response.status(Response.Status.OK).entity(person)
						.build();
			}
		}
		return Response.status(Response.Status.BAD_REQUEST).build();
	}

	@DELETE
	@Path("/{p_id}/healthprofile/{hp_id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response deleteHealthProfileX(@PathParam("p_id") Long p_id,
			@PathParam("hp_id") Long hp_id) {

		Person person = PersonDB.getPerson(p_id);
		if (person != null) {

			HealthProfile hp = HealthProfileDB.getSpecificHealthProfile(p_id,
					hp_id);

			if (hp != null) {
				HealthProfileDB.deleteHealthProfile(hp_id);

				person.setHealthProfileHistory(HealthProfileDB
						.getPersonHealthProfileHistory(p_id));

				return Response.status(Response.Status.OK).entity(person)
						.build();
			}
		}
		return Response.status(Response.Status.BAD_REQUEST).build();
	}
	//
	// @GET
	// @Path("/{p_id}/healthprofile")
	// @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	// public Person getHealthProfile(@PathParam("p_id") Long p_id,
	// @QueryParam("before") String before,
	// @QueryParam("after") String after) throws ParseException {
	//
	// Person p = PersonDB.getPerson(p_id);
	// if (p != null) {
	//
	// SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	//
	// ArrayList<HealthProfile> history;
	//
	// if (before != null && after != null
	// && before.matches("[0-9]{2}-[0-9]{2}-[0-9]{4}")
	// && after.matches("[0-9]{2}-[0-9]{2}-[0-9]{4}")) {
	//
	// history = HealthProfileDB.getPersonHealthProfileBefore(p_id,
	// df.parse(before), df.parse(after));
	//
	// } else if (before != null
	// && before.matches("[0-9]{2}-[0-9]{2}-[0-9]{4}")) {
	//
	// history = HealthProfileDB.getPersonHealthProfileBefore(p_id,
	// df.parse(before), null);
	//
	// } else if (after != null
	// && after.matches("[0-9]{2}-[0-9]{2}-[0-9]{4}")) {
	//
	// history = HealthProfileDB.getPersonHealthProfileBefore(p_id,
	// null, df.parse(after));
	//
	// } else {
	//
	// history = HealthProfileDB.getPersonHealthProfileHistory(p_id);
	// }
	// p.setHealthProfileHistory(history);
	// }
	// return p;
	// }

}

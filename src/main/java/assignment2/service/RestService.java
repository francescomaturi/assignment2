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

import assignment2.hibernate.PeopleCompareDB;
import assignment2.hibernate.HealthProfileDB;
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

		Person p = PeopleDB.getPerson(p_id);

		p.setHealthProfileIds(HealthProfileDB.getHealthProfileIds(p_id));
		return p;
	}

	@PUT
	@Path("/{p_id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updatePerson(@PathParam("p_id") Long p_id,
			Person updatedPerson) {

		Person dbPerson = PeopleDB.getPerson(p_id);

		if (dbPerson != null
				&& dbPerson.getPerson_id().equals(updatedPerson.getPerson_id())
				&& updatedPerson.getBirthdate() != null
				&& updatedPerson.getWeight() != null
				&& updatedPerson.getHeight() != null) {

			// metto nella history il corrente perche ora lo aggiorno
			HealthProfileDB.saveHealthProfile(new HealthProfile(dbPerson
					.getPerson_id(), dbPerson.getWeight(),
					dbPerson.getHeight(), dbPerson.getLastupdate()));

			updatedPerson.setLastupdate(new Date());
			updatedPerson = PeopleDB.updatePerson(updatedPerson);

			updatedPerson.setHealthProfileIds(HealthProfileDB
					.getHealthProfileIds(p_id));

			return Response.status(Response.Status.OK).entity(updatedPerson)
					.build();
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

		// Person person = PersonDB.getPerson(p_id);
		// if (person != null) {
		// person.setHealthProfileHistory(HealthProfileDB
		// .getPersonHealthProfileHistory(p_id));
		// }
		return HealthProfileDB.getPersonHealthProfileHistory(p_id);
	}

	@POST
	@Path("/{p_id}/healthprofile")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Person setHealthProfile(@PathParam("p_id") Long p_id,
			HealthProfile newHp) {

		Person dbPerson = PeopleDB.getPerson(p_id);

		if (dbPerson != null && newHp != null && newHp.getHeight() != null
				&& newHp.getWeight() != null) {

			// metto nella history il corrente perche ora lo aggiorno
			HealthProfileDB.saveHealthProfile(new HealthProfile(dbPerson
					.getPerson_id(), dbPerson.getWeight(),
					dbPerson.getHeight(), dbPerson.getLastupdate()));

			// aggiorno la persona col nuovo hp
			dbPerson.setLastupdate(new Date());
			dbPerson.setHeight(newHp.getHeight());
			dbPerson.setWeight(newHp.getWeight());

			dbPerson = PeopleDB.updatePerson(dbPerson);

			dbPerson.setHealthProfileHistory(HealthProfileDB
					.getPersonHealthProfileHistory(p_id));
		}

		return dbPerson;
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

		} else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	@DELETE
	@Path("/{p_id}/healthprofile/{hp_id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response deleteHealthProfileX(@PathParam("p_id") Long p_id,
			@PathParam("hp_id") Long hp_id) {

		Person person = PeopleDB.getPerson(p_id);
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

	/**
	 * GET
	 * 
	 * /person/birthdate?after=dd-mm-yyyy&before=dd-mm-yyyy
	 */

	@GET
	@Path("/birthdate")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ArrayList<Person> getPeopleByBirthdate(@PathParam("p_id") Long p_id,
			@QueryParam("to") String before_qp,
			@QueryParam("from") String after_qp) {

		if (after_qp != null && before_qp != null) {

			return PeopleCompareDB.searchBirthdate(Utils.parseDate(after_qp),
					Utils.parseDate(before_qp));
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

		ArrayList<Person> list = null;

		if (measure != null) {

			if (measure.equalsIgnoreCase("height")) {
				list = PeopleCompareDB.getPersonByHeight(min, max);

			} else if (measure.equalsIgnoreCase("weight")) {
				list = PeopleCompareDB.getPersonByWeight(min, max);
			}
		}

		return list;
	}

	/**
	 * GET
	 * 
	 * /person/search?firstname=name&lastname=surname
	 */

	@GET
	@Path("/search")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Person> getPeopleByMeasure(@QueryParam("q") String query) {

		if (query != null && Utils.parseQuery(query).size() > 0) {

			List<String> string = Utils.parseQuery(query);

			if (string.size() == 1) {

				String name = string.get(0);

				ArrayList<Person> list1 = PeopleCompareDB
						.searchInFirstname(name);
				ArrayList<Person> list2 = PeopleCompareDB
						.searchInLastname(name);

				return Utils.createUniqueList(list1, list2);

			} else if (string.size() == 2) {

				String firstname = string.get(0), lastname = string.get(1);

				ArrayList<Person> list1 = PeopleCompareDB
						.searchInFirstname(firstname);
				ArrayList<Person> list2 = PeopleCompareDB
						.searchInLastname(lastname);

				return Utils.createUniqueList(list1, list2);

			} else {
				// (string.size() >= 3)
				String firstname = string.get(0), lastname = string.get(1)
						+ " " + string.get(2);

				ArrayList<Person> list1 = PeopleCompareDB.searchPerson(
						firstname, lastname);

				firstname = string.get(0) + " " + string.get(1);
				lastname = string.get(2);

				ArrayList<Person> list2 = PeopleCompareDB.searchPerson(
						firstname, lastname);

				return Utils.createUniqueList(list1, list2);

			}

		} else {

			return PeopleDB.getPeople();
		}
	}
}

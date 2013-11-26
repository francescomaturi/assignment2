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
				&& person.getHeight() != null && person.getFirstname() != null
				&& person.getLastname() != null) {

			person.setPerson_id(null);
			person.setLastupdate(new Date());

			person = PersonDB.savePerson(person);

			return Response.status(Response.Status.OK).entity(person).build();

		} else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	/**
	 * 
	 * GET PUT DELETE
	 * 
	 * /person/{p_id}
	 * 
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
	public Response updatePerson(@PathParam("p_id") Long p_id, Person json) {

		Person dbPerson = PersonDB.getPerson(p_id);

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
			dbPerson = PersonDB.updatePerson(dbPerson);

			return Response.status(Response.Status.OK).entity(dbPerson).build();

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

}

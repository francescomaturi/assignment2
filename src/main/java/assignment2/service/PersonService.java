package assignment2.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import assignment2.HealthProfile;
import assignment2.Person;
import assignment2.hibernate.HibernateUtil;

@Path("/person")
public class PersonService {

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Person> getPeople() {

		return HibernateUtil.getPeople();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Person getPerson(@PathParam("id") Long id) {

		Person p = HibernateUtil.getPerson(id);
		if (p != null) {

			Set<HealthProfile> history = new HashSet<HealthProfile>();
			history.add(HibernateUtil.getCurrentHealthProfile(id));

			p.setHealthProfileHistory(history);
		}
		return p;
	}

	@GET
	@Path("/{id}/healthprofile")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Person getHealthProfile(@PathParam("id") Long id) {

		Person p = HibernateUtil.getPerson(id);
		if (p != null) {
			p.setHealthProfileHistory(HibernateUtil
					.getPersonHealthProfileHistory(id));
		}
		return p;
	}

	@GET
	@Path("/{p_id}/healthprofile/{hp_id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Person getHealthProfileX(@PathParam("p_id") Long p_id,
			@PathParam("hp_id") Long hp_id) {

		Person p = HibernateUtil.getPerson(p_id);
		if (p != null) {

			Set<HealthProfile> history = new HashSet<HealthProfile>();
			history.add(HibernateUtil.getSpecificHealthProfile(hp_id));

			p.setHealthProfileHistory(history);
		}
		return p;
	}

	@POST
	@Path("/{id}/healthprofile/{height}/{weight}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Person setHealthProfile(@PathParam("id") Long p_id,
			@PathParam("height") Double height,
			@PathParam("weight") Double weight) {

		Person p = HibernateUtil.getPerson(p_id);
		if (p != null) {

			HealthProfile current_hp = new HealthProfile(p_id, weight, height,
					new Date());
			current_hp = HibernateUtil.setHealthProfile(current_hp);

			Set<HealthProfile> history = new HashSet<HealthProfile>();
			history.add(current_hp);

			p.setHealthProfileHistory(history);

		}
		return p;
	}

	@POST
	@Path("/{firstname}/{lastname}/{birthdate}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response addPerson(@PathParam("firstname") String firstname,
			@PathParam("lastname") String lastname,
			@PathParam("birthdate") String birthdate) throws ParseException {

		if (birthdate.matches("[0-9]{2}-[0-9]{2}-[0-9]{4}")) {

			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

			Date date = df.parse(birthdate);

			Person p = HibernateUtil.addPerson(new Person(firstname, lastname,
					date));

			return Response.status(Response.Status.OK).entity(p).build();

		} else {
			// TODO errore
			String json = "{\"birthdate format\":\"dd-mm-yyyy\"}";
			return Response.status(400).entity(json).build();
		}
	}
	// @POST
	// @Path("/{firstname}/{lastname}/{height}/{weight}")
	// public Person saveAndGetPerson(@PathParam("firstname") String firstname,
	// @PathParam("lastname") String lastname,
	// @PathParam("height") Double height,
	// @PathParam("weight") Double weight) {
	//
	// Person p = new Person();
	// p.setFirstname(firstname);
	// p.setLastname(lastname);
	// p.setBirthday(new Date(System.currentTimeMillis() - 10000));
	//
	// HealthProfile hp = new HealthProfile();
	// hp.setDate(new Date());
	// hp.setHeight(height);
	// hp.setWeight(weight);
	//
	// Set<HealthProfile> history = new HashSet<HealthProfile>();
	// history.add(hp);
	// p.setHealthProfileHistory(history);
	//
	// HibernateUtil.addPerson(p);
	//
	// return p;
	// }

}

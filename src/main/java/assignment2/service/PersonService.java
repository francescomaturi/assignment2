package assignment2.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import assignment2.hibernate.HibernateUtil;
import assignment2.model.HealthProfile;
import assignment2.model.Person;

@Path("/person")
public class PersonService {

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Person> getPeople() {

		return HibernateUtil.getPeople();
	}

	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response addPerson(@QueryParam("firstname") String firstname,
			@QueryParam("lastname") String lastname,
			@QueryParam("birthdate") String birthdate) throws ParseException {

		if (firstname != null && lastname != null && birthdate != null
				&& birthdate.matches("[0-9]{2}-[0-9]{2}-[0-9]{4}")) {

			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

			Date date = df.parse(birthdate);

			Person p = HibernateUtil.addPerson(new Person(firstname, lastname,
					date));

			return Response.status(Response.Status.OK).entity(p).build();

		} else {
			String json = "{\"birthdate\":\"dd-mm-yyyy\",\"q\":\"/person?firstname=name&lastname=surname&birthdate=dd-mm-yyyy\"}";
			return Response.status(400).entity(json).build();
		}
	}

	@GET
	@Path("/{p_id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Person getPerson(@PathParam("p_id") Long p_id) {

		Person p = HibernateUtil.getPerson(p_id);
		if (p != null) {

			HealthProfile hp = HibernateUtil.getCurrentHealthProfile(p_id);

			if (hp != null) {
				Set<HealthProfile> history = new HashSet<HealthProfile>();
				history.add(hp);

				p.setHealthProfileHistory(history);
			}
		}
		return p;
	}

	@GET
	@Path("/{p_id}/healthprofile")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Person getHealthProfile(@PathParam("p_id") Long p_id,
			@QueryParam("before") String before,
			@QueryParam("after") String after) throws ParseException {

		Person p = HibernateUtil.getPerson(p_id);
		if (p != null) {

			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

			Set<HealthProfile> history;

			if (before != null && after != null
					&& before.matches("[0-9]{2}-[0-9]{2}-[0-9]{4}")
					&& after.matches("[0-9]{2}-[0-9]{2}-[0-9]{4}")) {

				history = HibernateUtil.getPersonHealthProfileBefore(p_id,
						df.parse(before), df.parse(after));

			} else if (before != null
					&& before.matches("[0-9]{2}-[0-9]{2}-[0-9]{4}")) {

				history = HibernateUtil.getPersonHealthProfileBefore(p_id,
						df.parse(before), null);

			} else if (after != null
					&& after.matches("[0-9]{2}-[0-9]{2}-[0-9]{4}")) {

				history = HibernateUtil.getPersonHealthProfileBefore(p_id,
						null, df.parse(after));

			} else {

				history = HibernateUtil.getPersonHealthProfileHistory(p_id);
			}
			p.setHealthProfileHistory(history);
		}
		return p;
	}

	@POST
	@Path("/{p_id}/healthprofile")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Person setHealthProfile(@PathParam("p_id") Long p_id,
			@QueryParam("height") Double height,
			@QueryParam("weight") Double weight) {

		Person p = HibernateUtil.getPerson(p_id);

		if (p != null && height != null && weight != null) {

			HealthProfile current_hp = new HealthProfile(p_id, weight, height,
					new Date());
			current_hp = HibernateUtil.setHealthProfile(current_hp);

			Set<HealthProfile> history = new HashSet<HealthProfile>();
			history.add(current_hp);

			p.setHealthProfileHistory(history);
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

			HealthProfile hp = HibernateUtil.getSpecificHealthProfile(p_id,
					hp_id);

			if (hp != null) {
				Set<HealthProfile> history = new HashSet<HealthProfile>();
				history.add(hp);

				p.setHealthProfileHistory(history);
			}
		}
		return p;
	}

	@PUT
	@Path("/{p_id}/healthprofile/{hp_id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Person updateHealthProfileX(@PathParam("p_id") Long p_id,
			@PathParam("hp_id") Long hp_id,
			@QueryParam("height") Double height,
			@QueryParam("weight") Double weight) {

		Person p = HibernateUtil.getPerson(p_id);
		if (p != null) {

			HealthProfile hp = HibernateUtil.getSpecificHealthProfile(p_id,
					hp_id);

			if (hp != null && height != null & weight != null) {
				hp.setHeight(height);
				hp.setWeight(weight);

				// TODO scommentare se è da aggiornare anche la data
				// hp.setDate(new Date());

				Set<HealthProfile> history = new HashSet<HealthProfile>();
				history.add(hp);

				p.setHealthProfileHistory(history);
			}
		}
		return p;
	}

}

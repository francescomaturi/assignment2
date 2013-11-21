package assignment2.hibernate;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import assignment2.HealthProfile;
import assignment2.Person;

public class HibernateUtil {

	private static SessionFactory sessionFactory = null;
	private static ServiceRegistry serviceRegistry = null;

	static {
		Configuration configuration = new Configuration();
		configuration.configure();

		Properties properties = configuration.getProperties();

		serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(properties).buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}

	public static Person addPerson(Person person) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			Long id = (Long) session.save(person);
			person.setPerson_id(id);

			transaction.commit();

		} catch (HibernateException e) {
			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}

		return person;
	}

	public static Person getPerson(Long p_id) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Person person = null;
		try {
			transaction = session.beginTransaction();

			person = (Person) session.get(Person.class, p_id);

			transaction.commit();

		} catch (HibernateException e) {
			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}

		return person;
	}

	@SuppressWarnings("unchecked")
	public static List<Person> getPeople() {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		List<Person> people = null;

		try {
			transaction = session.beginTransaction();

			people = (List<Person>) session.createCriteria(Person.class).list();

			transaction.commit();
		} catch (HibernateException e) {
			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}

		return people;
	}

	public static HealthProfile setHealthProfile(HealthProfile hp) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			Long id = (Long) session.save(hp);
			hp.setHealthprofile_id(id);

			transaction.commit();
		} catch (HibernateException e) {
			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}

		return hp;
	}

	public static HealthProfile getSpecificHealthProfile(Long hp_id) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		HealthProfile hp = null;
		try {
			transaction = session.beginTransaction();

			hp = (HealthProfile) session.get(HealthProfile.class, hp_id);

			transaction.commit();
		} catch (HibernateException e) {
			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}

		return hp;
	}

	public static HealthProfile getCurrentHealthProfile(Long p_id) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		HealthProfile hp = null;
		try {
			transaction = session.beginTransaction();

			hp = (HealthProfile) session.createCriteria(HealthProfile.class)
					.add(Restrictions.eq("person_id", p_id))
					.addOrder(Order.desc("date")).setMaxResults(1)
					.uniqueResult();

			transaction.commit();
		} catch (HibernateException e) {
			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}

		return hp;
	}

	@SuppressWarnings("unchecked")
	public static Set<HealthProfile> getPersonHealthProfileHistory(Long id) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Set<HealthProfile> historyHealthProfiles = null;
		try {
			transaction = session.beginTransaction();

			historyHealthProfiles = new HashSet<HealthProfile>(session
					.createCriteria(HealthProfile.class)
					.add(Restrictions.eq("person_id", id))
					.addOrder(Order.desc("date")).list());

			transaction.commit();

		} catch (HibernateException e) {
			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}

		return historyHealthProfiles;
	}
}

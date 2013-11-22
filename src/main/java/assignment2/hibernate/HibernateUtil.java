package assignment2.hibernate;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import assignment2.model.HealthProfile;
import assignment2.model.Person;

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

	public static Person savePerson(Person person) {
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

	public static Person updatePerson(Person person) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			session.update(person);
			person = (Person) session.get(Person.class, person.getPerson_id());

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

	public static HealthProfile createHealthProfile(HealthProfile hp) {
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

	public static HealthProfile updateHealthProfile(HealthProfile hp) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			session.update(hp);
			hp = (HealthProfile) session.get(HealthProfile.class,
					hp.getHealthprofile_id());

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

	public static HealthProfile getSpecificHealthProfile(Long p_id, Long hp_id) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		HealthProfile hp = null;
		try {
			transaction = session.beginTransaction();

			hp = (HealthProfile) session.createCriteria(HealthProfile.class)
					.add(Restrictions.eq("healthprofile_id", hp_id))
					.add(Restrictions.eq("person_id", p_id)).uniqueResult();

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
	public static Set<HealthProfile> getPersonHealthProfileHistory(Long p_id) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Set<HealthProfile> historyHealthProfiles = null;
		try {
			transaction = session.beginTransaction();

			historyHealthProfiles = new HashSet<HealthProfile>(session
					.createCriteria(HealthProfile.class)
					.add(Restrictions.eq("person_id", p_id))
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

	public static Set<HealthProfile> getPersonHealthProfileBefore(Long p_id,
			Date before, Date after) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Set<HealthProfile> historyHealthProfiles = null;

		try {
			transaction = session.beginTransaction();

			Criteria criteria = session.createCriteria(HealthProfile.class)
					.add(Restrictions.eq("person_id", p_id))
					.addOrder(Order.desc("date"));

			if (before != null && after != null) {
				// between two dates
				criteria.add(Restrictions.between("date", after, before));

			} else if (before != null) {
				// less than or equal the given date
				criteria.add(Restrictions.le("date", before));

			} else {
				// grater than or equal the given date
				criteria.add(Restrictions.ge("date", after));
			}

			historyHealthProfiles = new HashSet<HealthProfile>(criteria.list());

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

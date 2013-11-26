package assignment2.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import assignment2.model.Person;

public class PeopleDB {

	public static Person savePerson(Person person) {
		Session session = Hibernate.getSessionFactory().openSession();
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
		Session session = Hibernate.getSessionFactory().openSession();
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
		Session session = Hibernate.getSessionFactory().openSession();
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

	public static Person deletePerson(Long p_id) {
		Session session = Hibernate.getSessionFactory().openSession();
		Transaction transaction = null;
		Person person = null;
		try {
			transaction = session.beginTransaction();

			person = (Person) session.get(Person.class, p_id);
			session.delete(person);

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
		Session session = Hibernate.getSessionFactory().openSession();
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
}

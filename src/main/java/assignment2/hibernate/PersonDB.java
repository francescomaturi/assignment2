package assignment2.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import assignment2.model.Person;

public class PersonDB {

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

	@SuppressWarnings("unchecked")
	public static ArrayList<Person> searchBirthdate(Date from, Date to) {
		Session session = Hibernate.getSessionFactory().openSession();
		Transaction transaction = null;
		ArrayList<Person> list = null;
		try {
			transaction = session.beginTransaction();
			Criteria query = session.createCriteria(Person.class);

			if (from != null && to != null) {

				query.add(Restrictions.between("birthdate", from, to));

			} else if (to != null && from == null) {

				query.add(Restrictions.lt("birthdate", to));

			} else if (from != null && to == null) {

				query.add(Restrictions.gt("birthdate", from));
			}

			list = (ArrayList<Person>) query.list();

			transaction.commit();
		} catch (HibernateException e) {
			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Person> getPersonByWeight(Double min, Double max) {
		Session session = Hibernate.getSessionFactory().openSession();
		Transaction transaction = null;
		ArrayList<Person> list = null;
		try {
			transaction = session.beginTransaction();

			Criteria query = session.createCriteria(Person.class);

			if (max != null && min != null) {

				query.add(Restrictions.between("weight", min, max));

			} else if (max != null && min == null) {

				query.add(Restrictions.lt("weight", max));

			} else if (min != null && max == null) {

				query.add(Restrictions.gt("weight", min));
			}

			list = (ArrayList<Person>) query.list();

			transaction.commit();
		} catch (HibernateException e) {
			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Person> getPersonByHeight(Double min, Double max) {
		Session session = Hibernate.getSessionFactory().openSession();
		Transaction transaction = null;
		ArrayList<Person> list = null;
		try {
			transaction = session.beginTransaction();

			Criteria query = session.createCriteria(Person.class);

			if (max != null && min != null) {

				query.add(Restrictions.between("height", min, max));

			} else if (max != null && min == null) {

				query.add(Restrictions.lt("height", max));

			} else if (min != null && max == null) {

				query.add(Restrictions.gt("height", min));
			}

			list = (ArrayList<Person>) query.list();

			transaction.commit();
		} catch (HibernateException e) {
			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return list;
	}

}

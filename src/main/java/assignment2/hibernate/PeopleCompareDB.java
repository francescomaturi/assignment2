package assignment2.hibernate;

import java.util.ArrayList;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import assignment2.model.Person;

public class PeopleCompareDB {
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

	@SuppressWarnings("unchecked")
	public static ArrayList<Person> searchInFirstname(String firstname) {
		Session session = Hibernate.getSessionFactory().openSession();
		Transaction transaction = null;
		ArrayList<Person> list = null;
		try {
			transaction = session.beginTransaction();

			list = (ArrayList<Person>) session
					.createCriteria(Person.class)
					.add(Restrictions.ilike("firstname", firstname,
							MatchMode.ANYWHERE)).list();

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
	public static ArrayList<Person> searchInLastname(String lastname) {
		Session session = Hibernate.getSessionFactory().openSession();
		Transaction transaction = null;
		ArrayList<Person> list = null;
		try {
			transaction = session.beginTransaction();

			list = (ArrayList<Person>) session
					.createCriteria(Person.class)
					.add(Restrictions.ilike("lastname", lastname,
							MatchMode.ANYWHERE)).list();

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
	public static ArrayList<Person> searchPerson(String firstname,
			String lastname) {
		Session session = Hibernate.getSessionFactory().openSession();
		Transaction transaction = null;
		ArrayList<Person> list = null;
		try {
			transaction = session.beginTransaction();

			Criteria query = session.createCriteria(Person.class);

			query.add(Restrictions.ilike("lastname", lastname,
					MatchMode.ANYWHERE));
			query.add(Restrictions.ilike("firstname", firstname,
					MatchMode.ANYWHERE));

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

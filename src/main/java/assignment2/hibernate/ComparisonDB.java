package assignment2.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import assignment2.model.Person;

public class ComparisonDB {

	@SuppressWarnings("unchecked")
	public static ArrayList<Person> birthdate(Date from, Date to) {

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
	public static ArrayList<Person> weight(Double min, Double max) {

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
	public static ArrayList<Person> height(Double min, Double max) {

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
	public static ArrayList<Person> search(String... text) {

		Session session = Hibernate.getSessionFactory().openSession();
		Transaction transaction = null;
		ArrayList<Person> list = null;

		try {
			transaction = session.beginTransaction();

			Criteria query = session.createCriteria(Person.class);

			List<Criterion> criterias = new ArrayList<Criterion>();

			for (int i = 0; i < text.length && i < 3; i++) {
				criterias.add(Restrictions.ilike("firstname", text[i],
						MatchMode.ANYWHERE));
				criterias.add(Restrictions.ilike("lastname", text[i],
						MatchMode.ANYWHERE));
			}

			Criterion[] arr = new Criterion[criterias.size()];

			query.add(Restrictions.or(criterias.toArray(arr)));

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

package assignment2.hibernate;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import assignment2.model.HealthProfile;

public class HealthProfileDB {

	public static HealthProfile saveHealthProfile(HealthProfile hp) {
		Session session = Hibernate.getSessionFactory().openSession();
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
		Session session = Hibernate.getSessionFactory().openSession();
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

	public static void deleteHealthProfile(Long hp_id) {
		Session session = Hibernate.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			HealthProfile hp = (HealthProfile) session.get(HealthProfile.class,
					hp_id);
			session.delete(hp);

			transaction.commit();
		} catch (HibernateException e) {
			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
	}

	public static HealthProfile getSpecificHealthProfile(Long p_id, Long hp_id) {
		Session session = Hibernate.getSessionFactory().openSession();
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

	public static void deletePersonHealthProfileHistory(
			ArrayList<HealthProfile> history) {
		Session session = Hibernate.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			for (HealthProfile hp : history) {
				session.delete(hp);
			}

			transaction.commit();
		} catch (HibernateException e) {
			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Long> getHealthProfileIds(Long p_id) {
		Session session = Hibernate.getSessionFactory().openSession();
		Transaction transaction = null;
		ArrayList<Long> ids = null;
		try {
			transaction = session.beginTransaction();

			ids = (ArrayList<Long>) session
					.createQuery(
							"select healthprofile_id from HealthProfile where person_id = :p_id")
					.setParameter("p_id", p_id).list();

			transaction.commit();
		} catch (HibernateException e) {
			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return ids;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<HealthProfile> getPersonHealthProfileHistory(
			Long p_id) {
		Session session = Hibernate.getSessionFactory().openSession();
		Transaction transaction = null;
		ArrayList<HealthProfile> historyHealthProfiles = null;
		try {
			transaction = session.beginTransaction();

			historyHealthProfiles = (ArrayList<HealthProfile>) session
					.createCriteria(HealthProfile.class)
					.add(Restrictions.eq("person_id", p_id))
					.addOrder(Order.desc("date")).list();

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

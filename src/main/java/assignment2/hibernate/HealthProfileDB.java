package assignment2.hibernate;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import assignment2.model.HealthProfile;

public class HealthProfileDB {

	public static Double getProperty(String property, Long p_id) {
		Session session = Hibernate.getSessionFactory().openSession();
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

		if (property.equalsIgnoreCase("weight"))
			return hp.getWeight();
		else
			return hp.getHeight();
	}

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

	public static HealthProfile isCurrent(Long p_id, Long hp_id) {
		Session session = Hibernate.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			HealthProfile hp = (HealthProfile) session
					.createCriteria(HealthProfile.class)
					.add(Restrictions.eq("person_id", p_id))
					.addOrder(Order.desc("date")).setMaxResults(1)
					.uniqueResult();

			if (hp.getHealthprofile_id().equals(hp_id)) {
				return hp;
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

		return null;
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

	// @SuppressWarnings("unchecked")
	// public static ArrayList<HealthProfile> getPersonHealthProfileBefore(
	// Long p_id, Date before, Date after) {
	// Session session = Hibernate.getSessionFactory().openSession();
	// Transaction transaction = null;
	// ArrayList<HealthProfile> historyHealthProfiles = null;
	//
	// try {
	// transaction = session.beginTransaction();
	//
	// Criteria criteria = session.createCriteria(HealthProfile.class)
	// .add(Restrictions.eq("person_id", p_id))
	// .addOrder(Order.desc("date"));
	//
	// if (before != null && after != null) {
	// // between two dates
	// criteria.add(Restrictions.between("date", after, before));
	//
	// } else if (before != null) {
	// // less than or equal the given date
	// criteria.add(Restrictions.le("date", before));
	//
	// } else {
	// // grater than or equal the given date
	// criteria.add(Restrictions.ge("date", after));
	// }
	//
	// historyHealthProfiles = (ArrayList<HealthProfile>) criteria.list();
	//
	// transaction.commit();
	//
	// } catch (HibernateException e) {
	// // rollback transaction
	// if (transaction != null) {
	// transaction.rollback();
	// }
	// } finally {
	// session.close();
	// }
	//
	// return historyHealthProfiles;
	// }
}

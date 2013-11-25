package assignment2.hibernate;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Hibernate {

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

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}

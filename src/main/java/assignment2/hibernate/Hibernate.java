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
		// configuration.setProperty("hibernate.dialect",
		// "org.hibernate.dialect.SQLiteDialect");
		// configuration.setProperty("hibernate.connection.driver_class",
		// "org.sqlite.JDBC");
		// configuration.setProperty("hibernate.connection.url", "jdbc:sqlite:"
		// + "/database/mydb.db");
		// configuration.setProperty("hibernate.hbm2ddl.auto", "update");
		// configuration.setProperty("hibernate.show_sql", "true");
		// configuration.configure("hibernate.cfg.xml");
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

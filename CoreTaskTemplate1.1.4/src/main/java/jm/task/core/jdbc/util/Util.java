package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private SessionFactory sessionFactory;
    public SessionFactory getSessionFactory() throws HibernateException {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();

            Properties settings = new Properties();
            settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            settings.put(Environment.URL, "jdbc:mysql://localhost:3306/baseclienttest");
            settings.put(Environment.USER, "root");
            settings.put(Environment.PASS, "2332431512Kk");
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

            configuration.setProperties(settings);

            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }


    public Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/baseclienttest?autoReconnect=true&useSSL=false";
        String userName = "root";
        String password = "2332431512Kk";//Проверяем наличие JDBC драйвера для работы с БД
        Connection connection = DriverManager.getConnection(url, userName, password);
        return connection;
    }


}

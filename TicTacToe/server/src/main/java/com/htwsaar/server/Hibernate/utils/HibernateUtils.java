package com.htwsaar.server.Hibernate.utils;

import com.htwsaar.server.Hibernate.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

/**
 * Eine Utils Klasse um Hibernate Einstellungen festzulegen
 */
public class HibernateUtils {
    private static SessionFactory sessionFactory;

    /**
     * Eine Utils Methode für Hibernate um Einstellungen wie Login(user/passwort)
     * oder die URL der Datenbank zu bestimmen. Wird auch benutzt, um den HBM2DDL_AUTO Wert
     * einzustellen (create-drop für eine neue Datenbank bei jedem Start / update für eine
     * persistente Datenbank, die neue Daten einträgt
     *
     * @return Gibt eine SessionFactory mit allen Einstellungen zurück
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost/gameserver");
                settings.put(Environment.USER, "testuser");
                settings.put(Environment.PASS, "test");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "update");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}

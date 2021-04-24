package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    Util util = new Util();
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS user " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL)";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch(Exception e){
            if(transaction !=null){
                transaction.rollback();
                e.printStackTrace();
            }
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS user";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            session.close();
        } catch(Exception e){
            if(transaction !=null){
                transaction.rollback();
                e.printStackTrace();
            }
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Session session = null;
        Transaction transaction = null;
        try {
            session = util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch(Exception e){
            if(transaction !=null){
                transaction.rollback();
                e.printStackTrace();
            }
            e.printStackTrace();
        }finally{
            session.close();
        }
    }


    @Override
    public void removeUserById(long id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            User user = (User)session.load(User.class, id);
            session.delete(user);
            transaction.commit();
            session.close();
        } catch(Exception e){
            if(transaction !=null){
                transaction.rollback();
                e.printStackTrace();
            }
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = null;
        Session session = null;
        Transaction transaction = null;
        try {
            session = util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            list= session.createQuery("FROM " +User.class.getSimpleName()).list();
            transaction.commit();
            session.close();
            for (User us : list) {
                System.out.println(us.toString());
            }
        } catch(Exception e){
            if(transaction !=null){
                transaction.rollback();
                e.printStackTrace();
            }
            e.printStackTrace();
        }finally{
            session.close();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            List<User> list= session.createCriteria(User.class).list();
            for (User user : list ) {
                session.delete(user);
            }
            transaction.commit();
            session.close();
        } catch(Exception e){
            if(transaction !=null){
                transaction.rollback();
                e.printStackTrace();
            }
            e.printStackTrace();
        }finally{
            session.close();
        }
    }
}

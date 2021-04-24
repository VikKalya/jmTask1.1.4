package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Util util = new Util();
    Statement statement;

    {
        try {
            statement = util.connect().createStatement();
        } catch (Exception trow) {
            trow.printStackTrace();
        }
    }

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS user" +
                            "(Id INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL, " +
                            "Name VARCHAR(20) NOT NULL, lastName VARCHAR(20), " +
                            "age INTEGER NOT NULL )"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            statement.executeUpdate("DROP TABLE user");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            String selectSQL = "INSERT INTO user (NAME, LASTNAME, AGE) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = util.connect().prepareStatement(selectSQL);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            String selectSQL ="DELETE FROM user WHERE Id = ?";
            PreparedStatement preparedStatement = util.connect().prepareStatement(selectSQL);
            preparedStatement.setInt(1, (int) id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List <User> usersList= new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));
                usersList.add(user);
                System.out.println(user.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public void cleanUsersTable() {
        try {
            statement.executeUpdate("DELETE FROM user");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

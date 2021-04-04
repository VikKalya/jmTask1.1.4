package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.*;
public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("k", "Ivanov", (byte) 47);
        userService.saveUser("ic", "Inotev", (byte) 29);
        userService.saveUser("Ron", "Bolov", (byte) 26);
        userService.saveUser("Gun", "Tenzin", (byte) 26);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();


    }
}
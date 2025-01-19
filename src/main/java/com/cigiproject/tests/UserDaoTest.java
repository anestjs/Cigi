package main.java.com.cigiproject.tests;

import main.java.com.cigiproject.model.User;
import main.java.com.cigiproject.dao.impl.UserDaoImpl;
import main.java.com.cigiproject.model.Role;
import java.util.List;
import java.util.Optional;

public class UserDaoTest {
    public static void main(String[] args) {
        UserDaoImpl userDao = new UserDaoImpl();

        User newUser = new User(0, "Aya", "Qadry", "aya.qa@example.com", Role.Professor, "password123");
        if (userDao.save(newUser)) {
            System.out.println("User saved successfully with ID: " + newUser.getUser_id());
        } else {
            System.out.println("Failed to save user.");
        }

        Optional<User> foundUser = userDao.findByEmail("john.doe@example.com");
        foundUser.ifPresent(user -> System.out.println("User found: " + user.getFirstname() + " " + user.getLastname()));

        if (foundUser.isPresent()) {
            User user = foundUser.get();
            user.setFirstname("Jonathan");
            user.setLastname("Doe Updated");
            if (userDao.update(user)) {
                System.out.println("User updated successfully.");
            } else {
                System.out.println("Failed to update user.");
            }
        }

        List<User> professors = userDao.findByRole(Role.Professor);
        System.out.println("Professors List:");
        for (User u : professors) {
            System.out.println(" - " + u.getFirstname() + " " + u.getLastname());
        }
    }
}

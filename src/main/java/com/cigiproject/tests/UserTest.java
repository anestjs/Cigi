package main.java.com.cigiproject.tests;



import java.util.Optional;

import main.java.com.cigiproject.dao.impl.UserDaoImpl;
import main.java.com.cigiproject.model.Role;
import main.java.com.cigiproject.model.User;
import main.java.com.cigiproject.services.UserService;

public class UserTest {

    public static void main(String[] args) {

        UserDaoImpl userDao = new UserDaoImpl();


        UserService userService = new UserService(userDao);

        User user = new User(0, "Test", "Qadry", "aya.qadsd@example.com", Role.Student, "password123");
        // boolean registrationResult = userService.registerUser(user);

        // if (registrationResult) {
        //     System.out.println("User registered successfully!");
        // } else {
        //     System.out.println("User registration failed.");
        // }

        String email= "aya.qa@example.com" ;
        String passw = "password123" ; 
        Optional<User> authenticated = userService.authenticate(email, passw); 

        if (authenticated.isPresent()) {
            System.out.println("User authenticated successfully!");
        } else {
            System.out.println("User auth failed.");
        }
    }
}

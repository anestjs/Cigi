package main.java.com.cigiproject.tests;

import java.util.Optional;

import main.java.com.cigiproject.dao.ProfessorDao;
import main.java.com.cigiproject.dao.impl.ProfessorDaoImpl;
import main.java.com.cigiproject.dao.impl.UserDaoImpl;
import main.java.com.cigiproject.model.Professor;
import main.java.com.cigiproject.model.User;
import main.java.com.cigiproject.services.ProfessorService;
import main.java.com.cigiproject.services.UserService;
import main.java.com.cigiproject.model.Role;

public class ProfessorServiceTest {

    public static void main(String[] args) {
        ProfessorDao professorDao = new ProfessorDaoImpl();
        // Professor professor = professorDao.findById(5).orElseThrow(); // Fetch the professor

        // // Update user data
        // professor.getUser().setFirstname("NewFirstName");
        // professor.getUser().setLastname("NewLastName");
        // professor.getUser().setEmail("new.email@example.com");

        // Save changes
        // professorDao.update(professor);

        // Initialize services
        ProfessorService professorService = new ProfessorService();
        UserDaoImpl userDao = new UserDaoImpl();

        UserService userService = new UserService(userDao);

        // professorDao.delete(5); // Delete professor with ID 1
        // Step 1: Create and add the user
        User user = new User();
        user.setFirstname("blop");
        user.setLastname("Doess");
        user.setEmail("lol.doedv23@example.com");
        user.setPassword("password");
        user.setRole(Role.Professor);

        // Professor p = new Professor(0, user);
        professorDao.delete(7);


        // boolean isUserAdded = userService.registerUser(user);
        // if (!isUserAdded) {
        //     System.out.println("Error adding user.");
        //     return;
        // }


    }

    
}

package main.java.com.cigiproject.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import main.java.com.cigiproject.dao.impl.UserDaoImpl;
import main.java.com.cigiproject.model.User;

public class UserService {
    private UserDaoImpl userDao;

    public UserService(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    public Optional<User> authenticate(String email, String password) {
        String hashedPassword = hashPassword(password);

        return userDao.findByEmailAndPassword(email, hashedPassword);
    }

    public boolean registerUser(User user) {
        if (userDao.emailExists(user.getEmail())) {
            System.out.println("Error: Email already in use.");
            return false;
        }

        if (userDao.nameExists(user.getFirstname(), user.getLastname())) {
            System.out.println("Error: User already exists with the same name.");
            return false;
        }
        user.setPassword(hashPassword(user.getPassword()));

        return userDao.save(user);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}

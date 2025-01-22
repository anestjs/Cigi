package main.java.com.cigiproject.dao;

import java.util.List;
import java.util.Optional;

import main.java.com.cigiproject.model.Role;
import main.java.com.cigiproject.model.User;

public interface UserDao extends BaseDao<User> {
    Optional<User> findByEmail(String email);

    List<User> findByRole(Role role);
    Optional<User> findByEmailAndPassword(String email, String password);
    // boolean updateSetting(User user) ;
    boolean updateStudent(User use);
}

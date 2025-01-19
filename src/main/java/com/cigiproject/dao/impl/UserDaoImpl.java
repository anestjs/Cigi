package main.java.com.cigiproject.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import main.java.com.cigiproject.dao.UserDao;
import main.java.com.cigiproject.database.DatabaseConfig;
import main.java.com.cigiproject.model.Role;
import main.java.com.cigiproject.model.User;

public class UserDaoImpl implements UserDao {

    private Connection conn;

    @Override
    public Optional<User> findById(Integer id) {
        String req = "SELECT * FROM users WHERE user_id = ?";
        try{
            this.conn = DatabaseConfig.connexion();
            PreparedStatement pStatement = conn.prepareStatement(req) ; 
            pStatement.setInt(1, id);

            ResultSet rs = pStatement.executeQuery();
            if(rs.next()) {
                User user = new User();
                user.setUser_id(rs.getInt("user_id"));
                user.setFirstname(rs.getString("firstname"));
                user.setFirstname(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(Role.valueOf(rs.getString("role")));

                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        
        try {
            this.conn = DatabaseConfig.connexion();
            Statement stat = conn.createStatement() ; 
            ResultSet rs= stat.executeQuery(sql);
            
            while (rs.next()) {
                User user = new User();
                user.setUser_id(rs.getInt("user_id"));
                user.setFirstname(rs.getString("firstname"));
                user.setFirstname(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(Role.valueOf(rs.getString("role")));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean nameExists(String firstName, String lastName) {
        try {
            this.conn = DatabaseConfig.connexion();
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM users WHERE firstname = '" + firstName + "' AND lastname = '" + lastName + "'";
            ResultSet rs = stat.executeQuery(sql);
            return rs.next(); 
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean save(User user) {
        String sql = "INSERT INTO users (firstname, lastname, email, password, role) VALUES (?, ?, ?, ?, ?)";
    
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, user.getFirstname());
            stmt.setString(2, user.getLastname());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());  
            stmt.setString(5, user.getRole().name());
    
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user.setUser_id(generatedKeys.getInt(1));
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    

    @Override
    public boolean update(User user) {
        String sql = "UPDATE users SET firstname = ?, lastname = ?, email = ?, password = ?, role = ? WHERE user_id = ?";
        
        try {
            this.conn = DatabaseConfig.connexion();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, user.getFirstname());
            stmt.setString(2, user.getLastname());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getRole().name());
            stmt.setInt(6, user.getUser_id());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        
        try {
            this.conn = DatabaseConfig.connexion();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try  {
            this.conn = DatabaseConfig.connexion();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setUser_id(rs.getInt("user_id"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(Role.valueOf(rs.getString("role")));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<User> findByRole(Role role) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = ?";
        
        try  {
            this.conn = DatabaseConfig.connexion();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, role.name());
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                User user = new User();
                user.setUser_id(rs.getInt("user_id"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(Role.valueOf(rs.getString("role")));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


    @Override
    public Optional<User> findByEmailAndPassword(String email, String hashedPassword) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            this.conn = DatabaseConfig.connexion();
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setString(1, email);  
            ResultSet rs = pStatement.executeQuery();
            
            if (rs.next()) {
                String storedHashedPassword = rs.getString("password");  


                if (storedHashedPassword.equals(hashedPassword)) {
                    User user = new User();
                    user.setUser_id(rs.getInt("user_id"));
                    user.setFirstname(rs.getString("firstname"));
                    user.setLastname(rs.getString("lastname"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));  
                    user.setRole(Role.valueOf(rs.getString("role")));

                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


}




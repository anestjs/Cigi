package main.java.com.cigiproject.dao.impl;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import main.java.com.cigiproject.dao.StudentDao;
import main.java.com.cigiproject.database.DatabaseConfig;
import main.java.com.cigiproject.model.*;

public class StudentDaoImpl implements StudentDao {

    @Override
    public Optional<Student> findById(Integer id) {
        String sql = "SELECT s.*, u.* FROM students s " +
                    "JOIN users u ON s.user_id = u.user_id " +
                    "WHERE s.student_id = ?";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToStudent(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT s.*, u.* FROM students s " +
                    "JOIN users u ON s.user_id = u.user_id";
        try (Connection conn = DatabaseConfig.connexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                students.add(mapRowToStudent(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public boolean save(Student student) {
        String userSql = "INSERT INTO users (firstname, lastname, email, password, role) VALUES (?, ?, ?, ?, ?)";
        String studentSql = "INSERT INTO students (user_id, cne) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConfig.connexion()) {
            conn.setAutoCommit(false);
            
            try (PreparedStatement userStmt = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {
                User user = student.getUser();
                userStmt.setString(1, user.getFirstname());
                userStmt.setString(2, user.getLastname());
                userStmt.setString(3, user.getEmail());
                userStmt.setString(4, user.getPassword());
                userStmt.setString(5, user.getRole().name());
                
                int affectedRows = userStmt.executeUpdate();
                
                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                try (ResultSet generatedKeys = userStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);
                        
                        try (PreparedStatement studentStmt = conn.prepareStatement(studentSql)) {
                            studentStmt.setInt(1, userId);
                            studentStmt.setInt(2, student.getCne());
                            studentStmt.executeUpdate();
                        }
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
                
                conn.commit();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Student student) {
        String userSql = "UPDATE users SET firstname = ?, lastname = ?, email = ? WHERE user_id = ?";
        String studentSql = "UPDATE students SET cne = ? WHERE user_id = ?";
        
        try (Connection conn = DatabaseConfig.connexion()) {
            conn.setAutoCommit(false);
            
            try (PreparedStatement userStmt = conn.prepareStatement(userSql);
                 PreparedStatement studentStmt = conn.prepareStatement(studentSql)) {
                
                User user = student.getUser();
                userStmt.setString(1, user.getFirstname());
                userStmt.setString(2, user.getLastname());
                userStmt.setString(3, user.getEmail());
                userStmt.setInt(4, user.getUser_id());
                userStmt.executeUpdate();
                
                studentStmt.setInt(1, student.getCne());
                studentStmt.setInt(2, user.getUser_id());
                studentStmt.executeUpdate();
                
                conn.commit();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM users WHERE user_id = (SELECT user_id FROM students WHERE student_id = ?)";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Student findByCne(Integer cne) {
        String sql = "SELECT s.*, u.* FROM students s " +
                    "JOIN users u ON s.user_id = u.user_id " +
                    "WHERE s.cne = ?";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cne);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRowToStudent(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Student> findByClassId(Integer classId) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT DISTINCT s.*, u.* FROM students s " +
                    "JOIN users u ON s.user_id = u.user_id " +
                    "JOIN enrollments e ON s.cne = e.cne " +
                    "WHERE e.class_id = ?";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, classId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                students.add(mapRowToStudent(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public int getStudentCountByClass(Integer classId) {
        String sql = "SELECT COUNT(DISTINCT e.cne) FROM enrollments e WHERE e.class_id = ?";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, classId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Student mapRowToStudent(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUser_id(rs.getInt("user_id"));
        user.setFirstname(rs.getString("firstname"));
        user.setLastname(rs.getString("lastname"));
        user.setEmail(rs.getString("email"));
        user.setRole(Role.valueOf(rs.getString("role")));

        Student student = new Student();
        // student.setCne(rs.getInt("c"));
        student.setCne(rs.getInt("cne"));
        student.setUser(user);

        return student;
    }
}
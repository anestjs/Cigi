package main.java.com.cigiproject.dao.impl;

import main.java.com.cigiproject.dao.ClassDao;

 
import main.java.com.cigiproject.database.DatabaseConfig;
import main.java.com.cigiproject.model.Class;
import main.java.com.cigiproject.model.Module;
import main.java.com.cigiproject.model.Role;
import main.java.com.cigiproject.model.Student;
 import main.java.com.cigiproject.model.User;
import main.java.com.cigiproject.model.Year;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClassDaoImpl implements ClassDao {

    @Override
    public Optional<Class> findById(Integer id) {
        String sql = "SELECT * FROM classes WHERE class_id = ?";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToClass(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Class> findAll() { 
        List<Class> classes = new ArrayList<>();
        String sql = "SELECT * FROM classes";
        try (Connection conn = DatabaseConfig.connexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                classes.add(mapRowToClass(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classes;
    }

    @Override
public boolean save(Class entity) {
    String sql = "INSERT INTO classes (name, student_count, year) VALUES (?, ?, ?)";
    try (Connection conn = DatabaseConfig.connexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, entity.getName());
        stmt.setInt(2, entity.getStudent_count());
        stmt.setString(3, entity.getYear().name()); // Save the year as a string
        int rowsInserted = stmt.executeUpdate();
        return rowsInserted > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    @Override
    public boolean update(Class entity) {
        String sql = "UPDATE classes SET name = ?, student_count = ? WHERE class_id = ?";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, entity.getName());
            stmt.setInt(2, entity.getStudent_count());
            stmt.setInt(3, entity.getClass_id());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM classes WHERE class_id = ?";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

   
    @Override
    public List<Student> getEnrolledStudents(Integer classId) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT s.cne, u.user_id, u.firstname, u.lastname, u.email, u.role, e.year " +
                    "FROM enrollments e " +
                    "JOIN students s ON e.cne = s.cne " +
                    "JOIN users u ON s.user_id = u.user_id " +
                    "WHERE e.class_id = ?";

        try (Connection conn = DatabaseConfig.connexion();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, classId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                // Create User object
                User user = new User();
                user.setUser_id(rs.getInt("user_id"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                user.setRole(Role.valueOf(rs.getString("role"))); // Assuming Role is an enum

                // Create Student object
                Student student = new Student();
                student.setCne(rs.getInt("cne"));
                student.setUser(user);
                
                String yearStr = rs.getString("year");
                if (yearStr != null) {
                    student.setYear(Year.valueOf(yearStr)); // Convert safely
                }

                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }


    @Override
    public List<Module> getClassModules(Integer classId) {
        List<Module> modules = new ArrayList<>();
        String sql = "SELECT * FROM modules WHERE class_id = ?";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, classId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Module module = new Module();
                module.setModule_id(rs.getInt("module_id"));
                module.setName(rs.getString("name"));
                modules.add(module);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modules;
    }

    @Override
    public boolean isClassFull(Integer classId) {
        String sql = "SELECT COUNT(*) AS student_count FROM students WHERE class_id = ?";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, classId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int studentCount = rs.getInt("student_count");
                Optional<Class> classOptional = findById(classId);
                if (classOptional.isPresent()) {
                    return studentCount >= classOptional.get().getStudent_count();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

   
    private Class mapRowToClass(ResultSet rs) throws SQLException {
        Class classEntity = new Class();
        classEntity.setClass_id(rs.getInt("class_id"));
        classEntity.setStudent_count(rs.getInt("student_count"));
        
        // Retrieve the year from the database and set it in the Class object
        String yearStr = rs.getString("year"); // Assuming 'year' is stored as a string in the database
        if (yearStr != null) {
            classEntity.setYear(Year.valueOf(yearStr)); // Convert the string to the Year enum
        }
        
        // Set the name dynamically based on the year
        classEntity.setName(); // This will generate the name as "CIGI" + year
        return classEntity;
    }
}
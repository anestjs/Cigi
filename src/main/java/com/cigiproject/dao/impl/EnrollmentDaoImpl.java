package main.java.com.cigiproject.dao.impl;

import main.java.com.cigiproject.dao.EnrollmentDao;
import main.java.com.cigiproject.database.DatabaseConfig;
import main.java.com.cigiproject.model.Class;
import main.java.com.cigiproject.model.Enrollment;
import main.java.com.cigiproject.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnrollmentDaoImpl implements EnrollmentDao {

    @Override
    public boolean enrollStudent(Integer studentCne, Integer classId) {
        String query = "INSERT INTO enrollments (student_cne, class_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentCne);
            stmt.setInt(2, classId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean unenrollStudent(Integer studentCne, Integer classId) {
        String query = "DELETE FROM enrollments WHERE student_cne = ? AND class_id = ?";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentCne);
            stmt.setInt(2, classId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Class> getStudentClasses(Integer studentCne) {
        List<Class> classes = new ArrayList<>();
        String query = "SELECT c.* FROM classes c JOIN enrollments e ON c.id = e.class_id WHERE e.student_cne = ?";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentCne);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Class c = new Class();
                c.setClass_id(rs.getInt("id"));
                classes.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classes;
    }

    @Override
    public List<Student> getClassStudents(Integer classId) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT s.* FROM students s JOIN enrollments e ON s.cne = e.student_cne WHERE e.class_id = ?";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, classId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Student s = new Student();
                s.setCne(rs.getInt("cne"));
                students.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public Optional<Enrollment> findById(Integer id) {
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<Enrollment> findAll() {
        return new ArrayList<>();
    }

    @Override
    public boolean save(Enrollment entity) {
        return true;
    }

    @Override
    public boolean update(Enrollment entity) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}

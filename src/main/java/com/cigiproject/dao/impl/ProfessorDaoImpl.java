package main.java.com.cigiproject.dao.impl;

import main.java.com.cigiproject.dao.ProfessorDao;
import main.java.com.cigiproject.dao.UserDao;
import main.java.com.cigiproject.database.DatabaseConfig;

import java.util.List;
import java.util.Optional;

import java.sql.*;
import java.util.ArrayList;

 
import main.java.com.cigiproject.model.*;
import main.java.com.cigiproject.model.Class;
import main.java.com.cigiproject.model.Module;
 

public class ProfessorDaoImpl implements ProfessorDao {

    @Override
    public Optional<Professor> findById(Integer id) {
        String sql = "SELECT p.*, u.* FROM professors p JOIN users u ON p.user_id = u.user_id WHERE p.professor_id = ?";
        try (Connection conn = DatabaseConfig.connexion();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Professor professor = new Professor();
                professor.setProfessor_id(rs.getInt("professor_id"));

                // Fetch and set the associated User
                User user = new User();
                user.setUser_id(rs.getInt("user_id"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                user.setRole(Role.valueOf(rs.getString("role")));
                user.setPassword(rs.getString("password")); // Be cautious with passwords in real applications
                professor.setUser(user);

                return Optional.of(professor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Professor> findAll() {
        List<Professor> professors = new ArrayList<>();
        String sql = "SELECT p.*, u.* FROM professors p JOIN users u ON p.user_id = u.user_id";
        try (Connection conn = DatabaseConfig.connexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Professor professor = new Professor();
                professor.setProfessor_id(rs.getInt("professor_id"));

                // Fetch and set the associated User
                User user = new User();
                user.setUser_id(rs.getInt("user_id"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                user.setRole(Role.valueOf(rs.getString("role")));
                user.setPassword(rs.getString("password")); // Be cautious with passwords in real applications
                professor.setUser(user);

                professors.add(professor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return professors;
    }

    @Override
    public boolean save(Professor professor) {
        // First, save the User
        UserDao userDao = new UserDaoImpl(); // Assuming you have a UserDaoImpl class
        boolean isUserSaved = userDao.save(professor.getUser());

        if (!isUserSaved) {
            return false; // If the User couldn't be saved, return false
        }

        // Now, save the Professor with the generated user_id
        String sql = "INSERT INTO professors (user_id) VALUES (?)";
        try (Connection conn = DatabaseConfig.connexion();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, professor.getUser().getUser_id()); // Use the generated user_id
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean update(Professor professor) {
        Connection conn = null;
        try {
            conn = DatabaseConfig.connexion();
            conn.setAutoCommit(false); // Start transaction

            // Update the   User data using UserDao
            UserDao userDao = new UserDaoImpl(); // Assuming UserDaoImpl exists
            boolean isUserUpdated = userDao.update(professor.getUser());

            if (!isUserUpdated) {
                conn.rollback(); // Rollback if User couldn't be updated
                return false;
            }

            // Commit the transaction if everything succeeds
            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback on exception
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Reset auto-commit
                    conn.close(); // Close the connection
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean delete(Integer professorId) {
        Connection conn = null;
        try {
            conn = DatabaseConfig.connexion();
            conn.setAutoCommit(false); // Start transaction

            // First, get the user_id of the professor
            String getUserSql = "SELECT user_id FROM professors WHERE professor_id = ?";
            int userId = -1;
            try (PreparedStatement getUserStmt = conn.prepareStatement(getUserSql)) {
                getUserStmt.setInt(1, professorId);
                ResultSet rs = getUserStmt.executeQuery();
                if (rs.next()) {
                    userId = rs.getInt("user_id");
                } else {
                    conn.rollback(); // Rollback if professor doesn't exist
                    return false;
                }
            }

            // Now, delete the Professor
            String professorSql = "DELETE FROM professors WHERE professor_id = ?";
            try (PreparedStatement professorStmt = conn.prepareStatement(professorSql)) {
                professorStmt.setInt(1, professorId);
                int professorRowsDeleted = professorStmt.executeUpdate();
                if (professorRowsDeleted == 0) {
                    conn.rollback(); // Rollback if Professor couldn't be deleted
                    return false;
                }
            }

            // Finally, delete the User using UserDao
            UserDao userDao = new UserDaoImpl(); // Assuming UserDaoImpl exists
            boolean isUserDeleted = userDao.delete(userId);

            if (isUserDeleted) {
                conn.commit(); // Commit the transaction
                return true;
            } else {
                conn.rollback(); // Rollback if User couldn't be deleted
                return false;
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback on exception
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Reset auto-commit
                    conn.close(); // Close the connection
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<Module> getAssignedModules(Integer professorId) {
        List<Module> modules = new ArrayList<>();
        String sql = "SELECT * FROM modules WHERE professor_id = ?";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, professorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Module module = new Module();
                module.setModule_id(rs.getInt("module_id"));
                module.setName(rs.getString("name"));
                module.setSemester(Semester.valueOf(rs.getString("semester")));
                module.setYear(Year.valueOf(rs.getString("year")));
                modules.add(module);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modules;
    }

    @Override
    public Class getClassForModule(Integer moduleId) {
        String sql = "SELECT c.* FROM classes c JOIN modules m ON c.class_id = m.class_id WHERE m.module_id = ?";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, moduleId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Class classEntity = new Class();
                classEntity.setClass_id(rs.getInt("class_id"));
                classEntity.setYear(Year.valueOf(rs.getString("year")));
                classEntity.setStudent_count(rs.getInt("student_count"));
                return classEntity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Student> getStudentsInClass(Integer classId) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT s.* FROM students s JOIN enrollments e ON s.cne = e.cne WHERE e.class_id = ?";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, classId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setCne(rs.getInt("cne"));
                student.setYear(Year.valueOf(rs.getString("year")));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public void assignGradeToStudent(Integer studentCne, Integer moduleId, String grade) {
        String sql = "INSERT INTO grades (cne, module_id, grade) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentCne);
            stmt.setInt(2, moduleId);
            stmt.setString(3, grade);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateGradeForStudent(Integer studentCne, Integer moduleId, String newGrade) {
        String sql = "UPDATE grades SET grade = ? WHERE cne = ? AND module_id = ?";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newGrade);
            stmt.setInt(2, studentCne);
            stmt.setInt(3, moduleId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Grade> getGradesForModule(Integer moduleId) {
        List<Grade> grades = new ArrayList<>();
        String sql = "SELECT * FROM grades WHERE module_id = ?";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, moduleId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Grade grade = new Grade();
                grade.setGradeId(rs.getInt("grade_id"));
                grade.setGrade(rs.getString("grade"));
                grades.add(grade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }

    @Override
    public Grade getGradeForStudentInModule(Integer studentCne, Integer moduleId) {
        String sql = "SELECT * FROM grades WHERE cne = ? AND module_id = ?";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentCne);
            stmt.setInt(2, moduleId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Grade grade = new Grade();
                grade.setGradeId(rs.getInt("grade_id"));
                grade.setGrade(rs.getString("grade"));
                return grade;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
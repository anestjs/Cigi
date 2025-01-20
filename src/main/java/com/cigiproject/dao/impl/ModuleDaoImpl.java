package main.java.com.cigiproject.dao.impl;

import main.java.com.cigiproject.dao.ClassDao;
import main.java.com.cigiproject.dao.ModuleDao;
import main.java.com.cigiproject.dao.ProfessorDao;
import main.java.com.cigiproject.database.DatabaseConfig;
import main.java.com.cigiproject.model.*;
import main.java.com.cigiproject.model.Class;
import main.java.com.cigiproject.model.Module;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ModuleDaoImpl implements ModuleDao {

    private ProfessorDao professorDao; // Assuming you have a ProfessorDao
    private ClassDao classDao; // Assuming you have a ClassDao

    public ModuleDaoImpl() {
        this.professorDao = new ProfessorDaoImpl(); // Initialize ProfessorDao
        this.classDao = new ClassDaoImpl(); // Initialize ClassDao
    }

    @Override
    public List<Module> getModulesBySemester(Semester semester) {
        List<Module> modules = new ArrayList<>();
        String sql = "SELECT * FROM modules WHERE semester = ?";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, semester.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Module module = mapRowToModule(rs);
                modules.add(module);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modules;
    }

    @Override
    public List<Module> getModulesByYear(Year year) {
        List<Module> modules = new ArrayList<>();
        String sql = "SELECT * FROM modules WHERE year = ?";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, year.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Module module = mapRowToModule(rs);
                modules.add(module);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modules;
    }

    @Override
    public boolean assignModuleToClass(Integer moduleId, Integer classId) {
        String sql = "UPDATE modules SET class_id = ? WHERE module_id = ?";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, classId);
            stmt.setInt(2, moduleId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeModuleFromClass(Integer moduleId, Integer classId) {
        String sql = "UPDATE modules SET class_id = NULL WHERE module_id = ? AND class_id = ?";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, moduleId);
            stmt.setInt(2, classId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Module getModuleForClass(Integer classId) {
        String sql = "SELECT * FROM modules WHERE class_id = ?";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, classId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRowToModule(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Module> getUnassignedModules() {
        List<Module> modules = new ArrayList<>();
        String sql = "SELECT * FROM modules WHERE class_id IS NULL";
        try (Connection conn = DatabaseConfig.connexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Module module = mapRowToModule(rs);
                modules.add(module);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modules;
    }

    @Override
    public Optional<Module> findById(Integer id) {
        String sql = "SELECT * FROM modules WHERE module_id = ?";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToModule(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Module> findAll() {
        List<Module> modules = new ArrayList<>();
        String sql = "SELECT * FROM modules";
        try (Connection conn = DatabaseConfig.connexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Module module = mapRowToModule(rs);
                modules.add(module);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modules;
    }

    @Override
    public boolean save(Module entity) {
        String sql = "INSERT INTO modules (name, semester, year, professor_id, class_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getSemester().name());
            stmt.setString(3, entity.getYear().name());
            stmt.setInt(4, entity.getProfessor().getProfessor_id());
            stmt.setInt(5, entity.getClassEntity().getClass_id());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Module entity) {
        String sql = "UPDATE modules SET name = ?, semester = ?, year = ?, professor_id = ?, class_id = ? WHERE module_id = ?";
        try (Connection conn = DatabaseConfig.connexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getSemester().name());
            stmt.setString(3, entity.getYear().name());
            stmt.setInt(4, entity.getProfessor().getProfessor_id());
            stmt.setInt(5, entity.getClassEntity().getClass_id());
            stmt.setInt(6, entity.getModule_id());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM modules WHERE module_id = ?";
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

    private Module mapRowToModule(ResultSet rs) throws SQLException {
        Module module = new Module();
        module.setModule_id(rs.getInt("module_id"));
        module.setName(rs.getString("name"));
        module.setSemester(Semester.valueOf(rs.getString("semester")));
        module.setYear(Year.valueOf(rs.getString("year")));

        // Fetch and set the Professor object
        int professorId = rs.getInt("professor_id");
        Optional<Professor> professor = professorDao.findById(professorId);
        professor.ifPresent(module::setProfessor);

        // Fetch and set the Class object
        int classId = rs.getInt("class_id");
        Optional<Class> classEntity = classDao.findById(classId);
        classEntity.ifPresent(module::setClassEntity);

        return module;
    }
}
package main.java.com.cigiproject.dao;

import java.util.List;

import main.java.com.cigiproject.model.Class;
import main.java.com.cigiproject.model.Module;
import main.java.com.cigiproject.model.*;

public interface AdminDao extends BaseDao<User> {

    // Professor Management
    void addProfessor(Professor professor);
    void updateProfessor(Professor professor);
    void deleteProfessor(Integer professorId);
    Professor getProfessorById(Integer professorId);
    List<Professor> getAllProfessors();

    List<Module> getProfessorModules(Integer professorId);
    Professor getProfessorByModuleId(Integer moduleId);

    // Student Management
    void addStudent(Student student);
    void updateStudent(Student student);
    void deleteStudent(Integer studentCne);
    Student getStudentByCne(Integer studentCne);
    List<Student> getAllStudents();
    List<Student> getStudentsByYear(Year year); 
 
    // Class Management
    void createClass(Class newClass);
    void updateClass(Class updatedClass);
    void deleteClass(Integer classId);
    Class getClassById(Integer classId);
    List<Class> getAllClasses();
    List<Class> getClassesByYear(Year year); 
    void updateClassYear(Integer classId, Year newYear); 

    // Module Management
    void addModule(Module module);
    void updateModule(Module module);
    void deleteModule(Integer moduleId);
    Module getModuleById(Integer moduleId);
    List<Module> getAllModules();
    
    List<Module> getModulesBySemester(Semester semester); 
    List<Module> getModulesByYear(Year year); 
    boolean assignModuleToClass(Integer moduleId, Integer classId);
    boolean removeModuleFromClass(Integer moduleId, Integer classId);
    List<Module> getModulesForClass(Integer classId);

    // Enrollment Management
    void enrollStudentInClass(Integer studentCne, Integer classId, Year year); 
    void unenrollStudentFromClass(Integer studentCne, Integer classId); 
    List<Class> getClassesForStudent(Integer studentCne);
    List<Student> getStudentsForClass(Integer classId);

    // Professor-Module Assignment
    void assignProfessorToModule(Integer professorId, Integer moduleId); 
    void unassignProfessorFromModule(Integer professorId, Integer moduleId); 
}
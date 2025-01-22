package main.java.com.cigiproject.dao;

import java.util.List;
import java.util.Optional;

import main.java.com.cigiproject.model.*;
import main.java.com.cigiproject.model.Class;
import main.java.com.cigiproject.model.Module;


public interface ProfessorDao extends BaseDao<Professor> {

    // Professor getProfessorByUserId(Integer userId);

    List<Module> getAssignedModules(Integer professorId);

    Class getClassForModule(Integer moduleId);

    List<Student> getStudentsInClass(Integer classId);

    void assignGradeToStudent(Integer studentCne, Integer moduleId, String grade);

    void updateGradeForStudent(Integer studentCne, Integer moduleId, String newGrade);

    List<Grade> getGradesForModule(Integer moduleId);

    Grade getGradeForStudentInModule(Integer studentCne, Integer moduleId);
    



}
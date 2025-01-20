package main.java.com.cigiproject.dao;

import java.util.List;

import main.java.com.cigiproject.model.Student;
import main.java.com.cigiproject.model.Class;
import main.java.com.cigiproject.model.Module;

public interface ClassDao extends BaseDao<Class> {

    List<Student> getEnrolledStudents(Integer classId);
    List<main.java.com.cigiproject.model.Module> getClassModules(Integer classId);
    boolean isClassFull(Integer classId);
}

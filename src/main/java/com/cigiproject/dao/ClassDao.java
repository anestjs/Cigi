package main.java.com.cigiproject.dao;

import java.util.List;

import main.java.com.cigiproject.model.Student;
import main.java.com.cigiproject.model.Class;

public interface ClassDao extends BaseDao<Class> {

    List<Student> getEnrolledStudents(Integer classId);
    List<Module> getClassModules(Integer classId);
    boolean isClassFull(Integer classId);
}

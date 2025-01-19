package main.java.com.cigiproject.dao;

import java.util.List;

import main.java.com.cigiproject.model.Enrollment;
import main.java.com.cigiproject.model.Student;
import main.java.com.cigiproject.model.Class;

public interface EnrollmentDao extends BaseDao<Enrollment> {

    boolean enrollStudent(Integer studentCne, Integer classId);
    boolean unenrollStudent(Integer studentCne, Integer classId);

    List<Class> getStudentClasses(Integer studentCne);
    List<Student> getClassStudents(Integer classId);

}
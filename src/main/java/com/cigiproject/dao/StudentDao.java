package main.java.com.cigiproject.dao;

import java.util.List;

import main.java.com.cigiproject.model.Student;

public interface StudentDao extends BaseDao<Student> {

    Student findByCne(Integer cne);

    List<Student> findByClassId(Integer classId);

    int getStudentCountByClass(Integer classId);
}
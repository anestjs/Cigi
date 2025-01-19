package main.java.com.cigiproject.dao;

import java.util.List;
import main.java.com.cigiproject.model.Module;

public interface ModuleDao extends BaseDao<Module> {

    List<Module> findByProfessorId(Integer professorId);
    List<Module> findByClassId(Integer classId);
    List<Module> findBySemester(String semester);
    Module findByProfessorAndSemester(Integer professorId, String semester);

}
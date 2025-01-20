package main.java.com.cigiproject.dao;

import java.util.List;
import main.java.com.cigiproject.model.Module;
import main.java.com.cigiproject.model.Semester;
import main.java.com.cigiproject.model.Year;


public interface ModuleDao extends BaseDao<Module> {

    List<Module> getModulesBySemester(Semester semester);

    List<Module> getModulesByYear(Year year);

    boolean assignModuleToClass(Integer moduleId, Integer classId);

    boolean removeModuleFromClass(Integer moduleId, Integer classId);

    Module getModuleForClass(Integer classId);

    List<Module> getUnassignedModules();
    
}
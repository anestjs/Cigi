package main.java.com.cigiproject.services;

import main.java.com.cigiproject.dao.ProfessorDao;
import main.java.com.cigiproject.dao.impl.ProfessorDaoImpl;
import main.java.com.cigiproject.model.Professor;

import java.util.List;
import java.util.Optional;

public class ProfessorService {

    private ProfessorDao professorDao;

    public ProfessorService() {
        this.professorDao = new ProfessorDaoImpl();
    }

    public Optional<Professor> getProfessorById(Integer professorId) {
        return professorDao.findById(professorId);
    }

    public List<Professor> getAllProfessors() {
        return professorDao.findAll();
    }

    public boolean addProfessor(Professor professor) {
        if (professor != null && professor.getUser() != null) {
            return professorDao.save(professor);
        }
        return false;
    }

    public boolean updateProfessor(Professor professor) {
        if (professor != null && professor.getProfessor_id() > 0) {
            return professorDao.update(professor);
        }
        return false;
    }

    public boolean deleteProfessor(Integer professorId) {
        return professorDao.delete(professorId);
    }


}

package main.java.com.cigiproject.model;

import javax.persistence.*;

@Entity
@Table(name = "grades")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private Integer gradeId;

    @ManyToOne
    @JoinColumn(name = "cne")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;

    @Column(name = "grade", length = 10)
    private String grade;

    public Grade(Integer gradeId, Student student, Module module, String grade) {
        this.gradeId = gradeId;
        this.student = student;
        this.module = module;
        this.grade = grade;
    }

    public Grade() {
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
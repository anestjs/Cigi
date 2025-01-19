package main.java.com.cigiproject.model;

import javax.persistence.*;

@Entity
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Integer enrollment_id;

    @ManyToOne
    @JoinColumn(name = "cne")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class classEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "year")
    private Year year;

    public Enrollment(Integer enrollment_id, Student student, Class classEntity, Year year) {
        this.enrollment_id = enrollment_id;
        this.student = student;
        this.classEntity = classEntity;
        this.year = year;
    }

    public Enrollment() {
    }

    public Integer getEnrollment_id() {
        return enrollment_id;
    }

    public void setEnrollment_id(Integer enrollment_id) {
        this.enrollment_id = enrollment_id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Class getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(Class classEntity) {
        this.classEntity = classEntity;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

}

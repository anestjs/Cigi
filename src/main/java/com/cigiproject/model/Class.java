package main.java.com.cigiproject.model;

import javax.persistence.*;

@Entity
@Table(name = "classes")

public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Integer class_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "year")
    private Year year;

    @Column(name = "student_count")
    private Integer studentCount;

    public Class(Integer class_id, Year year, Integer studentCount) {
        this.class_id = class_id;
        this.year = year;
        this.studentCount = studentCount;
    }

    public Class() {
    }

    public Integer getClass_id() {
        return class_id;
    }

    public void setClass_id(Integer class_id) {
        this.class_id = class_id;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Integer getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }
}
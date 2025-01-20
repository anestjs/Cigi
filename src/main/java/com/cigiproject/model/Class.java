package main.java.com.cigiproject.model;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "classes")
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Integer class_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester")
    private Semester semester;

    @Enumerated(EnumType.STRING)
    @Column(name = "year")
    private Year year;

    @Column(name = "student_count")
    private Integer student_count;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "classEntity", fetch = FetchType.LAZY)
    private Module module; // One-to-one relationship

    @OneToMany(mappedBy = "classEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Module> modules;

    // Constructor
    public Class() {
    }

    public Class(Semester semester, Integer student_count) {
        this.semester = semester;
        this.year = determineYearFromSemester(semester); // Set year based on semester
        this.student_count = student_count;
        this.setName(); // Set the name dynamically
    }

    // Getter for name (dynamically generated)
    public String getName() {
        return "CIGI" + (year != null ? year.toString() : "");
    }
    public void setName() {
        this.name= "CIGI" + (year != null ? year.toString() : "");
    }
    
    @Override
    public String toString() {
        return "CIGI" + (year != null ? year.toString() : ""); // Include the year in the string representation
    }

    // Method to determine year based on semester
    private Year determineYearFromSemester(Semester semester) {
        switch (semester) {
            case S1:
            case S2:
                return Year._1;
            case S3:
            case S4:
                return Year._2;
            case S5:
            case S6:
                return Year._3;
            default:
                throw new IllegalArgumentException("Invalid semester: " + semester);
        }
    }

    // Getters and setters
    public Integer getClass_id() {
        return class_id;
    }

    public void setClass_id(Integer class_id) {
        this.class_id = class_id;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
        this.year = determineYearFromSemester(semester); // Update year when semester changes
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Integer getStudent_count() {
        return student_count;
    }

    public void setStudent_count(Integer student_count) {
        this.student_count = student_count;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
}
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
    @Column(name = "year")
    private Year year;

    @Column(name = "student_count")
    private Integer student_count;

    @OneToMany(mappedBy = "classEntity", fetch = FetchType.LAZY)
    private Module module; // One-to-one relationship

    public String getName() {
        return "CIGI" + (this.year != null ? this.year.toString() : "");
    }

    @Override
    public String toString() {
        return getName(); // Example: "Mathematics 101"
    }


    @OneToMany(mappedBy = "classEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Module> modules;  


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


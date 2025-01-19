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
}

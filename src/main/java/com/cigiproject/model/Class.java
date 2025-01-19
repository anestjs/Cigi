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
}
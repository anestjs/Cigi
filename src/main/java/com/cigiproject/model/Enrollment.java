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

}

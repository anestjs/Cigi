package main.java.com.cigiproject.model;

import javax.persistence.*;

@Entity
@Table(name = "professors")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professor_id")
    private Integer professor_id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    
}

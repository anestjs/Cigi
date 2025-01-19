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

    public Professor(Integer professor_id, User user) {
        this.professor_id = professor_id;
        this.user = user;
    }
    
    public Professor() {
    }

    public Integer getProfessor_id() {
        return professor_id;
    }

    public void setProfessor_id(Integer professor_id) {
        this.professor_id = professor_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}

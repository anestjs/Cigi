package main.java.com.cigiproject.model;

import javax.persistence.*;

@Entity
@Table(name = "students")

public class Student {
    @Id
    @Column(name = "cne")
    private Integer cne;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "year")
    private Year year;

    public Student(Integer cne, User user, Year year) {
        this.cne = cne;
        this.user = user;
        this.year = year;
    }

    public Student() {
    }

    public Integer getCne() {
        return cne;
    }

    public User getUser() {
        return user;
    }

    public Year getYear() {
        return year;
    }

    public void setCne(Integer cne) {
        this.cne = cne;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setYear(Year year) {
        this.year = year;
    }
}
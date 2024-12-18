package pl.edu.lab4.entity;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "ratings")
public class Rating implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int value;
    private String comment;
    private LocalDate ratingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelter_id")
    private AnimalShelter shelter;

    public Rating() {
    }

    public Rating(int value, String comment, LocalDate ratingDate) {
        this.value = value;
        this.comment = comment;
        this.ratingDate = ratingDate;
    }

    public void setShelter(AnimalShelter shelter) {
        this.shelter = shelter;
    }
}
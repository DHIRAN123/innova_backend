package com.example.Innovatiview.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String optionChosen;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public TaskEntity() {
    }

    public TaskEntity(String description, UserEntity user) {
        this.description = description;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public UserEntity getUser() {
        return user;
    }

    public String getOptionChosen() {
        return optionChosen;
    }

    public void setOptionChosen(String optionChosen) {
        this.optionChosen = optionChosen;
    }
}

package com.example.Innovatiview.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "centres")
public class CentreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String centreCode;
    private String centreName;

    public CentreEntity() {
    }

    public CentreEntity(String centreCode, String centreName) {
        this.centreCode = centreCode;
        this.centreName = centreName;
    }

    public Long getId() {
        return id;
    }

    public String getCentreCode() {
        return centreCode;
    }

    public String getCentreName() {
        return centreName;
    }
}

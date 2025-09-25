package com.system.project.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Caste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String casteName;

    @OneToMany(mappedBy = "caste", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Fees> fees;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCasteName() {
        return casteName;
    }

    public void setCasteName(String casteName) {
        this.casteName = casteName;
    }

    public Set<Fees> getFees() {
        return fees;
    }

    public void setFees(Set<Fees> fees) {
        this.fees = fees;
    }
}
package com.softuni.movienightapp.model.entity;

import com.softuni.movienightapp.model.enums.RoleNames;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleNames role;

    @Column(columnDefinition = "TEXT")
    private String description;

    public RoleEntity() {
    }

    public Long getId() {
        return id;
    }

    public RoleNames getRole() {
        return role;
    }

    public RoleEntity setRole(RoleNames role) {
        this.role = role;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RoleEntity setDescription(String description) {
        this.description = description;
        return this;
    }
}

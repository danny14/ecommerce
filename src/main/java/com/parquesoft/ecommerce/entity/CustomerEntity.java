package com.parquesoft.ecommerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column( nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "loyality_points", nullable = false)
    private int loyalityPoints = 0;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    public CustomerEntity() {
    }

    public CustomerEntity(Long id, boolean isActive, String fullName, String email, int loyalityPoints) {
        this.id = id;
        this.isActive = isActive;
        this.fullName = fullName;
        this.email = email;
        this.loyalityPoints = loyalityPoints;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLoyalityPoints() {
        return loyalityPoints;
    }

    public void setLoyalityPoints(int loyalityPoints) {
        this.loyalityPoints = loyalityPoints;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

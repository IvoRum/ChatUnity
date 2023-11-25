package com.tu.varna.chat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.Set;

@Entity
public class UnityUser {

    @Id
    private Long id;
    private String email;
    private String telephone;
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "family_name")
    private String familyName;
    @ManyToMany
    private Set<UnityUser> friends;

    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Set<UnityUser> getFriends() {
        return friends;
    }

    public void setFriends(Set<UnityUser> friends) {
        this.friends = friends;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

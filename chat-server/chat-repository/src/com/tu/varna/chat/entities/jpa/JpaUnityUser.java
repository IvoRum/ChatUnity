package com.tu.varna.chat.entities.jpa;

import com.tu.varna.chat.model.UnityUser;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "unity_user")
@NamedQuery(name = "unity_user.findAll", query = "SELECT unityuser FROM JpaUnityUser AS unityuser")
public class JpaUnityUser implements UnityUser {

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
    private Set<JpaUnityUser> friends;

    public JpaUnityUser() {
        // Needed by JPA.
    }

    @Override
    public UnityUser getFriends() {
        return null;
    }
}

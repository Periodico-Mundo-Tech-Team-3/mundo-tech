package com.mundotech.newspaper.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    //@NotBlank(message = "El nombre no puede estar vacío")
    //@Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String name;

    @Column(nullable = false,unique = true)
    //@NotBlank
    //@Email
    private String email;

    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Article> articles;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
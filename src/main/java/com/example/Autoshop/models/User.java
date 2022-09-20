package com.example.Autoshop.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min=2, max = 50, message = "Размер данного поля должен быть в диапозене от 2 до 50")
    @NotEmpty(message = "Поле не может быть пустым")
    private String surname, name, patronymic;
    @Size(min=2, max = 50, message = "Размер данного поля должен быть в диапозене от 2 до 50")
    @NotEmpty(message = "Поле не может быть пустым")
    private String username;
    @Size(min=2, max = 50, message = "Размер данного поля должен быть в диапозене от 2 до 50")
    @NotEmpty(message = "Поле не может быть пустым")
    private String password;
    private boolean active;

    @OneToMany (mappedBy = "user")
    private Collection<Zakaz> zakazy;

    @OneToMany (mappedBy = "user")
    private Collection<Service> services;

    public User(String surname, String name, String patronymic, String username, String password, boolean active) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.username = username;
        this.password = password;
        this.active = active;
    }

    public Collection<Zakaz> getZakazy() {
        return zakazy;
    }

    public void setZakazy(Collection<Zakaz> zakazy) {
        this.zakazy = zakazy;
    }

    public Collection<Service> getServices() {
        return services;
    }

    public void setServices(Collection<Service> services) {
        this.services = services;
    }

    public User() {

    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
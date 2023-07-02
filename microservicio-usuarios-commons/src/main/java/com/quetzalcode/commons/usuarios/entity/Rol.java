package com.quetzalcode.commons.usuarios.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="roles")
public class Rol implements Serializable {
    private static final long serialVersionUID = 2230670564873568182L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 30)
    private String nombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

package com.busqueda_ms.msbusqueda.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Producto")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 250)
    private String nombreProd;

    @Column(nullable = false, length = 250)
    private String origenProd;

    @Column(nullable = false, length = 250)
    private String materialPrincipal;

    @Column(nullable = false)
    private boolean reutilizable;

    @Column(nullable = false)
    private int vidaUtilMeses;
    
    @Column(nullable = false)
    private int precio;

    @Column(nullable = false, length = 250)
    private String categoria;
    
    @Column(nullable = false)
    private int cantidad;
}

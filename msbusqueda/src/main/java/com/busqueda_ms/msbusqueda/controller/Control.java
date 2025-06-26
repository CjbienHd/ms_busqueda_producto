package com.busqueda_ms.msbusqueda.controller;

import com.busqueda_ms.msbusqueda.service.ServicioBusqProducto;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import com.busqueda_ms.msbusqueda.model.Producto;



@RestController
@RequiredArgsConstructor
@RequestMapping("/ecomarket")
public class Control {

    private final ServicioBusqProducto servicioBusqProducto;


    @GetMapping
    public String status() {
        return "microservicio busqueda de productos funcionando correctamente";
    }

    // --- BÚSQUEDA DE PRODUCTOS CON FILTROS ---
    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String origen,
            @RequestParam(required = false) String material,
            @RequestParam(required = false) Boolean reutilizable,
            @RequestParam(required = false) Integer vidaMin,
            @RequestParam(required = false) Integer vidaMax,
            @RequestParam(required = false) Integer precioMin,
            @RequestParam(required = false) Integer precioMax,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Integer cantidadMin,
            @RequestParam(required = false) Integer cantidadMax
    ) {
        return ResponseEntity.ok().body(servicioBusqProducto.buscaComplejaProductos(
                nombre, origen, material, reutilizable, vidaMin, vidaMax,
                precioMin, precioMax, categoria, cantidadMin, cantidadMax
        ));
    }

    // --- INVENTARIO ---

    // Obtener todo el inventario
    @GetMapping("/inventario")
    public ResponseEntity<List<Producto>> obtenerInventario() {
            return ResponseEntity.ok().body(servicioBusqProducto.obtenerInventarioCompleto());
    }

    // Obtener producto por ID
    @GetMapping("/inventario/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(servicioBusqProducto.obtenerPorId(id));
    }

    
}

    



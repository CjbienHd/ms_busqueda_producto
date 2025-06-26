package com.busqueda_ms.msbusqueda.controller;

import com.busqueda_ms.msbusqueda.service.ServicioBusqProducto;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import com.busqueda_ms.msbusqueda.model.Producto;



@RestController
@RequiredArgsConstructor
@RequestMapping("/ecomarket")
@Tag(name = "Busqueda", description = "Operaciones relacionadas con la busqueda de productos")
public class Control {

    private final ServicioBusqProducto servicioBusqProducto;


    @GetMapping
     @Operation(
        summary = "Consulta estado del servidor",
        description = "Obtiene el estado del servicio si esta activo"
        )
        @ApiResponse(responseCode = "200",
                            description = "El servicio está activo"
        )
    public String status() {
        return "microservicio busqueda de productos funcionando correctamente";
    }

    // Busqueda con filtros de productos


    @GetMapping("/buscar")
    @Operation(
        summary = "Busqueda compleja de productos",
        description = "Obtiene productos en base al cumplimiento de distintos filtros."
        )
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Filtros aplicados correctamente"),
    @ApiResponse(responseCode = "400", description = "Ingreso de tipo de dato incorrecto")
    })

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


    // Obtener todo el inventario
    @GetMapping("/inventario")
    @Operation(
        summary = "Obtener todos los productos",
        description = "Muestra una lista con todos los productos"
)
    @ApiResponse(responseCode = "200", description = "Lista de productos encontrada")

    public ResponseEntity<List<Producto>> obtenerInventario() {
            return ResponseEntity.ok().body(servicioBusqProducto.obtenerInventarioCompleto());
    }

    // Obtener producto por ID
    @GetMapping("/inventario/{id}")
    @Operation(
        summary = "Busqueda por ID",
        description = "Obtiene productos buscando por ID"
    )
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Operación exitosa"),
    @ApiResponse(responseCode = "400", description = "Ingreso de tipo de dato incorrecto"),
    @ApiResponse(responseCode = "500", description = "No se encontro producto con ID")
})
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(servicioBusqProducto.obtenerPorId(id));
    }

    
}

    



package com.busqueda_ms.msbusqueda.assembler;

import com.busqueda_ms.msbusqueda.controller.Control;
import com.busqueda_ms.msbusqueda.model.Producto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProductoAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
            linkTo(methodOn(Control.class).obtenerPorId(producto.getId())).withSelfRel(),
            linkTo(methodOn(Control.class).obtenerInventario()).withRel("todos")
        );
    }
}
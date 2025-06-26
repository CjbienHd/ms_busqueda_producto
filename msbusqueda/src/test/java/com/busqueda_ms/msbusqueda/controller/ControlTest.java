package com.busqueda_ms.msbusqueda.controller;

import com.busqueda_ms.msbusqueda.service.ServicioBusqProducto;
import com.busqueda_ms.msbusqueda.assembler.ProductoAssembler;
import com.busqueda_ms.msbusqueda.model.Producto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;



import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;




import java.util.List;

@WebMvcTest(Control.class)
public class ControlTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ServicioBusqProducto servicioBusqProducto;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductoAssembler assembler;

    @Test
    void status_DeberiaRetornarMensaje() throws Exception {
        mockMvc.perform(get("/ecomarket"))
                .andExpect(status().isOk())
                .andExpect(content().string("microservicio busqueda de productos funcionando correctamente"));
    }

    @Test
    void obtenerInventario_DeberiaRetornarInventario() throws Exception {
        Producto producto = new Producto(1L, "producto1", "origen1", "material", true, 12, 1000, "categoria", 2);
        List<Producto> productos = List.of(producto);

        when(servicioBusqProducto.obtenerInventarioCompleto()).thenReturn(productos);
        when(assembler.toModel(producto)).thenReturn(EntityModel.of(producto));

        mockMvc.perform(get("/ecomarket/inventario"))
                .andExpect(status().isOk());
    }


    @Test
    void obtenerPorId_DeberiaRetornarProducto() throws Exception {
        Producto producto = new Producto(1L, "nombre", "origen", "material", true, 12, 1000, "categoria", 2);

        when(servicioBusqProducto.obtenerPorId(1L)).thenReturn(producto);
        when(assembler.toModel(producto)).thenReturn(EntityModel.of(producto));

        mockMvc.perform(get("/ecomarket/inventario/1"))
                .andExpect(status().isOk());
    }

    @Test
    void buscar_DeberiaRetornarListaFiltrada() throws Exception {
        List<Producto> filtrados = List.of(new Producto(1L, "filtro", "origen", "material", true, 12, 1000, "categoria", 5));
        when(servicioBusqProducto.buscaComplejaProductos(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(filtrados);

        mockMvc.perform(get("/ecomarket/buscar").param("nombre", "filtro"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(filtrados)));
    }
}

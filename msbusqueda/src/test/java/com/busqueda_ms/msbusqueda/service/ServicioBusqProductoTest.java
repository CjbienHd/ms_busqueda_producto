package com.busqueda_ms.msbusqueda.service;

import com.busqueda_ms.msbusqueda.model.Producto;
import com.busqueda_ms.msbusqueda.repository.RepositoryProducto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;



@ExtendWith(MockitoExtension.class)
public class ServicioBusqProductoTest {

    @Mock
    private RepositoryProducto repositoryProducto;

    @InjectMocks
    private ServicioBusqProducto servicio;

    @Test
    void obtenerInventarioCompleto_DeberiaRetornarTodosLosProductos() {
        List<Producto> productos = List.of(new Producto(1L, "test", "origen", "mat", true, 10, 500, "cat", 3));
        when(repositoryProducto.findAll()).thenReturn(productos);

        List<Producto> resultado = servicio.obtenerInventarioCompleto();

        assertEquals(1, resultado.size());
        assertEquals("test", resultado.get(0).getNombreProd());
    }

    @Test
    void obtenerPorId_DeberiaRetornarProducto() {
        Producto producto = new Producto(1L, "test", "origen", "mat", true, 10, 500, "cat", 3);
        when(repositoryProducto.findById(1L)).thenReturn(Optional.of(producto));

        Producto resultado = servicio.obtenerPorId(1L);

        assertEquals("test", resultado.getNombreProd());
    }

    @Test
    void obtenerPorId_NoExistente_DeberiaLanzarExcepcion() {
        when(repositoryProducto.findById(999L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class, () -> servicio.obtenerPorId(999L));
        assertTrue(ex.getMessage().contains("No se encontró producto con id"));
    }

    @Test
    void buscaComplejaProductos_DeberiaFiltrarPorNombre() {
        Producto p1 = new Producto(1L, "bolsa reciclable", "Chile", "algodon", true, 12, 1000, "bolsas", 10);
        Producto p2 = new Producto(2L, "cuchillo", "China", "plastico", false, 6, 300, "utensilios", 2);
        when(repositoryProducto.findAll()).thenReturn(List.of(p1, p2));

        List<Producto> filtrados = servicio.buscaComplejaProductos("bolsa", null, null, null, null, null, null, null, null, null, null);

        assertEquals(1, filtrados.size());
        assertEquals("bolsa reciclable", filtrados.get(0).getNombreProd());
    }
}


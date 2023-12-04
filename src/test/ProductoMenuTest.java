package test;



import org.junit.jupiter.api.Test;

import uniandes.dpoo.taller2.modelo.ProductoMenu;

import static org.junit.Assert.*;
import org.junit.Before;

public class ProductoMenuTest {

    private ProductoMenu productoMenu;

    @Before
    public void setUp() {
        // Inicialización común para todas las pruebas
        productoMenu = new ProductoMenu("Hamburguesa", 10000, 500);
    }

    @Test
    public void testGenerarTextoFactura() {
        String resultadoEsperado = "-Hamburguesa $10000 calorias:500";
        String resultadoReal = productoMenu.generarTextoFactura();
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void testToString() {
        assertEquals("Hamburguesa", productoMenu.toString());
    }

    @Test
    public void testGetCalorias() {
        assertEquals(500, productoMenu.getCalorias());
    }

    @Test
    public void testCompareTo() {
        ProductoMenu otroProducto = new ProductoMenu("Ensalada", 8000, 200);
        assertTrue(productoMenu.compareTo(otroProducto) > 0);
        assertTrue(otroProducto.compareTo(productoMenu) < 0);
        ProductoMenu productoIgual = new ProductoMenu("Hamburguesa", 12000, 550);
        assertEquals(0, productoMenu.compareTo(productoIgual));
    }
}

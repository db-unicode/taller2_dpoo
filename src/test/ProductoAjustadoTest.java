package test;




import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import uniandes.dpoo.taller2.modelo.Ingrediente;
import uniandes.dpoo.taller2.modelo.ProductoAjustado;
import uniandes.dpoo.taller2.modelo.ProductoMenu;

public class ProductoAjustadoTest {

    private ProductoMenu productoBase;
    private ProductoAjustado productoAjustado;

    @Before
    public void setUp() {
        productoBase = new ProductoMenu("Hamburguesa", 10000, 500);
        productoAjustado = new ProductoAjustado(productoBase);
    }

    @Test
    public void testAgregarIngredienteIncrementaPrecioYCalorias() {
        Ingrediente ingredienteAdicional = new Ingrediente("Tomate", 1000, 40);
        productoAjustado.agregarIngrediente(ingredienteAdicional);
        assertEquals(11000, productoAjustado.getPrecio());
        assertEquals(540, productoAjustado.getCalorias());
    }

    @Test
    public void testAgregarMismoIngredienteRepetidamente() {
        Ingrediente ingredienteAdicional = new Ingrediente("Tomate", 1000, 40);
        productoAjustado.agregarIngrediente(ingredienteAdicional);
        productoAjustado.agregarIngrediente(ingredienteAdicional); 
        assertEquals(11000, productoAjustado.getPrecio());
        assertEquals(540, productoAjustado.getCalorias());
    }

    @Test
    public void testQuitarIngredienteQueNoFueAgregado() {
        Ingrediente ingredienteEliminar = new Ingrediente("Cebolla", 500, 20);
        productoAjustado.quitarIngrediente(ingredienteEliminar);
        assertTrue(productoAjustado.getCalorias() == 500); // Las calorías no cambian
        assertTrue(productoAjustado.toString().contains("Cebolla")); // Verificar que 'Cebolla' esté en la lista de eliminados
    }

    @Test
    public void testGenerarTextoFacturaConAgregadosYEliminados() {
        Ingrediente ingredienteAdicional = new Ingrediente("Queso", 1500, 60);
        Ingrediente ingredienteEliminar = new Ingrediente("Cebolla", 500, 20);
        productoAjustado.agregarIngrediente(ingredienteAdicional);
        productoAjustado.quitarIngrediente(ingredienteEliminar);
        String textoFactura = productoAjustado.generarTextoFactura();
        assertTrue(textoFactura.contains("Queso"));
        assertTrue(textoFactura.contains("Cebolla"));
    }

    @Test
    public void testMismasAdiciones() {
        ProductoAjustado otroProductoAjustado = new ProductoAjustado(productoBase);
        Ingrediente ingredienteAdicional = new Ingrediente("Queso", 1500, 60);
        productoAjustado.agregarIngrediente(ingredienteAdicional);
        otroProductoAjustado.agregarIngrediente(ingredienteAdicional);
        assertTrue(productoAjustado.mismasAdiciones(otroProductoAjustado));
    }

    @Test
    public void testMismasEliminaciones() {
        ProductoAjustado otroProductoAjustado = new ProductoAjustado(productoBase);
        Ingrediente ingredienteEliminar = new Ingrediente("Cebolla", 500, 20);
        productoAjustado.quitarIngrediente(ingredienteEliminar);
        otroProductoAjustado.quitarIngrediente(ingredienteEliminar);
        assertTrue(productoAjustado.mismasEliminaciones(otroProductoAjustado));
    }

    @Test
    public void testToStringConAgregadosYEliminados() {
        Ingrediente ingredienteAdicional = new Ingrediente("Queso", 1500, 60);
        Ingrediente ingredienteEliminar = new Ingrediente("Cebolla", 500, 20);
        productoAjustado.agregarIngrediente(ingredienteAdicional);
        productoAjustado.quitarIngrediente(ingredienteEliminar);
        String resultadoToString = productoAjustado.toString();
        assertTrue(resultadoToString.contains("Queso"));
        assertTrue(resultadoToString.contains("Cebolla"));
    }


    @Test
    public void testEqualsConProductoDiferente() {
        ProductoAjustado otroProducto = new ProductoAjustado(new ProductoMenu("Ensalada", 8000, 300));
        assertFalse(productoAjustado.equals(otroProducto));
    }


    @Test
    public void testCompareToConProductoDiferente() {
        ProductoMenu otroProducto = new ProductoMenu("Ensalada", 8000, 300);
        assertTrue(productoAjustado.compareTo(otroProducto) != 0);
    }
}

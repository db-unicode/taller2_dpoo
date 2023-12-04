package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import uniandes.dpoo.taller2.excepciones.ValorPedidoExcedido;
import uniandes.dpoo.taller2.modelo.Pedido;
import uniandes.dpoo.taller2.modelo.ProductoMenu;

public class PedidoTest {

    private Pedido pedido;
    private ProductoMenu producto1;
    private ProductoMenu producto2;

    @Before
    public void setUp() {
        pedido = new Pedido("Cliente 1", "Direccion 1");
        producto1 = new ProductoMenu("Producto 1", 10000, 500); // Precio 10000, Calorías 500
        producto2 = new ProductoMenu("Producto 2", 15000, 800); // Precio 15000, Calorías 800
    }

    @Test
    public void testAgregarProducto() throws ValorPedidoExcedido {
        pedido.agregarProducto(producto1);
        assertEquals(1, pedido.getCantidadItems());
        pedido.agregarProducto(producto2);
        assertEquals(2, pedido.getCantidadItems());
    }

    @Test(expected = ValorPedidoExcedido.class)
    public void testAgregarProductoConExcepcion() throws ValorPedidoExcedido {
        ProductoMenu productoCaro = new ProductoMenu("Producto Caro", 200000, 1000);
        pedido.agregarProducto(productoCaro);
    }

    @Test
    public void testGetPrecioNetoPedido() throws ValorPedidoExcedido {
        pedido.agregarProducto(producto1);
        pedido.agregarProducto(producto2);
        assertEquals(25000, pedido.getPrecioNetoPedido()); // 10000 + 15000
    }

    @Test
    public void testGetCaloriasTotales() throws ValorPedidoExcedido {
        pedido.agregarProducto(producto1);
        pedido.agregarProducto(producto2);
        assertEquals(1300, pedido.getCaloriasTotales()); // 500 + 800
    }

    @Test
    public void testGetPrecioIVAPedido() throws ValorPedidoExcedido {
        pedido.agregarProducto(producto1);
        assertEquals(1900, pedido.getPrecioIVAPedido()); // 19% de 10000
    }

    @Test
    public void testGetPrecioTotalPedido() throws ValorPedidoExcedido {
        pedido.agregarProducto(producto1);
        assertEquals(11900, pedido.getPrecioTotalPedido()); // 10000 + 19% de 10000
    }

    @Test
    public void testGenerarTextoFactura() throws ValorPedidoExcedido {
        pedido.agregarProducto(producto1);
        String textoFactura = pedido.generarTextoFactura();
        assertTrue(textoFactura.contains("Producto 1"));
        assertTrue(textoFactura.contains("10000"));
    }

    @Test
    public void testEquals() {
        Pedido otroPedido = new Pedido("Cliente 1", "Direccion 1");
        assertEquals(pedido, otroPedido);
    }

    @Test
    public void testGetIdPedido() {
        assertEquals(1, pedido.getIdPedido());
    }

    @Test
    public void testGenerarTextoFacturaConMultiplesProductos() throws ValorPedidoExcedido {
        pedido.agregarProducto(producto1);
        pedido.agregarProducto(producto2);
        String textoFactura = pedido.generarTextoFactura();
        assertTrue(textoFactura.contains("Producto 1"));
        assertTrue(textoFactura.contains("Producto 2"));
        assertTrue(textoFactura.contains("Total:"));
    }

    @Test
    public void testEqualsConPedidoDiferente() {
        Pedido otroPedido = new Pedido("Cliente 2", "Direccion 2");
        assertNotEquals(pedido, otroPedido);
    }

    @Test
    public void testGuardarFacturaCreaArchivo() {
        pedido.guardarFactura();
    }




    // Otros tests que consideres necesarios para cubrir completamente la funcionalidad de la clase
}

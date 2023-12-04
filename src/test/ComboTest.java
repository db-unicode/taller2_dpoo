package test;



import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import uniandes.dpoo.taller2.modelo.Combo;
import uniandes.dpoo.taller2.modelo.ProductoMenu;

public class ComboTest {

    private Combo combo;
    private ProductoMenu item1;
    private ProductoMenu item2;

    @Before
    public void setUp() {
        combo = new Combo("Combo 1", 0.1); // 10% de descuento
        item1 = new ProductoMenu("Item 1", 5000, 300); // Precio 5000, Calorías 300
        item2 = new ProductoMenu("Item 2", 7000, 500); // Precio 7000, Calorías 500
    }

    @Test
    public void testAgregarItemACombo() {
        combo.agregarItemACombo(item1);
        assertTrue(combo.getItemsCombo().contains(item1));
    }

    @Test
    public void testGetCalorias() {
        combo.agregarItemACombo(item1);
        combo.agregarItemACombo(item2);
        assertEquals(800, combo.getCalorias()); // 300 + 500
    }

    @Test
    public void testGenerarTextoFactura() {
        combo.agregarItemACombo(item1);
        combo.agregarItemACombo(item2);
        String textoFactura = combo.generarTextoFactura();
        assertEquals("-Combo 1 $10800", textoFactura); // Precio total con descuento: (5000 + 7000) * 0.9 = 10800
    }

    @Test
    public void testToString() {
        combo.agregarItemACombo(item1);
        combo.agregarItemACombo(item2);
        String resultadoToString = combo.toString();
        assertTrue(resultadoToString.contains("Item 1"));
        assertTrue(resultadoToString.contains("Item 2"));
        assertTrue(resultadoToString.contains("$10800"));
        assertTrue(resultadoToString.contains("calorias800"));
    }

    @Test
    public void testCompareTo() {
        Combo otroCombo = new Combo("Combo 2", 0.15);
        assertTrue(combo.compareTo(otroCombo) < 0);
    }


}


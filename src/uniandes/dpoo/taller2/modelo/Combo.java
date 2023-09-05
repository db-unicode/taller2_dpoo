package uniandes.dpoo.taller2.modelo;

import java.util.List;
import java.util.ArrayList;
import java.text.MessageFormat;


public class Combo implements Producto {
	private double descuento;
	private String nombreCombo;
	private List<ProductoMenu> itemsCombo;

	public Combo(String nombre, double descuento) {
		this.nombreCombo = nombre;
		this.descuento = descuento;
		this.itemsCombo = new ArrayList<ProductoMenu>();
	}

	public void agregarItemACombo(ProductoMenu itemCombo) {
		this.itemsCombo.add(itemCombo);
	}
	
	public List<ProductoMenu> getItemsCombo() {
		return this.itemsCombo;
	}
	
	@Override
	public int getPrecio() {
		int precioCombo = 0;
		for (Producto itemCombo: this.itemsCombo) {
			precioCombo += itemCombo.getPrecio();
		}
		precioCombo *= 1 - this.descuento;
		return precioCombo;
	}

	@Override
	public String getNombre() {
		return this.nombreCombo;
	}

	@Override
	public String generarTextoFactura() {
		String plantillaTextoFactura = "-{0} ${1}";
		String textoFactura = MessageFormat.format(
				plantillaTextoFactura, 
				this.getNombre(), 
				this.getPrecio());
		return textoFactura;
	}
	
	@Override 
	public String toString() {
		String plantillaComboString = "{0}:{1} ${2} calorias{3}";
		String textoComboString = MessageFormat.format(
				plantillaComboString,
				this.getNombre(),
				this.itemsCombo,
				this.getPrecio(),
				this.getCalorias());
		return textoComboString;
	}

	@Override
	public int getCalorias() {
		int caloriasCombo = 0;
		for (Producto itemCombo: this.itemsCombo) {
			caloriasCombo += itemCombo.getCalorias();
		}
		return caloriasCombo;
	}
	
	@Override
	public int compareTo(Producto otro) {	
		return this.getNombre().compareTo(otro.getNombre());
	}

}

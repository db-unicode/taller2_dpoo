package uniandes.dpoo.taller2.modelo;

import java.text.MessageFormat;

public class ProductoMenu implements Producto {
	private String nombre;
	private int precioBase;
	private int calorias;
	public ProductoMenu(String nombre, int precioBase, int calorias) {
		this.nombre = nombre;
		this.precioBase = precioBase;
		this.calorias = calorias;
	}

	@Override
	public String getNombre() {
		return this.nombre;
	}

	@Override
	public int getPrecio() {
		return this.precioBase;
	}

	@Override
	public String generarTextoFactura() {
		String plantillaTextoFactura = "-{0} ${1} calorias:{2}";
		String textoFactura = MessageFormat.format(
				plantillaTextoFactura, 
				this.getNombre(), 
				this.getPrecio(),
				this.getCalorias());
		return textoFactura;
	}
	
	@Override
	public String toString() {
		return this.getNombre();
	}
	
	public int getCalorias() {
		return calorias;
	}

	@Override
	public int compareTo(Producto otro) {	
		return this.nombre.compareTo(otro.getNombre());
	}

}

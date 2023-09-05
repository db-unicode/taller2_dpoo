package uniandes.dpoo.taller2.modelo;

public interface Producto extends Comparable<Producto>{
	public int getPrecio();
	public String getNombre();
	public int getCalorias();
	public String generarTextoFactura();
}

package uniandes.dpoo.taller2.modelo;

public class Ingrediente implements Comparable<Ingrediente>{
	private String nombre;
	private int costoAdicional;
	private int calorias;

	public Ingrediente(String nombre, int costoAdicional, int calorias) {
		this.nombre = nombre;
		this.costoAdicional = costoAdicional;
		this.calorias = calorias;
	}

	@Override
	public String toString() {
		return this.getNombre();
	}

	public String getNombre() {
		return this.nombre;
	}

	public int getCostoAdicional() {
		return this.costoAdicional;
	}

	public int getCalorias() {
		return calorias;
	}

	@Override
	public int compareTo(Ingrediente o) {
		return this.getNombre().compareTo(o.getNombre());
	}
	

}

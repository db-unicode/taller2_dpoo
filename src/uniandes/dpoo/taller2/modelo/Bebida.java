package uniandes.dpoo.taller2.modelo;

public class Bebida extends ProductoMenu {
	@SuppressWarnings("unused")
	private String nombre;
	@SuppressWarnings("unused")
	private int precioBase;
	@SuppressWarnings("unused")
	private int calorias;

	public Bebida(String nombre, int precioBase, int calorias) {
		super(nombre, precioBase, calorias);
	}
}

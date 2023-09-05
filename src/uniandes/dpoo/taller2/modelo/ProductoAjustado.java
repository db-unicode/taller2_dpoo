package uniandes.dpoo.taller2.modelo;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class ProductoAjustado implements Producto {
	private int precio;
	private int calorias;
	private String nombre;
	private Set<Ingrediente> agregados;
	private Set<Ingrediente> eliminados;

	public ProductoAjustado(ProductoMenu base) {
		this.precio = base.getPrecio();
		this.nombre = base.getNombre();
		this.calorias = base.getCalorias();
		this.agregados = new LinkedHashSet<Ingrediente>();
		this.eliminados = new LinkedHashSet<Ingrediente>();
	}

	@Override
	public int getPrecio() {
		return this.precio;
	}

	@Override
	public String getNombre() {
		return this.nombre;
	}

	@Override
	public String generarTextoFactura() {
		String plantillaTextoFactura = "-{0} ${1} adicion:{2} sin:{3} calorias:{4}";
		String textoFactura = MessageFormat.format(
				plantillaTextoFactura, 
				this.getNombre(), 
				this.getPrecio(),
				this.agregados, 
				this.eliminados,
				this.calorias);
		return textoFactura;
	}
	
	public void agregarIngrediente(Ingrediente adicion) {
		this.agregados.add(adicion);
		this.precio += adicion.getCostoAdicional();
		this.calorias += adicion.getCalorias();
	}
	// no se eliminan las calorias ya que puede llegar a haber calorias negativas
	public void quitarIngrediente(Ingrediente eliminar) {
		this.eliminados.add(eliminar);		
	}

	public int getCalorias() {
		return calorias;
	}
	
	public boolean mismasAdiciones(ProductoAjustado otro) {
		List<Ingrediente> thisAdiciones = new ArrayList<Ingrediente>(this.agregados);
		List<Ingrediente> otroAdiciones = new ArrayList<Ingrediente>(otro.agregados);
		Collections.sort(thisAdiciones);
		Collections.sort(otroAdiciones);
		if (thisAdiciones.size() != otroAdiciones.size()) {
			return false;
		}
		for (int i = 0; i < thisAdiciones.size(); i++) {
			if (thisAdiciones.get(i)!= otroAdiciones.get(i)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean mismasEliminaciones(ProductoAjustado otro) {
		List<Ingrediente> thisEliminaciones = new ArrayList<Ingrediente>(this.eliminados);
		List<Ingrediente> otroEliminaciones = new ArrayList<Ingrediente>(otro.eliminados);
		Collections.sort(thisEliminaciones);
		Collections.sort(otroEliminaciones);
		if (thisEliminaciones.size() != otroEliminaciones.size()) {
			return false;
		}
		for (int i = 0; i < thisEliminaciones.size(); i++) {
			if (thisEliminaciones.get(i)!= otroEliminaciones.get(i)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public int compareTo(Producto otro) {	
		return this.nombre.compareTo(otro.getNombre());
	}
	
	@Override
	public boolean equals(Object obj) {
        if (this == obj) {
            return false;
        }
        if (obj == null || getClass() != obj.getClass()) {
        	return false;
        }
        ProductoAjustado otro = (ProductoAjustado) obj;
        if (nombre != otro.nombre) {
        	return false;
        }
		return true;
	}
	
	@Override
	public String toString() {
		String productoAjustado = getNombre() + getPrecio() + getCalorias() + agregados + eliminados;
		return productoAjustado;
	}
}

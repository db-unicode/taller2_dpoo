package uniandes.dpoo.taller2.modelo;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.text.MessageFormat;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class Pedido {
	private static final double IVA = 0.19;
	private static int numeroPedidos = 0;
	private int idPedido;
	private String nombreCliente;
	private String direccionCliente;
	private List<Producto> itemsPedido;

	public Pedido(String nombreCliente, String direccionCliente) {
		Pedido.numeroPedidos += 1;
		this.idPedido = Pedido.numeroPedidos;
		this.nombreCliente = nombreCliente;
		this.direccionCliente = direccionCliente;
		this.itemsPedido = new ArrayList<Producto>();
	}

	public int getIdPedido() {
		return idPedido;
	}
	
	public int getCantidadItems() {
		return itemsPedido.size();
	}
	public void agregarProducto(Producto nuevoItem) {
		this.itemsPedido.add(nuevoItem);
	}
	
	public void guardarFactura() {
	    String carpetaFacturas = "facturas"; // Ruta relativa al directorio actual del programa
	    String nombreArchivo = "FACTURA_ID_" + this.getIdPedido();
	    Path ruta = Paths.get(carpetaFacturas, nombreArchivo);
	    String contenido = this.generarTextoFactura();
	    try {
	        if (!Files.exists(ruta.getParent())) {
	            Files.createDirectories(ruta.getParent());
	        }
	        Files.write(ruta, contenido.getBytes());
	    } catch (IOException e) {
	        System.out.println("Error al guardar la factura en " + ruta.toString());
	        e.printStackTrace();
	    }
	}

	
	private int getPrecioNetoPedido() {
		int precioNetoPedido = 0;
		for (Producto itemPedido : this.itemsPedido) {
			precioNetoPedido += itemPedido.getPrecio();
		}
		return precioNetoPedido;
	}

	private int getCaloriasTotales() {
		int caloriasTotales = 0;
		for (Producto itemPedido : this.itemsPedido) {
			caloriasTotales += itemPedido.getCalorias();
		}
		return caloriasTotales;
	}
	// Creo que debería retornar un double. La implementación se apega al diagrama
	// UML presentado.
	private int getPrecioIVAPedido() {
		double precioIVAFlotante = this.getPrecioNetoPedido() * Pedido.IVA;
		int precioIVAEntero = (int) precioIVAFlotante;
		return precioIVAEntero;
	}

	private int getPrecioTotalPedido() {
		return this.getPrecioNetoPedido() + this.getPrecioIVAPedido();
	}
	
	private String generarHeaderFactura() {
		String plantillaHeaderFactura = "\t\tID PEDIDO:{0}\n\nCliente:{1} Direccion:{2}\n\n\n";
		String textoHeaderFactura = MessageFormat.format(
				plantillaHeaderFactura,
				this.getIdPedido(),
				this.nombreCliente,
				this.direccionCliente);
		return textoHeaderFactura;
	}
	
	private String generarBodyFactura() {
		StringBuilder builderBodyFactura = new StringBuilder();
		builderBodyFactura.append("ITEMS DEL PEDIDO:\n\n");
		for(Producto itemPedido: this.itemsPedido) {
			builderBodyFactura.append("\t");
			builderBodyFactura.append(itemPedido.generarTextoFactura());
			builderBodyFactura.append("\n");
		}
		builderBodyFactura.append("\n");
		String textoBodyFactura = builderBodyFactura.toString();
		return textoBodyFactura;
	}
	
	private String generarFooterFactura() {
		String plantillaFooterFactura = "Precio neto:${0}\nIVA:${1}\nTotal:${2}\nTotal calorias:{3}";
		String textoFooterFactura = MessageFormat.format(
				plantillaFooterFactura, 
				this.getPrecioNetoPedido(),
				this.getPrecioIVAPedido(),
				this.getPrecioTotalPedido(),
				this.getCaloriasTotales());
		return textoFooterFactura;
	}
	
	private String generarTextoFactura() {
		String textoHeaderFactura = this.generarHeaderFactura();
		String textoBodyFactura = this.generarBodyFactura();
		String textoFooterFactura = this.generarFooterFactura();
		String textoFactura = textoHeaderFactura + textoBodyFactura + textoFooterFactura;		
		return textoFactura;
	}
	@Override
	public String toString() {
		return itemsPedido.toString();
	}
	@Override
	public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
        	return false;
        }
        Pedido pedido = (Pedido) obj;
        if (this.getCantidadItems() != pedido.getCantidadItems()) {
        	return false;
        }
        Collections.sort(this.itemsPedido);
        Collections.sort(pedido.itemsPedido);
        for(int i = 0; i < getCantidadItems(); i++) {
        	Producto propio = this.itemsPedido.get(i);
        	Producto otro = pedido.itemsPedido.get(i);
        	if (propio instanceof ProductoAjustado && otro instanceof ProductoAjustado) {
        		ProductoAjustado propioAjustado = (ProductoAjustado) propio;
        		ProductoAjustado otroAjustado = (ProductoAjustado) otro;
        		if (propioAjustado.getNombre() != otroAjustado.getNombre()) {
        			return false;
        		}
        		if(!propioAjustado.mismasAdiciones(otroAjustado)) {
        			return false;
        		}
        		if(!propioAjustado.mismasEliminaciones(otroAjustado)) {
        			return false;
        		}
        	} else if(otro != propio) {
        		return false;
        	}
 
        }
		return true;
	}

}

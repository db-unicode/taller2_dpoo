package uniandes.dpoo.taller2.procesamiento;

import uniandes.dpoo.taller2.excepciones.HamburguesaException;
import uniandes.dpoo.taller2.excepciones.IngredienteRepetidoException;
import uniandes.dpoo.taller2.excepciones.ProductoRepetidoException;
import uniandes.dpoo.taller2.excepciones.ValorPedidoExcedido;
import uniandes.dpoo.taller2.modelo.Bebida;
import uniandes.dpoo.taller2.modelo.Combo;
import uniandes.dpoo.taller2.modelo.Ingrediente;
import uniandes.dpoo.taller2.modelo.Pedido;
import uniandes.dpoo.taller2.modelo.Producto;
import uniandes.dpoo.taller2.modelo.ProductoMenu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Restaurante {

	private Pedido pedidoEnCurso;
	private List<Ingrediente> ingredientes;
	private List<ProductoMenu> menuBase;
	private List<Combo> combos;
	private List<Bebida> bebidas;
	private Map<Integer, Pedido> pedidos;

	public Restaurante() {
		this.pedidoEnCurso = null;
		this.ingredientes = new ArrayList<Ingrediente>();
		this.menuBase = new ArrayList<ProductoMenu>();
		this.combos = new ArrayList<Combo>();
		this.bebidas = new ArrayList<Bebida>();
		this.pedidos = new HashMap<Integer, Pedido>();
		this.cargarInformacionRestaurante();
	}

	public void iniciarPedido(String nombreCliente, String DireccionCliente) {
		this.pedidoEnCurso = new Pedido(nombreCliente, DireccionCliente);
	}

	public void agregarProducto(Producto nuevoItem) {
		try {
			pedidoEnCurso.agregarProducto(nuevoItem);
		} catch (ValorPedidoExcedido e) {
			e.printStackTrace();
		}
	}
	public boolean existePedidoIdentico() {
        for (Map.Entry<Integer, Pedido> entrada : pedidos.entrySet()) {
            Pedido pedido = entrada.getValue();
            if(pedidoEnCurso.equals(pedido)) {
            	return true;
            }
        }
		return false;
	}
	public void cerrarYGuardarPedido() {
		this.pedidoEnCurso.guardarFactura();
		if(existePedidoIdentico()) {
			System.out.println("EXISTE PEDIDO IDENTICO");
		}
		this.pedidos.put(pedidoEnCurso.getIdPedido(), pedidoEnCurso);
		this.pedidoEnCurso = null;
	}

	public String obtenerStringIngredientes() {
		StringBuilder stringBuilderIngredientes = new StringBuilder();
		stringBuilderIngredientes.append("INGREDIENTES\n");
		int index = 1;
		for (Ingrediente ingrediente : this.ingredientes) {
			stringBuilderIngredientes.append(index + ". ");
			stringBuilderIngredientes.append(ingrediente.getNombre());
			stringBuilderIngredientes.append(" $");
			stringBuilderIngredientes.append(ingrediente.getCostoAdicional());
			stringBuilderIngredientes.append("\n");
			index++;
		}
		stringBuilderIngredientes.append("\n");
		String listaIngredientes = stringBuilderIngredientes.toString();
		return listaIngredientes;
	}

	public String obtenerStringMenuBase() {
		StringBuilder stringBuilderMenuBase = new StringBuilder();
		stringBuilderMenuBase.append("PRODUCTOS:\n");
		int index = 1;
		for (ProductoMenu productoMenu : this.menuBase) {
			stringBuilderMenuBase.append(index + ". ");
			stringBuilderMenuBase.append(productoMenu.getNombre());
			stringBuilderMenuBase.append(" $");
			stringBuilderMenuBase.append(productoMenu.getPrecio());
			stringBuilderMenuBase.append("\n");
			index++;
		}
		stringBuilderMenuBase.append("\n");
		String listaMenuBase = stringBuilderMenuBase.toString();
		return listaMenuBase;
	}
	
	public String obtenerStringBebidas() {
		StringBuilder stringBuilderBebidas = new StringBuilder();
		stringBuilderBebidas.append("PRODUCTOS:\n");
		int index = 1;
		for (Bebida bebida : this.bebidas) {
			stringBuilderBebidas.append(index + ". ");
			stringBuilderBebidas.append(bebida.getNombre());
			stringBuilderBebidas.append(" $");
			stringBuilderBebidas.append(bebida.getPrecio());
			stringBuilderBebidas.append("\n");
			index++;
		}
		stringBuilderBebidas.append("\n");
		String listaBebidas = stringBuilderBebidas.toString();
		return listaBebidas;
	}

	public String obtenerStringCombos() {
		StringBuilder stringBuilderCombos = new StringBuilder();
		stringBuilderCombos.append("COMBOS:\n");
		int index = 1;
		for (Combo combo: this.combos) {
			stringBuilderCombos.append(index + ". ");
			stringBuilderCombos.append(combo.getNombre());
			stringBuilderCombos.append(":");
			stringBuilderCombos.append(combo.getItemsCombo());
			stringBuilderCombos.append(" $");
			stringBuilderCombos.append(combo.getPrecio());
			stringBuilderCombos.append("\n");
			index++;
		}
		stringBuilderCombos.append("\n");
		String listaCombos = stringBuilderCombos.toString();
		return listaCombos;
	}

	public void cargarInformacionRestaurante() {
		File archivoIngredientes = new File("data/ingredientes.txt");
		File archivoMenu = new File("data/menu.txt");
		File archivoCombos = new File("data/combos.txt");
		File archivoBebida = new File("data/bebidas.txt");
		try {
			this.cargarIngredientes(archivoIngredientes);
			this.cargarMenu(archivoMenu);
			this.cargarBebidas(archivoBebida);
			this.cargarCombos(archivoCombos);
		} catch(HamburguesaException e ) {
			e.printStackTrace();
		}

	}

	public void cargarInformacionRestaurante(File archivoIngredientes, File archivoMenu, File archivoCombos) {
		try {
			this.cargarIngredientes(archivoIngredientes);
			this.cargarMenu(archivoMenu);
		} catch (HamburguesaException e) {
			e.printStackTrace();
		}

		this.cargarCombos(archivoCombos);
	}

	public Pedido getPedidoEnCurso() {
		return pedidoEnCurso;
	}

	public List<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public List<ProductoMenu> getMenuBase() {
		return menuBase;
	}

	public List<Combo> getCombos() {
		return combos;
	}

	public Map<Integer, Pedido> getPedidos() {
		return pedidos;
	}

	private void cargarIngredientes(File archivoIngredientes) throws IngredienteRepetidoException {
	    Set<String> nombresExistentes = new HashSet<>();

	    try (Scanner scanner = new Scanner(archivoIngredientes)) {
	        while (scanner.hasNextLine()) {
	            String linea = scanner.nextLine();
	            String[] partes = linea.split(";");
	            String nombreIngrediente = partes[0];

	            if (!nombresExistentes.add(nombreIngrediente)) {
	                throw new IngredienteRepetidoException("Ingrediente repetido: " + nombreIngrediente);
	            }

	            String precioIngredienteString = partes[1];
	            String caloriasIngredienteString = partes[2];
	            int precioIngrediente = Integer.parseInt(precioIngredienteString);
	            int caloriasIngrediente = Integer.parseInt(caloriasIngredienteString);

	            Ingrediente ingrediente = new Ingrediente(nombreIngrediente, precioIngrediente, caloriasIngrediente);
	            this.ingredientes.add(ingrediente);
	        }
	    } catch (FileNotFoundException e) {
	        System.err.println("Error al leer el archivo: " + e.getMessage());
	    }
	}


	private void cargarMenu(File archivoMenu) throws ProductoRepetidoException {
	    Set<String> nombresProductos = new HashSet<>();

	    try (Scanner scanner = new Scanner(archivoMenu)) {
	        while (scanner.hasNextLine()) {
	            String linea = scanner.nextLine();
	            String[] partes = linea.split(";");
	            String nombreProductoMenu = partes[0];

	            if (!nombresProductos.add(nombreProductoMenu)) {
	                throw new ProductoRepetidoException("Producto repetido en el menú: " + nombreProductoMenu);
	            }

	            String precioProductoMenuString = partes[1];
	            String caloriasProductoMenuString = partes[2];
	            int precioProducto = Integer.parseInt(precioProductoMenuString);
	            int caloriasProducto = Integer.parseInt(caloriasProductoMenuString);

	            ProductoMenu productoMenu = new ProductoMenu(nombreProductoMenu, precioProducto, caloriasProducto);
	            this.menuBase.add(productoMenu);
	        }
	    } catch (FileNotFoundException e) {
	        System.err.println("Error al leer el archivo: " + e.getMessage());
	    }
	}

	
	private void cargarBebidas(File archivoMenu) {
		try (Scanner scanner = new Scanner(archivoMenu)) {
			while (scanner.hasNextLine()) {
				String linea = scanner.nextLine();
				String[] partes = linea.split(";");
				String nombreBebida = partes[0];
				String precioBebidaString = partes[1];
				String caloriasBebidaString = partes[2];
				int precioBebida = Integer.parseInt(precioBebidaString);
				int caloriasBebida = Integer.parseInt(caloriasBebidaString);
				Bebida bebida = new Bebida(nombreBebida, precioBebida, caloriasBebida);
				this.bebidas.add(bebida);
			}
		} catch (FileNotFoundException e) {
			System.err.println("Error al leer el archivo: " + e.getMessage());
		}
	}

	private void cargarCombos(File archivoCombos) {
		try (Scanner scanner = new Scanner(archivoCombos)) {
			while (scanner.hasNextLine()) {
				String linea = scanner.nextLine();
				String[] partes = linea.split(";");
				String nombreCombo = partes[0];
				String descuentoComboString = partes[1].replace("%", "");
				int descuentoComboEntero = Integer.parseInt(descuentoComboString);
				float descuentoCombo = (float) descuentoComboEntero / 100;
				Combo combo= new Combo(nombreCombo, descuentoCombo);
				for (int i = 2; i < partes.length; i++) {
					String nombreProducto = partes[i];
					ProductoMenu productoMenu = encontrarProductoMenu(nombreProducto);
					if (productoMenu != null) {
						combo.agregarItemACombo(productoMenu);
					}
				}
				this.combos.add(combo);
			}
		} catch (FileNotFoundException e) {
			System.err.println("Error al leer el archivo: " + e.getMessage());
		}
	}
	
	private ProductoMenu encontrarProductoMenu(String nombreProducto) {
		for (ProductoMenu productoMenu: menuBase) {
			if (productoMenu.getNombre().equals(nombreProducto)) {
				return productoMenu;
			}
		}
		for (Bebida bebida: bebidas) {
			if (bebida.getNombre().equals(nombreProducto)) {
				return bebida;
			}
		}
		return null;
	}

	public List<Bebida> getBebidas() {
		return bebidas;
	}
}

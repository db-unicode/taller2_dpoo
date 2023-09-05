package uniandes.dpoo.taller2.consola;

import uniandes.dpoo.taller2.modelo.ProductoMenu;
import uniandes.dpoo.taller2.procesamiento.Restaurante;
import uniandes.dpoo.taller2.modelo.ProductoAjustado;
import uniandes.dpoo.taller2.modelo.Combo;
import uniandes.dpoo.taller2.modelo.Ingrediente;
import uniandes.dpoo.taller2.modelo.Bebida;
import java.util.Scanner;

public class Aplicacion {

	private Restaurante restaurante;
	private Scanner scanner;

	public Aplicacion() {
		this.restaurante = new Restaurante();
		this.scanner = new Scanner(System.in);
	}

	public static void main(String[] args) {
		Aplicacion aplicacion = new Aplicacion();
		aplicacion.ejecutarAplicacion();
	}

	private void ejecutarAplicacion() {
		boolean mantenerAbiertaAplicacion = true;
		while (mantenerAbiertaAplicacion) {
			mostrarMenuAplicacion();
			int respuesta = obtenerRespuesta();
			mantenerAbiertaAplicacion = ejecutarOpcionMenuAplicacion(respuesta);
		}
	}

	private void mostrarMenuAplicacion() {
		String header = "\n\n\t\tHAMBURGUESAS\n\n\n";
		String body = "INGRESE UNA OPCION:\n\n" + "1. Mostrar menu restaurante\n" + "2. Iniciar pedido nuevo\n"
				+ "3. Cerrar aplicacion";
		String menuAplicacion = header + body;
		System.out.println(menuAplicacion);
	}

	private boolean ejecutarOpcionMenuAplicacion(int opcion) {
		boolean respuesta = true;
		if (opcion == 1) {
			mostrarMenuRestaurante();
		} else if (opcion == 2) {
			iniciarPedidoNuevo();
		} else if (opcion == 3) {
			respuesta = false;
		} else {
			System.out.println("Ingresa una opcion valida");
		}
		return respuesta;
	}

	private void mostrarMenuRestaurante() {
		String ingredientes = this.restaurante.obtenerStringIngredientes();
		String productosMenu = this.restaurante.obtenerStringMenuBase();
		String combos = this.restaurante.obtenerStringCombos();
		String menuCompleto = ingredientes + productosMenu + combos;
		System.out.println(menuCompleto);
	}

	private void iniciarPedidoNuevo() {
		System.out.println("INGRESE EL NOMBRE DEL CLIENTE");
		String nombreCliente = scanner.nextLine();
		System.out.println("INGRESE LA DIRECCION DEL CLIENTE");
		String direccionCliente = scanner.nextLine();
		restaurante.iniciarPedido(nombreCliente, direccionCliente);
		boolean mantenerAbiertoPedido = true;
		while (mantenerAbiertoPedido) {
			mostrarMenuPedido();
			int respuesta = obtenerRespuesta();
			mantenerAbiertoPedido = ejecutarOpcionMenuPedido(respuesta);
		}
	}

	private void mostrarMenuPedido() {
		System.out.println("¿QUE DESEA AGREGAR A SU PEDIDO?");
		System.out.println("1. Producto");
		System.out.println("2. Bebida");
		System.out.println("3. Combo");
		System.out.println("4. Cerrar Pedido");
	}

	private boolean ejecutarOpcionMenuPedido(int opcion) {
		boolean respuesta = true;
		if (opcion == 1) {
			opcionPedidoAgregarProducto();
		} else if (opcion == 2){
			opcionBebida();
		} else if (opcion == 3) {
			opcionPedidoAgregarCombo();
		} else if (opcion == 4) {
			opcionPedidoCerrarPedido();
			respuesta = false;
		} else {
			System.out.println("Ingresa una opcion valida");
		}
		return respuesta;
	}
	
	private void opcionBebida() {
		mostrarMenuBebida();
		int respuestaMenuBebida = obtenerRespuesta();
		if (respuestaMenuBebida == 0) {
			return;
		}
		Bebida bebidaAgregar = restaurante.getBebidas().get(respuestaMenuBebida - 1);
		restaurante.agregarProducto(bebidaAgregar);
	}
	
	private void mostrarMenuBebida() {
		System.out.println("¿QUE BEBIDA DESEA AGREGAR?");
		System.out.println("0. Volver");
		System.out.println(restaurante.obtenerStringBebidas());
	}
	private void opcionPedidoAgregarProducto() {
		mostrarMenuProducto();
		int respuestaMenuProducto = obtenerRespuesta();
		if (respuestaMenuProducto == 0) {
			return;
		}
		ProductoMenu productoBase = restaurante.getMenuBase().get(respuestaMenuProducto - 1);
		mostrarPreguntaModificacion();
		int respuestaPreguntaModificacion = obtenerRespuesta();
		if (respuestaPreguntaModificacion != 1) {
			restaurante.agregarProducto(productoBase);
			return;
		}
		ProductoAjustado productoAjustado = new ProductoAjustado(productoBase);
		boolean mantenerMenuAdicion = true;
		while (mantenerMenuAdicion) {
			mostrarMenuProductoAdicion();
			int respuestaMenuAdicion = obtenerRespuesta();
			if (respuestaMenuAdicion == 0) {
				break;
			}
			Ingrediente adicion = restaurante.getIngredientes().get(respuestaMenuAdicion - 1);
			productoAjustado.agregarIngrediente(adicion);
		}
		boolean mantenerMenuEliminacion = true;
		while (mantenerMenuEliminacion) {
			mostrarMenuProductoEliminacion();
			int respuestaMenuEliminacion = obtenerRespuesta();
			if (respuestaMenuEliminacion == 0) {
				break;
			}
			Ingrediente eliminacion = restaurante.getIngredientes().get(respuestaMenuEliminacion - 1);
			productoAjustado.quitarIngrediente(eliminacion);
		}
		restaurante.agregarProducto(productoAjustado);
	}

	private void mostrarMenuProducto() {
		System.out.println("¿QUE PRODUCTO DESEA AGREGAR?");
		System.out.println("0. Volver");
		System.out.println(restaurante.obtenerStringMenuBase());
	}

	private void mostrarPreguntaModificacion() {
		System.out.println("¿DESEA MODIFICAR SU PRODUCTO?");
		System.out.println("1. Si");
		System.out.println("2. No");
	}

	private void mostrarMenuProductoAdicion() {
		System.out.println("¿DESEA AÑADIRLE ALGO A SU PRODUCTO?");
		System.out.println("0. Seguir");
		System.out.println(restaurante.obtenerStringIngredientes());
	}

	private void mostrarMenuProductoEliminacion() {
		System.out.println("¿DESEA QUITARLE ALGO A SU PRODUCTO?");
		System.out.println("0. Seguir");
		System.out.println(restaurante.obtenerStringIngredientes());
	}

	private void opcionPedidoAgregarCombo() {
		mostrarMenuCombo();
		int respuestaMenuCombo = obtenerRespuesta();
		if (respuestaMenuCombo == 0) {
			return;
		}
		Combo comboAgregar = restaurante.getCombos().get(respuestaMenuCombo - 1);
		restaurante.agregarProducto(comboAgregar);
	}

	private void mostrarMenuCombo() {
		System.out.println("¿QUE COMBO DESEA AGREGAR?");
		System.out.println("0. Volver");
		System.out.println(restaurante.obtenerStringCombos());
	}

	private void opcionPedidoCerrarPedido() {
		restaurante.cerrarYGuardarPedido();
	}

	private int obtenerRespuesta() {
		int respuesta = Integer.parseInt(scanner.nextLine());
		return respuesta;
	}
}

package uniandes.dpoo.taller2.excepciones;

public class ValorPedidoExcedido extends Exception {

	private static final long serialVersionUID = 1L;
	
    public ValorPedidoExcedido() {
        super("El valor del pedido ha excedido el límite permitido");
    }
}

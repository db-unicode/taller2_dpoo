package uniandes.dpoo.taller2.excepciones;

public class ProductoRepetidoException extends HamburguesaException {
    private static final long serialVersionUID = 1L;

	public ProductoRepetidoException(String mensaje) {
        super(mensaje);
    }
}

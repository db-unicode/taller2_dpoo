package uniandes.dpoo.taller2.excepciones;

public class IngredienteRepetidoException extends HamburguesaException {
    private static final long serialVersionUID = 1L;

	public IngredienteRepetidoException(String mensaje) {
        super(mensaje);
    }
}

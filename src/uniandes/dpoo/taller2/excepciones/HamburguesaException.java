package uniandes.dpoo.taller2.excepciones;

public abstract class HamburguesaException extends Exception {

	private static final long serialVersionUID = 1L;
		
    public HamburguesaException(String mensaje) {
        super(mensaje);
    }
}

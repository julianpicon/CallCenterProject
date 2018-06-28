package co.com.almundo.callcenter.exception;

/**
 * 
 * Excepcion no empleados disponibles.
 *
 */
public class NoEmployeesException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public NoEmployeesException(String message) {
        super(message);
    }

}

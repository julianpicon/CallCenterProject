package co.com.almundo.callcenter.exception;

public class NoEmployeesException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public NoEmployeesException(String message) {
        super(message);
    }

}

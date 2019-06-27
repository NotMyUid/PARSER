package lab11_05_06.visitors.typechecking;

@SuppressWarnings("serial")
public class TypecheckerException extends RuntimeException {

	public TypecheckerException() {
	}

	public TypecheckerException(String found, String expected) {
		this("Found " + found + ", expected " + expected);
	}

	public TypecheckerException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TypecheckerException(String message, Throwable cause) {
		super(message, cause);
	}

	public TypecheckerException(String message) {
		super(message);
	}

	public TypecheckerException(Throwable cause) {
		super(cause);
	}

}

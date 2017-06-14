package br.com.products.exception;

public abstract class AbstractAPIException extends Exception {

	private static final long serialVersionUID = -6948154113387419329L;

	public AbstractAPIException() {
		super();
	}

	public AbstractAPIException(final String message) {
		super(message);
	}

	public AbstractAPIException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public AbstractAPIException(final Throwable cause) {
		super(cause);
	}
}

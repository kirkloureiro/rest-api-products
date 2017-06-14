package br.com.products.exception;

public class InfrastructureException extends AbstractAPIException {

	private static final long serialVersionUID = 4554965988134922485L;

	public InfrastructureException() {
		super();
	}

	public InfrastructureException(final String message) {
		super(message);
	}

	public InfrastructureException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public InfrastructureException(final Throwable cause) {
		super(cause);
	}
}

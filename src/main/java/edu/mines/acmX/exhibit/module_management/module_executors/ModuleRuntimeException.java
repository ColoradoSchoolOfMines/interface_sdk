package edu.mines.acmX.exhibit.module_management.module_executors;

public class ModuleRuntimeException extends RuntimeException {

	public ModuleRuntimeException() {
		
	}

	public ModuleRuntimeException(String message) {
		super(message);
	}

	public ModuleRuntimeException(Throwable cause) {
		super(cause);
	}

	public ModuleRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ModuleRuntimeException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

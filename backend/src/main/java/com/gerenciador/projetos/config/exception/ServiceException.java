package com.gerenciador.projetos.config.exception;

public class ServiceException extends Exception {
    private static final long serialVersionUID = 6674326310966166054L;

    public ServiceException(String message) {
        super(message);
    }
}

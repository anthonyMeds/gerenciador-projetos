package com.gerenciador.projetos.config.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.DateTimeException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("Invalid argument provided: {}", ex.getMessage());
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Requisição possui campo inválido. Faça o preenchimento correto e tente novamente");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotFoundException(EntityNotFoundException ex) {
        logger.error("Entity not found: {}", ex.getMessage());
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Não foi possível obter os dados.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleArgumentNotValid(MethodArgumentNotValidException manve) {
        logger.error("Unexpected error occurred: {}", manve.getMessage());
        String msgTecnica = manve.getBindingResult().getFieldError().getField() + ": " + manve.getBindingResult().getFieldError().getDefaultMessage();
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error",msgTecnica);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        logger.error("Unexpected error occurred: {}", ex.getMessage());
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error","Não foi possível realizar a requisição. Tente novamente");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(DataIntegrityViolationException dive) {
        logger.error("Violação de integridade: {}", dive.getMessage());
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "As informações enviadas violam as restrições de integridade de dados");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }


    @ExceptionHandler({ServiceException.class})
    protected ResponseEntity<Map<String, String>> handleService(ServiceException se) {

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", se.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    protected ResponseEntity<Map<String, String>> handleHttpMessageNotReadable(HttpMessageNotReadableException hmnre, JsonMappingException jme) throws Throwable {
        StringBuilder msgTecnica = (new StringBuilder(((JsonMappingException.Reference)jme.getPath().get(0)).getFieldName())).append(": ");

        try {
            throw hmnre.getRootCause();
        } catch (DateTimeException var5) {
            msgTecnica.append("data inválida");
        } catch (Exception var6) {
            msgTecnica.append("valor inválido");
        }

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", String.valueOf(msgTecnica));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<Map<String, String>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException matme) throws Throwable {
        StringBuilder msgTecnica = (new StringBuilder(matme.getName())).append(": ");

        try {
            throw matme.getRootCause();
        } catch (DateTimeException var4) {
            msgTecnica.append("data inválida");
        } catch (Exception var5) {
            msgTecnica.append("deve ser do tipo ").append(matme.getRequiredType().getName());
        }

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", String.valueOf(msgTecnica));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


}

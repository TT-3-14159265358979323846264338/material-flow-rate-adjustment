package com.example.material_flow_rate_adjustment.errorhandling;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
	/**
	 * Validに適合しない場合、エラー番号422を返却
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
    	Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
        	String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(errors);
	}
    
	/**
	 * 与えられたデータに不備があった場合、エラー番号422を返却
	 */
	@ExceptionHandler(SentenceException.class)
	public ResponseEntity<Map<String, String>> handleSentenceException(SentenceException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(createErrorMap(e));
	}
	
	/**
	 * 不備によりデータベース処理ができない時、エラー番号409を返却
	 */
	@ExceptionHandler(DataBaseException.class)
	public ResponseEntity<Map<String, String>> handleDataBaseException(DataBaseException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(createErrorMap(e));
	}
	
	/**
	 * データベースに存在しない時、エラー番号404を返却
	 */
	@ExceptionHandler(NotFindException.class)
	public ResponseEntity<Map<String, String>> handleNotFindException(NotFindException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorMap(e));
	}
	
	<T extends RuntimeException> Map<String, String> createErrorMap(T e){
		Map<String, String> errors = new HashMap<>();
		errors.put("error", e.getMessage());
		return errors;
	}
}
package com.example.material_flow_rate_adjustment.errorhandling;

public class NotFindException extends RuntimeException{
	public NotFindException(String message) {
		super(message);
	}
}
package com.example.material_flow_rate_adjustment.errorhandling;

public class DataBaseException extends RuntimeException{
	public DataBaseException(String message) {
		super(message);
	}
}
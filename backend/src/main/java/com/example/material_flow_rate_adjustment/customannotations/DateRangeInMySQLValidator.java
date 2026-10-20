package com.example.material_flow_rate_adjustment.customannotations;

import java.time.LocalDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.example.material_flow_rate_adjustment.errorhandling.SentenceException;

public class DateRangeInMySQLValidator implements ConstraintValidator<ValidDateInMySQLNullable, LocalDate>{
	private LocalDate min;
	private LocalDate max;
	
	@Override
	public void initialize(ValidDateInMySQLNullable constraintAnnotation) {
		try {
			min = LocalDate.parse(constraintAnnotation.min());
			max = LocalDate.parse(constraintAnnotation.max());
		}catch(Exception e) {
			throw new SentenceException("日付範囲を取り込めませんでした");
		}
	}

	@Override
	public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
		if(value == null) {
			return true;
		}
		return !value.isBefore(min) && !value.isAfter(max);
	}
}
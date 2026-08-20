package com.example.material_flow_rate_adjustment.customannotations;

import java.util.stream.Stream;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.example.material_flow_rate_adjustment.savedata.maindata.AccountRole;

public class RoleValidator implements ConstraintValidator<ValidRole, String>{
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return Stream.of(AccountRole.values()).anyMatch(i -> i.name().equals(value));
	}
}
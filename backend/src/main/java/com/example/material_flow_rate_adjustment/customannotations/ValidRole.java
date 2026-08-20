package com.example.material_flow_rate_adjustment.customannotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;

@NotNull(message = "権限は必須入力です")
@Documented
@Constraint(validatedBy = {RoleValidator.class})
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRole {
	String message() default "存在しない権限が入力されました";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
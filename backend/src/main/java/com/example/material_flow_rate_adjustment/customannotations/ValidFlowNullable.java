package com.example.material_flow_rate_adjustment.customannotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;

@DecimalMin(value = "0", message = "負の値は入力できません")
@DecimalMax(value = "999999999.999", message = "999999999.999より大きな値は入力できません")
@Digits(integer = 9, fraction = 3, message = "小数点第3以まで入力しか入力できません")
@Documented
@Constraint(validatedBy = {})
@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFlowNullable {
	String message() default "";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
package com.softserve.if072.common.model.validation.product;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation for validation of selected units
 *
 * @author Vitaliy Malisevych
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UnitValidator.class)

public @interface ValidUnit {

    String message() default "Select the Unit";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}

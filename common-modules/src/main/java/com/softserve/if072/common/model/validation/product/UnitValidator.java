package com.softserve.if072.common.model.validation.product;

import com.softserve.if072.common.model.Unit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The class implements validation of selected units
 *
 * @author Vitaliy Malisevych
 */
public class UnitValidator implements ConstraintValidator<ValidUnit, Unit> {
    @Override
    public void initialize(ValidUnit validUnit) {

    }

    @Override
    public boolean isValid(Unit unit, ConstraintValidatorContext constraintValidatorContext) {

        return unit.getId()>0;

    }
}

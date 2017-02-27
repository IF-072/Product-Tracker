package com.softserve.if072.common.model.validation.product;

import com.softserve.if072.common.model.Category;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The class implements validation of selected categories
 *
 * @author Vitaliy Malisevych
 */
public class CategoryValidator implements ConstraintValidator<ValidCategory, Category> {
    @Override
    public void initialize(ValidCategory validCategory) {

    }

    @Override
    public boolean isValid(Category category, ConstraintValidatorContext constraintValidatorContext) {

        return category.getId() > 0;

    }
}

package com.api.denuncieAqui.features.categories.validations;

import com.api.denuncieAqui.infra.exceptionsHandler.validationError.ValidationErrorException;
import com.api.denuncieAqui.features.categories.types.CreateCategoryType;
import org.springframework.stereotype.Component;

@Component
public class ValidateEmptyOrNullName implements CategoryValidator {

    @Override
    public void validate(CreateCategoryType data) {
        if (data.name() == null || data.name().isBlank()) {
            throw new ValidationErrorException("O nome da categoria não pode estar vazio ou nulo.");
        }
    }
}

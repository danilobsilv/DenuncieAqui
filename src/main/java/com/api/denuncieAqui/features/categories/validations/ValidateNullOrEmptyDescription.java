package com.api.denuncieAqui.features.categories.validations;

import com.api.denuncieAqui.infra.exceptionsHandler.validationError.ValidationErrorException;
import com.api.denuncieAqui.features.categories.types.CreateCategoryType;
import org.springframework.stereotype.Component;

@Component
public class ValidateNullOrEmptyDescription implements CategoryValidator{
    @Override
    public void validate(CreateCategoryType data) {
        if (data.description() == null || data.description().isBlank()) {
            throw new ValidationErrorException("A descrição não pode estar vazia ou nula.");
        }
    }
}

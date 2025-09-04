package com.api.denuncieAqui.categories;

import com.api.denuncieAqui.infra.exceptionsHandler.validationError.ValidationErrorException;
import com.api.denuncieAqui.features.categories.types.CreateCategoryType;
import com.api.denuncieAqui.features.categories.validations.ValidateEmptyOrNullName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidateEmptyOrNullNameTest {

    private final ValidateEmptyOrNullName validator = new ValidateEmptyOrNullName();

    @Test
    void shouldThrowWhenNameIsNull() {
        CreateCategoryType type = new CreateCategoryType(null, "Descrição");
        assertThrows(ValidationErrorException.class, () -> validator.validate(type));
    }

    @Test
    void shouldThrowWhenNameIsBlank() {
        CreateCategoryType type = new CreateCategoryType("   ", "Descrição");
        assertThrows(ValidationErrorException.class, () -> validator.validate(type));
    }

    @Test
    void shouldPassWhenNameIsValid() {
        CreateCategoryType type = new CreateCategoryType("Categoria Válida", "Descrição");
        assertDoesNotThrow(() -> validator.validate(type));
    }
}

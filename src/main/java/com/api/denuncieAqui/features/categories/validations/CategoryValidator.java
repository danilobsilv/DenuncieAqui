package com.api.denuncieAqui.features.categories.validations;

import com.api.denuncieAqui.features.categories.types.CreateCategoryType;

public interface CategoryValidator {
    void validate(CreateCategoryType data);
}

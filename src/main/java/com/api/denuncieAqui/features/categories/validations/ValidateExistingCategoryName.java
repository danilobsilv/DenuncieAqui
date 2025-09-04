package com.api.denuncieAqui.features.categories.validations;

import com.api.denuncieAqui.infra.exceptionsHandler.resourceConflictException.ResourceConflictException;
import com.api.denuncieAqui.features.categories.CategoriesRepository;
import com.api.denuncieAqui.features.categories.types.CreateCategoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateExistingCategoryName implements CategoryValidator{

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Override
    public void validate(CreateCategoryType data) {
        if (categoriesRepository.existsByCategoryName(data.name())){
            throw new ResourceConflictException("Já há uma categoria com esse nome.");
        }
    }
}

package com.api.denuncieAqui.features.categories.types;

import com.api.denuncieAqui.features.categories.Categories;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CategoryResponseType(
        @NotNull
        UUID categoryId,

        @NotBlank
        String name,

        @NotBlank
        String description,

        @NotNull
        Boolean isActive
) {
    public CategoryResponseType(Categories data){
        this(
                data.getCategoryId(),
                data.getCategoryName(),
                data.getCategoryDescription(),
                data.isCategoryActive()
        );
    }
}

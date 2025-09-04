package com.api.denuncieAqui.features.categories;

import com.api.denuncieAqui.features.categories.types.CreateCategoryType;
import com.api.denuncieAqui.features.categories.types.UpdateCategoryType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "categories",
        indexes = @Index(name = "idx_categories_name", columnList = "categories_name"))
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "categories_id", updatable = false, nullable = false, columnDefinition = "char(36)")
    private UUID categoryId;

    @Column(name = "categories_name", nullable = false, unique = true, length = 100)
    private String categoryName;

    @Column(name = "categories_description", columnDefinition = "TEXT", nullable = false)
    private String categoryDescription;

    @Column(name = "categories_is_active", columnDefinition = "BOOLEAN", nullable = false)
    private boolean isCategoryActive;

    public Categories(CreateCategoryType data) {
        this.categoryName = data.name();
        this.categoryDescription = data.description();
    }

    @PrePersist
    public void prePersist() {
        this.isCategoryActive = true;
    }

    public void updateCategoryInformation(UpdateCategoryType data) {
        if (data.categoryName() != null) {
            this.categoryName = data.categoryName();
        }
        if (data.categoryDescription() != null) {
            this.categoryDescription = data.categoryDescription();
        }
    }
}

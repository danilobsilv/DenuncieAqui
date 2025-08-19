package com.api.denuncieAqui.features.categories;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories",
       indexes = @Index(name = "idx_categories_name", columnList = "categories_name"))
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "categories_id", updatable = false, nullable = false, columnDefinition = "char(36)")
    private String categoryId;

    @Column(name = "categories_name", nullable = false, unique = true, length = 100)
    private String categoryName;

    @Column(name = "categories_description", columnDefinition = "TEXT", nullable = false)
    private String categoryDescription;
}


package com.api.denuncieAqui.features.reports;


import com.api.denuncieAqui.features.admin.AdminUser;
import com.api.denuncieAqui.features.categories.Categories;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports", indexes = {
        @Index(name = "idx_status", columnList = "reports_status")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reports {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "reports_id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    private String id;

    @Column(name = "reports_protocol", length = 40, nullable = false, unique = true)
    private String protocol;

    @Column(name = "reports_title", length = 255, nullable = false)
    private String title;

    @Column(name = "reports_description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "reports_latitude", precision = 10, scale = 8, nullable = false)
    private BigDecimal latitude;

    @Column(name = "reports_longitude", precision = 11, scale = 8, nullable = false)
    private BigDecimal longitude;

    @Enumerated(EnumType.STRING)
    @Column(name = "reports_status", nullable = false)
    private ReportStatus status = ReportStatus.PENDING_REVIEW;

    @Column(name = "reports_occurrence_timestamp")
    private LocalDateTime occurrenceTimestamp;

    @Column(name = "reports_created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

    @Column(name = "reports_moderated_at")
    private LocalDateTime moderatedAt;

    @Column(name = "reports_rejection_justification", columnDefinition = "TEXT")
    private String rejectionJustification;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Categories category;

    @ManyToOne
    @JoinColumn(name = "moderator_id", nullable = false)
    private AdminUser moderator;

}

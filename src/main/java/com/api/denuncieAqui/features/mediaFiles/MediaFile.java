package com.api.denuncieAqui.features.mediaFiles;

import com.api.denuncieAqui.features.reports.Reports;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "media_files")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaFile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "media_files_id",  updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    private String id;

    @Column(name = "media_files_file_url", length = 512, nullable = false)
    private String fileUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_files_media_type", nullable = false)
    private MediaType mediaType;

    @Column(name = "media_files_uploaded_at", updatable = false, insertable = false)
    private LocalDateTime uploadedAt;

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private Reports report;
}
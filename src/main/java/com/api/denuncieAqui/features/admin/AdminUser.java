package com.api.denuncieAqui.features.admin;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "admin_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "admin_users_id", updatable = false, nullable = false, columnDefinition = "char(36)")
    private String id;

    @Column(name = "admin_users_name", length = 150, nullable = false)
    private String name;

    @Column(name = "admin_users_email", length = 150, nullable = false, unique = true)
    private String email;

    @Column(name = "admin_users_password", length = 255, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "admin_users_role", nullable = false)
    private AdminUserRole adminUserRole;

    @Column(name = "admin_users_is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "admin_users_created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;
}

package com.nexthood.notification_service.model;

import com.fasterxml.jackson.annotation.JsonTypeId;
import io.micrometer.core.annotation.Counted;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Data
@AllArgsConstructor
@Table(name="notifications")
@NoArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private String message;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;
    private OffsetDateTime sendAt;
    private String meta;
    private String status;
}

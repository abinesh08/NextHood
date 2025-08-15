package com.nexthood.notification_service.dto;

import com.nexthood.notification_service.model.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDto {
    private Long id;
    private Long userId;
    private String message;
    private NotificationType type;
    private OffsetDateTime sendAt;
    private String status;
    private String meta;
}

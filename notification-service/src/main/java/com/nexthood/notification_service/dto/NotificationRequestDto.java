package com.nexthood.notification_service.dto;

import com.nexthood.notification_service.model.NotificationType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationRequestDto {
    @NotNull
    private Long userId;
    @NotBlank
    private String message;
    @NotNull
    private NotificationType type;
}

package com.nexthood.notification_service.mapper;

import com.nexthood.notification_service.dto.NotificationRequestDto;
import com.nexthood.notification_service.dto.NotificationResponseDto;
import com.nexthood.notification_service.model.Notification;

import java.time.OffsetDateTime;

public class NotificationMapper {

    public static NotificationResponseDto toDto(Notification n){
        return NotificationResponseDto.builder()
                .id(n.getId())
                .userId(n.getUserId())
                .message(n.getMessage())
                .type(n.getType())
                .sendAt(n.getSendAt())
                .meta(n.getMeta())
                .status(n.getStatus())
                .build();
    }
    public static Notification toEntity(NotificationRequestDto request, String status, String meta) {
        return Notification.builder()
                .userId(request.getUserId())
                .message(request.getMessage())
                .type(request.getType())
                .sendAt(OffsetDateTime.now())
                .meta(meta)
                .status(status)
                .build();
    }
}

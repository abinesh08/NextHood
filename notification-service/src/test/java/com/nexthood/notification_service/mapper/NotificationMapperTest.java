package com.nexthood.notification_service.mapper;

import com.nexthood.notification_service.dto.NotificationRequestDto;
import com.nexthood.notification_service.dto.NotificationResponseDto;
import com.nexthood.notification_service.model.Notification;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static com.nexthood.notification_service.model.NotificationType.EMAIL;
import static com.nexthood.notification_service.model.NotificationType.SMS;
import static org.junit.jupiter.api.Assertions.*;

class NotificationMapperTest {
    @Test
    void testToDto(){
        Notification notification= Notification.builder()
                .id(1L)
                .userId(100L)
                .message("Test Message")
                .type(SMS)
                .sendAt(OffsetDateTime.now())
                .meta("Meta info")
                .status("SENT")
                .build();

        NotificationResponseDto dto= NotificationMapper.toDto(notification);
        assertNotNull(dto);
        assertEquals(notification.getId(), dto.getId());
        assertEquals(notification.getUserId(), dto.getUserId());
        assertEquals(notification.getMessage(), dto.getMessage());
        assertEquals(notification.getType(), dto.getType());
        assertEquals(notification.getSendAt(), dto.getSendAt());
        assertEquals(notification.getMeta(), dto.getMeta());
        assertEquals(notification.getStatus(), dto.getStatus());
    }

    @Test
    void testToEntity() {

        NotificationRequestDto request = NotificationRequestDto.builder()
                .userId(200L)
                .message("Hello World")
                .type(EMAIL)
                .build();

        Notification entity = NotificationMapper.toEntity(request, "QUEUED", "No error");

        assertNotNull(entity);
        assertEquals(request.getUserId(), entity.getUserId());
        assertEquals(request.getMessage(), entity.getMessage());
        assertEquals(request.getType(), entity.getType());
        assertNotNull(entity.getSendAt());
        assertEquals("QUEUED", entity.getStatus());
        assertEquals("No error", entity.getMeta());
    }
}
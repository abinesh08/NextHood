package com.nexthood.notification_service.controller;

import com.nexthood.notification_service.dto.NotificationRequestDto;
import com.nexthood.notification_service.dto.NotificationResponseDto;
import com.nexthood.notification_service.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping
    public NotificationResponseDto send(@Valid @RequestBody NotificationRequestDto requestDto){
        return notificationService.sendNotification(requestDto);
    }
}

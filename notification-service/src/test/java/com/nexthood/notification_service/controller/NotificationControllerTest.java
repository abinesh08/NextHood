package com.nexthood.notification_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexthood.common_security.JwtFilter;
import com.nexthood.notification_service.dto.NotificationRequestDto;
import com.nexthood.notification_service.dto.NotificationResponseDto;
import com.nexthood.notification_service.model.NotificationType;
import com.nexthood.notification_service.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
@AutoConfigureMockMvc(addFilters = false)
class NotificationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private NotificationService notificationService;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private JwtFilter jwtFilter;
    @Test
    void send_ShouldReturnNotificationResponse() throws Exception{
        NotificationRequestDto requestDto= NotificationRequestDto.builder()
                .userId(23L)
                .message("Hello")
                .type(NotificationType.EMAIL)
                .build();

        NotificationResponseDto responseDto= NotificationResponseDto.builder()
                .id(1L)
                .userId(23L)
                .message("Hello")
                .type(NotificationType.EMAIL)
                .status("SENT")
                .meta("")
                .build();
        Mockito.when(notificationService.sendNotification(any(NotificationRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/notification")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.userId").value(23L))
                .andExpect(jsonPath("$.message").value("Hello"))
                .andExpect(jsonPath("$.type").value("EMAIL"))
                .andExpect(jsonPath("$.status").value("SENT"));
    }

}
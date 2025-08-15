package com.nexthood.notification_service.service;

import com.nexthood.notification_service.dto.NotificationRequestDto;
import com.nexthood.notification_service.dto.NotificationResponseDto;
import com.nexthood.notification_service.dto.UserDto;
import com.nexthood.notification_service.model.Notification;
import com.nexthood.notification_service.model.NotificationType;
import com.nexthood.notification_service.repository.NotificationRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private HttpServletRequest httpServletRequest;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendNotification_Email_Success(){
        NotificationRequestDto requestDto= NotificationRequestDto.builder()
                .userId(1L)
                .message("Hello")
                .type(NotificationType.EMAIL)
                .build();

        UserDto userDto=new UserDto();
        userDto.setEmail("test@example.com");

        when(httpServletRequest.getHeader("Authorization")).thenReturn("Bearer token");
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class),eq(UserDto.class)))
                .thenReturn(ResponseEntity.ok(userDto));
        when(notificationRepository.save(any(Notification.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        NotificationResponseDto responseDto= notificationService.sendNotification(requestDto);
        assertNotNull(responseDto);
        assertEquals("SENT", responseDto.getStatus());
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendNotification_SMS_Success(){
        NotificationRequestDto requestDto= NotificationRequestDto.builder()
                .userId(1L)
                .message("Hello")
                .type(NotificationType.SMS)
                .build();
        UserDto userDto=new UserDto();
        userDto.setEmail("test@Email.com");
        userDto.setPhoneNumber("99999999");
        when(httpServletRequest.getHeader("Authorization")).thenReturn("Bearer token");
        when(restTemplate.exchange(contains("user/id"),eq(HttpMethod.GET),any(HttpEntity.class),eq(UserDto.class)))
                .thenReturn(ResponseEntity.ok(userDto));
        when(restTemplate.exchange(contains("fast2sms"),eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenReturn(ResponseEntity.ok("SMS sent Successfully"));
        when(notificationRepository.save(any(Notification.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        NotificationResponseDto responseDto= notificationService.sendNotification(requestDto);

        assertNotNull(responseDto);
        assertEquals("SENT", responseDto.getStatus());
        assertTrue(responseDto.getMeta().contains("SMS sent"));

    }
    @Test
    void testSendNotification_MissingAuthHeader(){
        NotificationRequestDto requestDto= NotificationRequestDto.builder()
                .userId(1L)
                .message("Hello")
                .type(NotificationType.EMAIL)
                .build();

        when(httpServletRequest.getHeader("Authorization")).thenReturn(null);
        RuntimeException exception= assertThrows(RuntimeException.class,()-> notificationService.sendNotification(requestDto));
        assertEquals("Missing Authorization header", exception.getMessage());
    }
}
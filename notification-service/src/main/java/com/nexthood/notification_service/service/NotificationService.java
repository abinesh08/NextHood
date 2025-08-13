package com.nexthood.notification_service.service;

import com.nexthood.notification_service.dto.NotificationRequestDto;
import com.nexthood.notification_service.dto.NotificationResponseDto;
import com.nexthood.notification_service.dto.UserDto;
import com.nexthood.notification_service.mapper.NotificationMapper;
import com.nexthood.notification_service.model.Notification;
import com.nexthood.notification_service.model.NotificationType;
import com.nexthood.notification_service.repository.NotificationRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final JavaMailSender mailSender;
    private final RestTemplate restTemplate;
    private final HttpServletRequest httpServletRequest;

    private final String USER_SERVICE_URL="http://localhost:8086/user/id/";

    public NotificationResponseDto sendNotification(NotificationRequestDto requestDto){
        String token=httpServletRequest.getHeader("Authorization");
        if(token==null || token.isBlank()){
            throw  new RuntimeException("Missing Authorization header");
        }

        if (!token.startsWith("Bearer ")) {
            token = "Bearer " + token;
        }

        HttpHeaders headers=new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<UserDto> response= restTemplate.exchange(USER_SERVICE_URL + requestDto.getUserId(),
                HttpMethod.GET, entity,UserDto.class);

        UserDto user=response.getBody();
        if(user==null || user.getEmail()==null){
            throw new RuntimeException("User not found or email missing");
        }

        String status="FAILED";
        String meta="";
        if(requestDto.getType()== NotificationType.EMAIL){
            try{
                SimpleMailMessage message= new SimpleMailMessage();
                message.setTo(user.getEmail());
                message.setSubject("Notification - NextHood");
                message.setText(requestDto.getMessage());
                mailSender.send(message);
                status="SENT";

            }catch (Exception e){
                meta= e.getMessage();
            }
        }
        Notification notification = NotificationMapper.toEntity(requestDto, status, meta);
        notificationRepository.save(notification);
        return NotificationMapper.toDto(notification);

    }
}

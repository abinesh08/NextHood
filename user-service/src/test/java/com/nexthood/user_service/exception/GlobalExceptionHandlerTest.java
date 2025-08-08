package com.nexthood.user_service.exception;

import com.nexthood.common_security.JwtUtil;
import com.nexthood.user_service.controller.UserController;
import com.nexthood.user_service.service.UserService;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
@WebMvcTest(controllers = UserController.class)
@Import({GlobalExceptionHandler.class})
class GlobalExceptionHandlerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtUtil jwtUtil;

    @Test
    @WithMockUser
    void testHandleResourceNotFoundException() throws Exception {
        Mockito.when(userService.getUserByName(anyString()))
                .thenThrow(new ResourceNotFoundException("User not found : test"));
        mockMvc.perform(get("/user/name/Test"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found : test"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }
    @Test
    @WithMockUser
    void testHandleGenericException() throws Exception{
        Mockito.when(userService.getUserByName(anyString()))
                .thenThrow(new RuntimeException("Something went wrong"));
        mockMvc.perform(get("/user/name/Test"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Something went wrong"))
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.error").value("Internal Server Error"));
    }

}

package com.openclassrooms.starterjwt.security.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = JwtUtils.class)
class JwtUtilsTest {

    @Autowired
    private JwtUtils jwtUtils;

    @MockBean
    private Authentication authentication;

    @BeforeEach
    public void setup(){
        UserDetailsImpl userDetailsImpl = UserDetailsImpl
            .builder()
            .id(1L)
            .username("username")
            .lastName("lastname")
            .firstName("firstname")
            .password("password")
            .build();

        when(authentication.getPrincipal()).thenReturn(userDetailsImpl);
        when(authentication.isAuthenticated()).thenReturn(false);
    }

    @Test
    void givenAuthentication(){
        String jwtToken = jwtUtils.generateJwtToken(authentication);
        assertNotNull(jwtToken);
        assertEquals("username", jwtUtils.getUserNameFromJwtToken(jwtToken));
        assertTrue(jwtUtils.validateJwtToken(jwtToken));
    }


}

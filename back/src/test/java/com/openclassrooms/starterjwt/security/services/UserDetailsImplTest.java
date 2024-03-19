package com.openclassrooms.starterjwt.security.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

class UserDetailsImplTest {
    
    @MockBean
    private UserDetailsImpl userDetails;

    @BeforeEach
    public void setup() {
        userDetails = UserDetailsImpl.builder()
                .id(1L)
                .username("testuser")
                .firstName("John")
                .lastName("Doe")
                .admin(false)
                .password("password")
                .build();
    }

    @Test
    void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertEquals(new HashSet<GrantedAuthority>(), authorities);
    }

    @Test
    void testIsEnabled() {
        UserDetails userDetails = UserDetailsImpl.builder().id(1L).build();
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void testIsCredentialsNonExpired() {
        UserDetails userDetails = UserDetailsImpl.builder().id(1L).build();
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    void testEquals() {
        UserDetailsImpl user1 = UserDetailsImpl.builder().id(1L).build();
        UserDetailsImpl user2 = UserDetailsImpl.builder().id(1L).build();
        UserDetailsImpl user3 = UserDetailsImpl.builder().id(2L).build();

        assertEquals(user1, user2);
        assertEquals(user2, user1);
        assertEquals(user1, user1);
        assertEquals(user2, user2);
        assertEquals(user1.equals(user3), false);
    }


}

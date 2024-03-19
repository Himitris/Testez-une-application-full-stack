package com.openclassrooms.starterjwt.security.jwt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.openclassrooms.starterjwt.security.services.UserDetailsServiceImpl;


@SpringBootTest(classes = AuthTokenFilter.class)
class AuthTokenFilterTest {

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthTokenFilter authTokenFilter;

    @Test
    void testDoFilterInternal() throws Exception {
        String jwt = "sampleJwtToken";
        String username = "testUser";

        UserDetails userDetails = User.withUsername(username).password("password").authorities("ROLE_USER").build();
        when(jwtUtils.validateJwtToken(jwt)).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken(jwt)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);

        // Utilisation de la réflexion pour accéder à la méthode protégée
        AuthTokenFilter.class.getDeclaredMethod("doFilterInternal", HttpServletRequest.class, HttpServletResponse.class, FilterChain.class)
            .invoke(authTokenFilter, request, response, filterChain);

        verify(userDetailsService, times(1)).loadUserByUsername(username);
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_InvalidJwt() throws Exception {
        String invalidJwt = "invalidJwtToken";

        when(jwtUtils.validateJwtToken(invalidJwt)).thenReturn(false);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + invalidJwt);

        AuthTokenFilter.class.getDeclaredMethod("doFilterInternal", HttpServletRequest.class, HttpServletResponse.class, FilterChain.class)
            .invoke(authTokenFilter, request, response, filterChain);

        verify(userDetailsService, never()).loadUserByUsername(any());
        verify(filterChain, times(1)).doFilter(request, response);
    }
}

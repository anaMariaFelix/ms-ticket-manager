package com.anamariafelix.ms_ticket_manager.service.jwt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.anamariafelix.ms_ticket_manager.enums.Role;
import com.anamariafelix.ms_ticket_manager.jwt.JwtToken;
import com.anamariafelix.ms_ticket_manager.jwt.JwtUserDetailsService;
import com.anamariafelix.ms_ticket_manager.jwt.JwtUtils;
import com.anamariafelix.ms_ticket_manager.model.User;
import com.anamariafelix.ms_ticket_manager.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class JwtUserDetailsServiceTest {

    @InjectMocks
    private JwtUserDetailsService jwtUserDetailsService;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_shouldReturnUserDetails_whenUserExists() {

        String email = "user@gmail.com";
        User user = new User();
        user.setEmail(email);
        user.setPassword("123456");
        user.setRole(Role.CLIENT);
        user.setCpf("12345678900");

        when(userService.findByEmail(email)).thenReturn(user);

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(Role.CLIENT.name())));

        verify(userService).findByEmail(email);
    }

    @Test
    void loadUserByUsername_shouldThrowUsernameNotFoundException_whenUserNotFound() {
        String email = "notfound@gmail.com";

        when(userService.findByEmail(email)).thenThrow(new UsernameNotFoundException("User not found"));

        assertThrows(UsernameNotFoundException.class, () -> {
            jwtUserDetailsService.loadUserByUsername(email);
        });

        verify(userService).findByEmail(email);
    }

    @Test
    void getTokenAuthenticated_shouldReturnJwtToken_whenUserExists() {
        String email = "tokenuser@gmail.com";
        User user = new User();
        user.setEmail(email);
        user.setRole(Role.ADMIN);

        when(userService.findByEmail(email)).thenReturn(user);

        JwtToken token = jwtUserDetailsService.getTokenAuthenticated(email);

        assertNotNull(token);
        assertNotNull(token.getToken());

        String emailFromToken = JwtUtils.getUserNameFromTokem(token.getToken());
        assertEquals(email, emailFromToken);

        verify(userService).findByEmail(email);
    }
}
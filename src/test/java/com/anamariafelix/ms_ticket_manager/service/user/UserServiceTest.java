package com.anamariafelix.ms_ticket_manager.service.user;

import com.anamariafelix.ms_ticket_manager.exception.UniqueViolationException;
import com.anamariafelix.ms_ticket_manager.exception.UserNotFoundException;
import com.anamariafelix.ms_ticket_manager.model.User;
import com.anamariafelix.ms_ticket_manager.repository.UserRepository;
import com.anamariafelix.ms_ticket_manager.service.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void create_ShouShouldEncodePasswordAndSaveUser_WhenEmailNotExists() {
        User user = new User();
        user.setEmail("userTest@gmail.com");
        user.setPassword("123456");

        String encodedPassword = "encoded123456";
        when(passwordEncoder.encode("123456")).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User savedUser = userService.create(user);

        assertNotNull(savedUser);
        assertEquals(encodedPassword, savedUser.getPassword());
        verify(passwordEncoder).encode("123456");
        verify(userRepository).save(user);
    }

    @Test
    void create_ShouldThrowUniqueViolationException_WhenDuplicateKey() {
        User user = new User();
        user.setEmail("emailduplicate@gmail.com");
        user.setPassword("123456");

        when(passwordEncoder.encode("123456")).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenThrow(new DuplicateKeyException("Email Duplicate"));

        UniqueViolationException ex = assertThrows(
                UniqueViolationException.class,
                () -> userService.create(user)
        );

        assertEquals("Email or CPF already registered!", ex.getMessage());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void findByEmail_ShouldReturnUser_WhenEmailExists() {
        User user = new User();
        user.setEmail("exists@gmail.com");

        when(userRepository.findByEmail("exists@email.com")).thenReturn(user);

        User foundUser = userService.findByEmail("exists@email.com");

        assertNotNull(foundUser);
        assertEquals("exists@gmail.com", foundUser.getEmail());
        verify(userRepository, times(1)).findByEmail("exists@email.com");
    }

    @Test
    void findByCpf_ShouldReturnUser_WhenCpfExists() {
        User user = new User();
        user.setEmail("cpf@gmail.com");

        when(userRepository.findByCpf("12345678900")).thenReturn(Optional.of(user));

        User foundUser = userService.findByCpf("12345678900");

        assertNotNull(foundUser);
        assertEquals("cpf@gmail.com", foundUser.getEmail());
        verify(userRepository, times(1)).findByCpf("12345678900");
    }

    @Test
    void findByCpf_ShouldThrowException_WhenCpfNotFound() {
        when(userRepository.findByCpf("00000000000")).thenReturn(Optional.empty());

        UserNotFoundException ex = assertThrows(UserNotFoundException.class,
                () -> userService.findByCpf("00000000000"));

        assertTrue(ex.getMessage().contains("Client with cpf = 00000000000 not found"));
        verify(userRepository, times(1)).findByCpf("00000000000");
    }
}

package com.surest.member.security;

import com.surest.member.entity.Role;
import com.surest.member.entity.User;
import com.surest.member.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService service;

    @Test
    void loadUserByUsername_success() {
        Role role = new Role();
        role.setName("ADMIN");

        User user = new User();
        user.setUsername("john");
        user.setPasswordHash("pass");
        user.setRole(role);

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(user));

        UserDetails details = service.loadUserByUsername("john");

        assertTrue(details.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void loadUserByUsername_notFound() {
        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> service.loadUserByUsername("john")
        );
    }
}

package com.surest.member.security;

import com.surest.member.entity.User;
import com.surest.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) {
        System.out.println(">>> LOGIN USERNAME RECEIVED = [" + username + "]");
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println(">>> DB USERNAME = [" + user.getUsername() + "]");
        System.out.println(">>> DB PASSWORD HASH = [" + user.getPasswordHash() + "]");
        System.out.println(">>> DB ROLE = [" + user.getRole().getName() + "]");
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority("ROLE_" + user.getRole().getName());
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPasswordHash(),
                List.of(authority)
        );
    }
}
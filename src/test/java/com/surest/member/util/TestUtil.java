package com.surest.member.util;

import com.surest.member.entity.Role;
import com.surest.member.entity.User;
import com.surest.member.repository.RoleRepository;
import com.surest.member.repository.UserRepository;
import com.surest.member.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestUtil {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String createTestUser(String username, String roleName) {

        // 1️⃣ Create / fetch role
        Role role = roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setName(roleName);
                    return roleRepository.save(r);
                });

        // 2️⃣ Create user entity
        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(passwordEncoder.encode("password"));
        user.setRole(role);

        userRepository.save(user);

        // 3️⃣ Build UserDetails (THIS IS THE KEY FIX)
        org.springframework.security.core.userdetails.User userDetails =
                new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPasswordHash(),
                        List.of(new SimpleGrantedAuthority("ROLE_" + roleName))
                );

        // 4️⃣ Generate JWT using JwtUtil
        return jwtUtil.generateToken(userDetails);
    }
}

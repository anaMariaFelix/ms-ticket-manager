package com.anamariafelix.ms_ticket_manager.jwt;

import com.anamariafelix.ms_ticket_manager.model.User;
import com.anamariafelix.ms_ticket_manager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private  final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userService.findByEmail(email);
        log.info("log role {}", user.getRole());
        return new JwtUserDetails(user);
    }

    public JwtToken getTokenAuthenticated(String email){
        User user = userService.findByEmail(email);
        return JwtUtils.createToken(email, user.getRole().name());
    }
}

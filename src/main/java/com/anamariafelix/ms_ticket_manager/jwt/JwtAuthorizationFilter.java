package com.anamariafelix.ms_ticket_manager.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService userDetailsService;

    private static final List<Pattern> PUBLIC_PATTERNS = List.of(
            Pattern.compile("^/api/v1/user/create-user$"),
            Pattern.compile("^/api/v1/auth$"),
            Pattern.compile("^/api/v1/events/get-event/[^/]+$")
    );


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();

        for (Pattern pattern : PUBLIC_PATTERNS) {
            if (pattern.matcher(path).matches()) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        final String token = request.getHeader(JwtUtils.JWT_AUTHORIZATION);

        if(token == null || !token.startsWith(JwtUtils.JWT_BEARER)){
            log.info("INFO: JWT Token is null, empty or does not start with 'Bearer'.");
            filterChain.doFilter(request,response);
            return;
        }

        if(!JwtUtils.isTokenValid(token)){
            log.warn("WARN: JWT Token is invalid or expired");
            filterChain.doFilter(request,response);
            return;
        }

        String email = JwtUtils.getUserNameFromTokem(token);

        toAuthentication(request, email);
        filterChain.doFilter(request, response);
    }

    private void toAuthentication(HttpServletRequest request, String email) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken
                .authenticated(userDetails, null, userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}

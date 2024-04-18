package tetravex.server.security;

import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.filter.OncePerRequestFilter;
import tetravex.server.security.token.JwtCore;

import java.io.IOException;



@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtCore jwtCore;

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = null;
        String username = null;
        UserDetails userDetails = null;
        UsernamePasswordAuthenticationToken authentication = null;

        System.out.println("--------------------------TokenFilter START");
        System.out.println(  SecurityContextHolder.getContext().toString());

        try {
            String authHeader = request.getHeader("Authorization");
            String url = request.getRequestURI();

            System.out.println(url);

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                jwt = authHeader.substring(7);



                if (jwt != null) {
                    System.out.println("--------------------------TOKEN IS " + jwt);
                    try {
                        username = jwtCore.getNameFromJwtToken(jwt);
                    } catch (Exception e) {
                        e.printStackTrace();
                        SecurityContextHolder.getContext().setAuthentication(null);
                        response.setStatus(403);
                    }

                    userDetails = userDetailsService.loadUserByUsername(username);

                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                                userDetails.getPassword(), userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } else SecurityContextHolder.getContext().setAuthentication(null);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(403);
        }

        filterChain.doFilter(request, response);
    }
}
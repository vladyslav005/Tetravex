package tetravex.server.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import tetravex.data.entity.User;
import tetravex.server.dto.SignInRequest;
import tetravex.server.dto.SignUpRequest;
import tetravex.server.security.token.JwtCore;
import tetravex.server.security.user.UserRepository;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;


@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtCore jwtCore;


    @PostMapping("/signin")
    ResponseEntity<?> signin(@RequestBody SignInRequest signInRequest, HttpServletRequest req) {
        Authentication auth = null;

        System.out.println(signInRequest);
        try {
            auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signInRequest.getUsername(), signInRequest.getPassword()
            ));

        } catch (BadCredentialsException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

//        SecurityContextHolder.getContext().setAuthentication(auth);

        String token = jwtCore.generateToken(auth);
        HttpSession session = req.getSession(true);
//        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        return ResponseEntity.ok(token);
    }

    @PostMapping("/signup")
    ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsUserByUsername(signUpRequest.getUsername())) {
            return  ResponseEntity.badRequest().body("Email already in use");
        }

        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        userRepository.save(user);

        return  ResponseEntity.ok().body("User registered successfully");
    }




}

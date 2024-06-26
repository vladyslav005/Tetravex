package tetravex.server.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tetravex.data.entity.User;
import tetravex.server.dto.SignInRequest;
import tetravex.server.dto.SignUpRequest;
import tetravex.server.security.token.JwtCore;
import tetravex.server.security.user.UserRepository;
import tetravex.server.security.user.UserService;


@ControllerAdvice
@RestController
@RequestMapping("/auth")
public class SecurityController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtCore jwtCore;

    @Autowired
    UserService userService;


    @PostMapping("/signin")
    private ResponseEntity<?> signin(@RequestBody SignInRequest signInRequest, HttpServletRequest req) {
        Authentication auth = null;

        try {
            auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signInRequest.getUsername(), signInRequest.getPassword()
            ));

        } catch (BadCredentialsException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


        String token = jwtCore.generateToken(auth, signInRequest.isLongToken());
        HttpSession session = req.getSession(true);
//        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        return ResponseEntity.ok(token);
    }

    @PostMapping("/renew")
    private ResponseEntity<?> renewToken(@RequestBody SignInRequest signInRequest) {

        UserDetails user = userService.loadUserByUsername(signInRequest.getUsername());

        String token = jwtCore.generateToken(user, false);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/signup")
    private ResponseEntity<String> signup(@RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsUserByUsername(signUpRequest.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok().body("User registered successfully");

    }


}

package com.taskManager.TaskManagerAPI.Controller;

import com.taskManager.TaskManagerAPI.Pojo.LoginRequest;
import com.taskManager.TaskManagerAPI.Pojo.User;
import com.taskManager.TaskManagerAPI.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthorizationController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private JwtEncoder jwtEncoder;


    public AuthorizationController(UserService userService, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder)
    {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping(path="/login")
    public ResponseEntity<?> checkUserCredentials(@RequestBody LoginRequest loginRequest)
    {
        User checkUser = userService.getUser(loginRequest.getUsername());

        if (checkUser == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        }
        boolean passwordMatches = passwordEncoder.matches(
                loginRequest.getPassword(),
                checkUser.getPassword()
        );

        if (!passwordMatches) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String token = createToken(checkUser);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    private String createToken(User user) {
        var claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(60 * 30))
                .subject(user.getUsername())
                .claim("scope","ROLE_USER")
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @PostMapping(path="/register")
    public ResponseEntity<?> registerUser(@RequestBody User user)
    {
        try
        {
            User registeredUser = userService.addUser(user);
            return ResponseEntity.ok(registeredUser);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

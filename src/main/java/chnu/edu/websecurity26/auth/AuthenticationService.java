package chnu.edu.websecurity26.auth;

import chnu.edu.websecurity26.config.JwtService;
import chnu.edu.websecurity26.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            User user = (User) auth.getPrincipal();

            Map<String, Object> claims = new HashMap<>();
            claims.put("fullName", user.getFullName());

            return AuthenticationResponse.builder()
                    .token(jwtService.generateToken(claims, user))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
package tetravex.server.security.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtCore {

    @Value("${token.secret}")
    private String secret;

    @Value("${token.lifetime}")
    private int lifeTime;


    public String generateToken(Authentication auth) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        return Jwts.builder().setSubject(
                        userDetails.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() +  lifeTime))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

    }

    public String getNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();

    }
}
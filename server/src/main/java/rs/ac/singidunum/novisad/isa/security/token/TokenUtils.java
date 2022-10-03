package rs.ac.singidunum.novisad.isa.security.token;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import rs.ac.singidunum.novisad.isa.model.UserDetailsImpl;

@Component
public class TokenUtils {
	private static final Logger logger = LoggerFactory.getLogger(TokenUtils.class);

	@Value("${lms.secret}")
	private String tokenSecret;

	@Value("${lms.expirationMs}")
	private int tokenExpirationMs;

	public String generateJwtToken(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + tokenExpirationMs))
				.signWith(SignatureAlgorithm.HS512, tokenSecret)
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}

package com.example.demo.service;

import static com.example.demo.utils.ConstantUtils.COLLECTION_USER_TOKEN;
import static com.example.demo.utils.ConstantUtils.JWT;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.dto.TokenResponseDTO;
import com.example.demo.dto.ValidTokenResponse;
import com.example.demo.exceptions.InvalidUserException;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;


@Service
public class TokenServiceImpl implements TokenService {
	
    @Value("${jwt.secret}")
    private String secretKey;
    
	@Autowired
	private Firestore firebase;

	@Override
	public TokenResponseDTO getToken(String user, String pass) throws InterruptedException, ExecutionException {
				
		Query query = firebase.collection(COLLECTION_USER_TOKEN)
				.whereEqualTo("user", user)
				.whereEqualTo("pass", pass);
				
		ApiFuture<QuerySnapshot> querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
		
		if(documents.isEmpty()) {
			throw new InvalidUserException("Invalid subject to generate token.");
		}
		
		documents.stream()
			.forEach(document -> System.out.println("Authenticated user in BDD firebase : " + document.getData()));
				
		//Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Genera una clave secreta aleatoria.
		byte[] secretBytes = Base64.getDecoder().decode(secretKey);
		Key key = Keys.hmacShaKeyFor(secretBytes);
		
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600000); // 1 hora de expiración
                
		return TokenResponseDTO.builder()
				.subject(user)
				.token(Jwts.builder()
						.setSubject(user)
						.setIssuedAt(now)
						.setExpiration(expiryDate)
						.setHeaderParam("headerClaimPersonalizado", "header_1")
						.claim("bodyClaimPersonalizado", "body_1")
						.signWith(key)
						.compact())
				.build();
		
	}
	
    public ValidTokenResponse validToken(String authorizationHeader) {
    	
		if (authorizationHeader == null) return ValidTokenResponse.builder().tokenType("JWT").isValid(false).errorMessage("Token Null").build();
		if (!authorizationHeader.startsWith("Bearer ")) return ValidTokenResponse.builder().tokenType("JWT").isValid(false).errorMessage("You did not provide a bearer token").build();
		
		String token = authorizationHeader.substring(7);
    	
        try {
    		byte[] secretBytes = Base64.getDecoder().decode(secretKey);
    		Key key = Keys.hmacShaKeyFor(secretBytes);
        	
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            
//            JwsHeader claimsHeader = claimsJws.getHeader();
//          System.out.println("headerClaimPersonalizado: " + claimsHeader.get("headerClaimPersonalizado"));
//            
//          Claims claimsBody = claimsJws.getBody();	
//          System.out.println("Subject: " + claimsBody.getSubject());
//          System.out.println("Expiration: " + claimsBody.getExpiration());
//          System.out.println("bodyClaimPersonalizado: " + claimsBody.get("bodyClaimPersonalizado"));
//          System.out.println();
            
            return ValidTokenResponse.builder().tokenType(JWT).isValid(true).errorMessage("").build();
            
        } catch (SecurityException | IllegalArgumentException | ExpiredJwtException | MalformedJwtException e) {	//System.out.println("Token Inválido: " + token + "\n");
        	return ValidTokenResponse.builder().tokenType(JWT).isValid(false).errorMessage(e.getMessage()).build();
        }
    }
    

}

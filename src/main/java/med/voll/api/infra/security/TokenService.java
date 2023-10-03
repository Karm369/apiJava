package med.voll.api.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.swing.plaf.synth.SynthScrollPaneUI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import med.voll.api.domain.usuarios.Usuario;

@Service
public class TokenService {
	
	@Value("${api.security.secret}")
	private String apiSecret;

	public String generarToken(Usuario usuario) {
		try {
		    Algorithm algorithm = Algorithm.HMAC256(apiSecret); // mala practica hardcodear
		    return  JWT.create()
		        .withIssuer("voll med")
		        .withSubject(usuario.getUsername())  //tiene que ser dinamico
		        .withClaim("id", usuario.getId()) //devolver al cliente el id 
		     //   .withExpiresAt(generarFechaExpiracion()) // le ponemos tiempo de expiracion con un metodo  
		        .sign(algorithm);
		} catch (JWTCreationException exception){
		    // Invalid Signing configuration / Couldn't convert Claims.
			throw new RuntimeException();
		}
		
	}
	
	private Instant generarFechaExpiracion() {
		return LocalDateTime.now().plusHours(4).toInstant(ZoneOffset.of("-5"));
	}
	
	public String getSubject(String token) {
	
		if (token==null) {
			throw new RuntimeException("Sin Token ");
		}
	
		DecodedJWT verifier = null;   // saco la var afuera del try C, luego lo verifico y luego retorno el sub
		try {
		    Algorithm algorithm = Algorithm.HMAC256(apiSecret); //valida la firma del token
		     verifier = JWT.require(algorithm)
		        // specify an specific claim validations
		        .withIssuer("voll med")  //valida que el emisor sea ese
		        .build()
		        .verify(token); 	//valida el token
		     	verifier.getSubject();
		     
		} catch (JWTVerificationException exception){
		    // Invalid signature/claims
			System.out.println(exception.toString());
		}
		//assert verifier != null; //una forma de validacion
		if (verifier.getSubject() == null) {
			throw new RuntimeException("verifier invalido");
		}
		return verifier.getSubject();
	}
	
	
}

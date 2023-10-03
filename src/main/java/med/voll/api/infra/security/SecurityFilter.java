package med.voll.api.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuarios.UsuarioRepository;

@Component //es la forma general, service y los otros son mas especificos 
public class SecurityFilter extends OncePerRequestFilter {
	
	@Autowired
	private TokenService tokenService;

	@Autowired
	private UsuarioRepository usuaruiRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		// TODO Auto-generated method stub
		var token = request.getHeader("Authorization"); // por standard
		
		if (token != null) {
			token = token.replace("Bearer"+" ", "");
			System.out.println(token);
			System.out.println(tokenService.getSubject(token)); //este usuario tiene sesion?
			
			var subj = tokenService.getSubject(token);
			//throw new RuntimeException("El token no es valido");
			if (subj !=null) {
				var  usuario = usuaruiRepository.findByLogin(subj);
				
				var authetication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authetication);
			}
		}
		filterChain.doFilter(request, response);
		
		
		
 		
		 
	}

}

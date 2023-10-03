package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // para decirle a spring que es en contexto de seguridad 
public class SecurityConfigurations  { //aqui se devuelve el obj

	@Autowired
	private SecurityFilter securityFilter;
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		 httpSecurity.csrf(csrf->csrf.disable())
         .sessionManagement((sess-> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)))
         .authorizeHttpRequests((request -> request.requestMatchers(HttpMethod.POST,"/login")
                 .permitAll()
                 .requestMatchers("/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll() 
                 .anyRequest().authenticated()))
         		.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
 return httpSecurity.build();

	}
	
	@Bean // es para que spring lo tengo en cuenta en su contexto
	public AuthenticationManager authenticationManager(AuthenticationConfiguration auConf) throws Exception {
		return auConf.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {   //le dice a spring que codigo usamos 
		return new BCryptPasswordEncoder();
	}
	
}
package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import med.voll.api.domain.usuarios.DatosAutenticacionUsuario;
import med.voll.api.domain.usuarios.Usuario;
import med.voll.api.infra.security.DatosJWTTOKEN;
import med.voll.api.infra.security.TokenService;


@RestController
@RequestMapping("/login")
public class AutenticacionController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tS; //importamos el nuestro
	
	@PostMapping
	public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario dataAutenticationUser ){
		Authentication autToken = new UsernamePasswordAuthenticationToken(
				dataAutenticationUser.login(), 
				dataAutenticationUser.clave()); 
		var usuarioAutenticado = authenticationManager.authenticate(autToken); //si se valido el user lo pone en la var
		var jWTtoken =tS.generarToken((Usuario) usuarioAutenticado.getPrincipal()); //agarra el obj que ya esta auten y lo casteamos
		//return ResponseEntity.ok(jWTtoken);// consumimos dto y damos dto
		return ResponseEntity.ok(new DatosJWTTOKEN(jWTtoken));
	}

}
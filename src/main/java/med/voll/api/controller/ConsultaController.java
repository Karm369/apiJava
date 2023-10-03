package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consultas.AgendaDeConsultasService;
import med.voll.api.domain.consultas.DatosAgendarConsulta;
import med.voll.api.domain.consultas.DatosDetalleConsulta;

@RestController
@ResponseBody
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

	@Autowired
	private AgendaDeConsultasService agendaDeConsultasService;
	
	@PostMapping
	@Transactional
	public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datosAgendarConsulta){
	
		
		var responseD = agendaDeConsultasService.agendar(datosAgendarConsulta);
		
		
		return ResponseEntity.ok(responseD);
	}
	
}

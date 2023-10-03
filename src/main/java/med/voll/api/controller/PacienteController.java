package med.voll.api.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.pacientes.DatosActualizacionPaciente;
import med.voll.api.domain.pacientes.DatosGetPaciente;
import med.voll.api.domain.pacientes.DatosRegistroPaciente;
import med.voll.api.domain.pacientes.DatosRespPac;
import med.voll.api.domain.pacientes.Paciente;
import med.voll.api.domain.pacientes.PacienteRepository;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {
	
	@Autowired
	private PacienteRepository repository;

	@PostMapping
	public ResponseEntity registrarPaciente (@RequestBody @Valid DatosRegistroPaciente datosRegistroPaciente,UriComponentsBuilder uriComponentsBuilder) {
		Paciente paciente =   repository.save(new Paciente(datosRegistroPaciente));
	   
	    
	    DatosRespPac datosRpac = new DatosRespPac(
	    		paciente.getId(),
	    		paciente.getNombre(),
	    		paciente.getEmail(),
	    		paciente.getTelefono(), 
	    		paciente.getDni().toString(), 
				new DatosDireccion(
						paciente.getDireccion().getCalle(),
						paciente.getDireccion().getDistrito(),
						paciente.getDireccion().getCiudad(),
						paciente.getDireccion().getNumero(),
						paciente.getDireccion().getComplemento()));
	    
	    URI uri = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
	    return ResponseEntity.created(uri).body(datosRpac);

	    
	}
	
	@GetMapping
	public ResponseEntity<Page<DatosGetPaciente>>  listarPacientes(@PageableDefault(page = 0, size = 10, sort = {"nombre"}) org.springframework.data.domain.Pageable paginacion ){
		
	    return ResponseEntity.ok(repository.findByActivoTrue(paginacion).map(DatosGetPaciente::new));
	}
	
	@PutMapping
	@Transactional
	public void atualizar(@RequestBody @Valid DatosActualizacionPaciente datos) {
	    var paciente = repository.getReferenceById(datos.id());
	    paciente.atualizarInformacion(datos);
	}
	@DeleteMapping("/{id}")
	@Transactional
	public void remover(@PathVariable Long id) {
	    var paciente = repository.getReferenceById(id);
	    paciente.inactivar();
	}
	@GetMapping("/{id}")
	public ResponseEntity<DatosRespPac> retornaDatosPaciente(@PathVariable Long id) {
		Paciente paciente = repository.getReferenceById(id);;
	    var datosRpac = new DatosRespPac(
	    		paciente.getId(),
	    		paciente.getNombre(),
	    		paciente.getEmail(),
	    		paciente.getTelefono(), 
	    		paciente.getDni().toString(), 
				new DatosDireccion(
						paciente.getDireccion().getCalle(),
						paciente.getDireccion().getDistrito(),
						paciente.getDireccion().getCiudad(),
						paciente.getDireccion().getNumero(),
						paciente.getDireccion().getComplemento()));
	    
	    return ResponseEntity.ok(datosRpac);
	}
	
	
	
}

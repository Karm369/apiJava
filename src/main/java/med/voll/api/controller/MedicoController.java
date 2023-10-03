package med.voll.api.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.DatosActuMed;
import med.voll.api.domain.medico.DatosGetMedico;
import med.voll.api.domain.medico.DatosRegistroMedico;
import med.voll.api.domain.medico.DatosRespMed;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;

@RestController
@RequestMapping("/medicos")   //endpoint
@SecurityRequirement(name = "bearer-key")
public class MedicoController {  
	
	
	// el resposiry es lo mismo que el DAO, no hay que abir, persistir ni close a la base de datos
	@Autowired
	private MedicoRepository medicoRepository; //para no hacer new con spring autowired
												// no es recomendable usarlo cuando se hace la declaracion 
	
	@PostMapping
	public ResponseEntity<DatosRespMed> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMed, 
			UriComponentsBuilder uriComponentsBuilder) {
		// le agregamos a un Med med para las BPM porque ocupamos datos del que se va a guardar
		Medico medico = medicoRepository.save(new Medico(datosRegistroMed)); // o creaar const o crear un metodo que haga datos->medico
		
		DatosRespMed datosRmed = new DatosRespMed(
				medico.getId(),
				medico.getNombre(),
				medico.getEmail(),
				medico.getTelefono(), 
				medico.getEspecialidad().toString(), 
				new DatosDireccion(
						medico.getDireccion().getCalle(),
						medico.getDireccion().getDistrito(),
						medico.getDireccion().getCiudad(),
						medico.getDireccion().getNumero(),
						medico.getDireccion().getComplemento()));
		//Forma 1 : pero si se cambien la url del servidor, se muere 
		//URI url = "http://localhost:8080/medicos/" + medico.getId();
		
		URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
		return ResponseEntity.created(url).body(datosRmed);
	}

	//devuelve un objeto pago de datosdemed
	@GetMapping
	public ResponseEntity<Page<DatosGetMedico>>  listarMedicos(@PageableDefault(size = 10) org.springframework.data.domain.Pageable paginacion){
		//metodo antes de activo
		//return medicoRepository.findAll(paginacion).map(DatosGetMedico::new);
		return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosGetMedico::new));
	}
	
	//por medico especifico
	@GetMapping("/{id}")
	public ResponseEntity<DatosRespMed> retornaDatosMedico(@PathVariable Long id){
		Medico medico = medicoRepository.getReferenceById(id);;
		var datosMedico = new DatosRespMed(
				medico.getId(),
				medico.getNombre(),
				medico.getEmail(),
				medico.getTelefono(), 
				medico.getEspecialidad().toString(), 
				new DatosDireccion(
						medico.getDireccion().getCalle(),
						medico.getDireccion().getDistrito(),
						medico.getDireccion().getCiudad(),
						medico.getDireccion().getNumero(),
						medico.getDireccion().getComplemento()));
		return ResponseEntity.ok(datosMedico);
	}
	
	@Transactional
	@PutMapping
	public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActuMed datosActuMed) {
		Medico medico = medicoRepository.getReferenceById(datosActuMed.id());
		medico.actualizarDatos(datosActuMed);
		return ResponseEntity.ok(new DatosRespMed(
				medico.getId(),
				medico.getNombre(),
				medico.getEmail(),
				medico.getTelefono(), 
				medico.getEspecialidad().toString(), 
				new DatosDireccion(
						medico.getDireccion().getCalle(),
						medico.getDireccion().getDistrito(),
						medico.getDireccion().getCiudad(),
						medico.getDireccion().getNumero(),
						medico.getDireccion().getComplemento())));
		
	}
	
	//delete en base de datos
//	@Transactional
//	@DeleteMapping("/{id}")
//	public void deleteMedico(@PathVariable Long id){
//		Medico medico = medicoRepository.getReferenceById(id);;
//		medicoRepository.delete(medico);
//	}
	
	//delete logico
	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity deleteMedico(@PathVariable Long id){
		Medico medico = medicoRepository.getReferenceById(id);;
		medico.desactivarMedico();  //usar el medico como param aaqui es tonto, ya usamos el medico en el positoty con el id
		return ResponseEntity.noContent().build();
	}
	
}

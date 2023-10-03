package med.voll.api.domain.consultas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.pacientes.Paciente;
import med.voll.api.domain.pacientes.PacienteRepository;
import med.voll.api.domain.validaciones.ValidadorDeConsultas;
import med.voll.api.infra.errores.ValidacionDeIntegridad;

@Service
public class AgendaDeConsultasService {
	
	

	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private ConsultasRepository consuRepository;
	
	@Autowired
	List<ValidadorDeConsultas> validadores;
	
	public DatosDetalleConsulta agendar(DatosAgendarConsulta datosAgen) {
		
		if(!pacienteRepository.findById(datosAgen.idPaciente()).isPresent()) {
			throw new ValidacionDeIntegridad("este id de paciente no fue encontrado ");
		}
		if (datosAgen.idMedico()!=null && !medicoRepository.existsById(datosAgen.idMedico())) {
			throw new ValidacionDeIntegridad("este id de medico no fue encontrado ");
		}
		//validacioens
		validadores.forEach(v->v.validar(datosAgen));
		
		var paciente = pacienteRepository.findById(datosAgen.idPaciente()).get();  
		
		var medico = seleccionarMedico(datosAgen);
		if(medico==null) {
			throw new ValidacionDeIntegridad("No exsite medicos disponibles para este horario o especialidad");
		}
		
		var consulta = new Consulta(medico, paciente, datosAgen.fecha());  //para pasarle los med y pas ocupamos los respos
		consuRepository.save(consulta);
		
		return new DatosDetalleConsulta(consulta);
	}

		//cambio de un comentario
	private Medico seleccionarMedico(DatosAgendarConsulta datosAgen) {
		// TODO Auto-generated method stub
		
		if (datosAgen.idMedico()!=null) {
			return medicoRepository.getReferenceById(datosAgen.idMedico());
		}
		if (datosAgen.especialidad()==null) {
			throw new ValidacionDeIntegridad("Debe escoger una especialidad para el medico ");

		}
		
		return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datosAgen.especialidad(),
				datosAgen.fecha());
	}
}















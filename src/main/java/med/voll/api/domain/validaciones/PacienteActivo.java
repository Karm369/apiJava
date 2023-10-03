package med.voll.api.domain.validaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consultas.DatosAgendarConsulta;
import med.voll.api.domain.pacientes.PacienteRepository;

@Component
public class PacienteActivo implements ValidadorDeConsultas {
	
	@Autowired
	private PacienteRepository pacRespos;  // sin autowired porque en la clase de servicio ya lo tiene
	public void validar(DatosAgendarConsulta datosAngCons) {
		if (datosAngCons.idPaciente()== null) {
			return;
		}
		
		var pacienteActivo = pacRespos.findByActivoById(datosAngCons.idPaciente());
		
		if (!pacienteActivo) {
			throw new ValidationException("No se puede permitir agendar a un paciente inactivo");
		}
	}
}

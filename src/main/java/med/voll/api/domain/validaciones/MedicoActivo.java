package med.voll.api.domain.validaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consultas.DatosAgendarConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.pacientes.PacienteRepository;

@Service
public class MedicoActivo  implements ValidadorDeConsultas{
	
	@Autowired
	private MedicoRepository medRespos;  // sin autowired porque en la clase de servicio ya lo tiene
	public void validar(DatosAgendarConsulta datosAngCons) {
		if (datosAngCons.idMedico()== null) {
			return;
		}
		
		var medicoActivo = medRespos.findByActivoById(datosAngCons.idMedico());
		
		if (!medicoActivo) {
			throw new ValidationException("No se puede permitir agendar a un Medico inactivo");
		}
	}
}

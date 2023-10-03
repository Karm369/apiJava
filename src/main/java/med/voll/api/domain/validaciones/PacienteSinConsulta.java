package med.voll.api.domain.validaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consultas.ConsultasRepository;
import med.voll.api.domain.consultas.DatosAgendarConsulta;
import med.voll.api.domain.pacientes.PacienteRepository;


@Component 
public class PacienteSinConsulta implements ValidadorDeConsultas{
	
	@Autowired
	private ConsultasRepository conRespos;

	public void validar(DatosAgendarConsulta datosAngCons) {
		var primerHorario = datosAngCons.fecha().withHour(7);
		var ultimoHorario = datosAngCons.fecha().withHour(18);
		
		var pacienteConConsulta = conRespos.existsByPacienteIdAndDataBetween(
				datosAngCons.idPaciente(),primerHorario,ultimoHorario);
		if (pacienteConConsulta) {
			throw new ValidationException("Paciente Sin Consulta");
		}
	}
	
}

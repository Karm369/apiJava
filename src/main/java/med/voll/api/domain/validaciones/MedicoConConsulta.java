package med.voll.api.domain.validaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consultas.ConsultasRepository;
import med.voll.api.domain.consultas.DatosAgendarConsulta;

@Component
public class MedicoConConsulta implements ValidadorDeConsultas{

	@Autowired
	private ConsultasRepository conRespos;

	public void validar(DatosAgendarConsulta datosAngCons) {
		if (datosAngCons.idMedico()==null) {
			return;
		}
		
		
		var medConCon = conRespos.existsByMedicoIdAndData(datosAngCons.idMedico(),datosAngCons.fecha());
		if (medConCon) {
			throw new ValidationException("Medico ya tiene una Consulta en ese horario");
		}
		
	}
}

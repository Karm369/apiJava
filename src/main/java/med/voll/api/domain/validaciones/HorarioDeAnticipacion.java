package med.voll.api.domain.validaciones;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consultas.DatosAgendarConsulta;

@Component
public class HorarioDeAnticipacion implements ValidadorDeConsultas {

public void validar(DatosAgendarConsulta datosAngCons) {
		
		var ahora = LocalDateTime.now();
		var horaConsulta = datosAngCons.fecha();
		
		var difDe30ms = Duration.between(ahora, horaConsulta).toMinutes()<30;
		if (difDe30ms) {
			throw new ValidationException("Las consultas deben tener al menos 30 minutos de anticipacion");
		}
	
	}
}

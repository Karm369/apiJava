package med.voll.api.domain.validaciones;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consultas.DatosAgendarConsulta;
import med.voll.api.infra.errores.ValidacionDeIntegridad;

@Component
public class HorariosDeFuncionamiento implements ValidadorDeConsultas{
	
	public void validar(DatosAgendarConsulta datosAngCons) {
		
		
		var antesApertura = datosAngCons.fecha().getHour()<7;
		var despuesCierre = datosAngCons.fecha().getHour()>19;
		var domingo=DayOfWeek.SUNDAY.equals(datosAngCons.fecha().getDayOfWeek());
		if (domingo ||antesApertura ||despuesCierre) {
			throw new ValidationException("El horario de atencion es de lunes a sabado de 7 a 19 horas");
		}
	}
	

}

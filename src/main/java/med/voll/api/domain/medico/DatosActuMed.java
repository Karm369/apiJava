package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosActuMed(
		@NotNull Long id, //solo el id como es indispensable debe ser not null
		
		String nombre,
		String documento,
		DatosDireccion datosDireccion
	) {

}

package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosRegistroMedico(
		@NotBlank(message="Campo obligatorio") String nombre, 
		@NotBlank @Email String email, 
		@NotBlank @Pattern(regexp="\\d{4,6}") String documento,  // expresion regular de 4 a 6 digitos
		@NotBlank String telefono,  //se puede poner un formato de telefono
		@NotNull Especialidad especialidad, 
		@NotNull @Valid DatosDireccion direccion) { // comoe s obj es null no blank y como es obj otro valid

	
	
}

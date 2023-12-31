package med.voll.api.domain.pacientes;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosRegistroPaciente(
		@NotBlank(message="Campo obligatorio")String nombre,
		@NotBlank @Email String email,
		@NotBlank String telefono,
		@NotBlank @Pattern(regexp="\\d{10,14}")String dni,
        @NotNull @Valid DatosDireccion direccion) {

}

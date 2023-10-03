package med.voll.api.domain.direccion;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotBlank;

public record DatosDireccion(
		@JsonAlias("urbanización") @NotBlank String calle, 
		@NotBlank String distrito, 
		@NotBlank String ciudad, 
		@NotBlank String numero, 
		@NotBlank String complemento) {

}

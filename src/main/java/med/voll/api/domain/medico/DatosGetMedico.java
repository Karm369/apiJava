package med.voll.api.domain.medico;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosGetMedico(
		@NotNull Long id,
		@NotBlank String nombre,
		@NotNull String especialidad,
		@NotBlank String documento,
		@NotBlank String email){        //podemos usar el @jsonignore para evitar enviar un atributo
	
	public DatosGetMedico(Medico medico) {
		this(medico.getId(),
				medico.getNombre(), 
				medico.getEspecialidad().toString(), 
				medico.getDocumento(), 
				medico.getEmail());
	}

	
}

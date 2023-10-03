package med.voll.api.domain.pacientes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.Medico;

public record DatosGetPaciente(
		@NotNull Long id,
		@NotBlank String nombre, 
		@NotBlank String email, 
		@NotBlank String dni,
		@NotBlank String telefono
		
		) { 
	
	public DatosGetPaciente(Paciente paciente) {
		this(	paciente.getId(),
				paciente.getNombre(),
				paciente.getEmail(),
				paciente.getDni(),
				paciente.getTelefono()
				);
				
	}
 
    
} 
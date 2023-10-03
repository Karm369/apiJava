package med.voll.api.domain.pacientes;

public record DatosListaPaciente(
		Long id, 
		String nombre, 
		String email, 
		String dni) {
	
    public DatosListaPaciente(Paciente paciente) {
        this(
        		paciente.getId(), 
        		paciente.getNombre(), 
        		paciente.getEmail(), 
        		paciente.getDni());
    }
}
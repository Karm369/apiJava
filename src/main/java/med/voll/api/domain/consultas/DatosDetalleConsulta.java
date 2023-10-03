package med.voll.api.domain.consultas;

import java.time.LocalDateTime;

public record DatosDetalleConsulta(
		Long id,
		Long idPaciente,
		Long idMedico,
		LocalDateTime fecha) {

	public DatosDetalleConsulta(Consulta consulta) {
		// TODO Auto-generated constructor stub
		this(   consulta.getId(), 
				consulta.getPaciente().getId(), 
				consulta.getMedico().getId(),
				consulta.getDate());
	}

}

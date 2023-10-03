package med.voll.api.domain.consultas;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultasRepository extends JpaRepository<Consulta,Long>{

	Boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primerHorario, LocalDateTime ultimoHorario);

	Boolean existsByMedicoIdAndData(Long idMedico, LocalDateTime fecha);


	
}

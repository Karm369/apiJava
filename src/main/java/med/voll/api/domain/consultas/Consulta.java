package med.voll.api.domain.consultas;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.pacientes.Paciente;

@Entity(name="Consulta")
@Table(name = "consultas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of ="id")
public class Consulta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="medico_id")
	private Medico medico;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="paciente_id")
	private Paciente paciente;
	
	private LocalDateTime data;
	
	
	
	
	public Consulta(long id, Medico medico, Paciente paciente, LocalDateTime date) {
		
		this.id = id;
		this.medico = medico;
		this.paciente = paciente;
		this.data = date;
	}

	public Consulta() {
		// TODO Auto-generated constructor stub
	}

	public Consulta(Medico medico, Paciente paciente, LocalDateTime date) {
		// TODO Auto-generated constructor stub
		this.medico = medico;
		this.paciente = paciente;
		this.data = date;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public LocalDateTime getDate() {
		return data;
	}

	public void setDate(LocalDateTime date) {
		this.data = date;
	}

	public long getId() {
		return id;
	}
	
	
	
	
}

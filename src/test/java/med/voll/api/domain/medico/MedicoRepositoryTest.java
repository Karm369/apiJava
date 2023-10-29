package med.voll.api.domain.medico;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import med.voll.api.domain.consultas.Consulta;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.pacientes.DatosRegistroPaciente;
import med.voll.api.domain.pacientes.Paciente;


@DataJpaTest
@AutoConfigureTestDatabase(replace =AutoConfigureTestDatabase.Replace.NONE) //no remplazar la base de datos que estoy usando
@ActiveProfiles("test")
class MedicoRepositoryTest {
	
	@Autowired
	private MedicoRepository medicoRepository;

	@Autowired
	private TestEntityManager tEM;

	@Test
	@DisplayName("deberia retornar nulo cuando el medico se encuentre en consulta con otro paciente ")
	void testSeleccionarMedicoConEspecialidadEnFechaEscenario1() {
		
		//given
		var proximoLunes10H= LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);
		
		
		var medico= registraMedico("Jose","jose@mail.com","348556", Especialidad.CARDIOLOGIA);
		var paciente= registraPaciente("Antonio","antonio@mail.com","456466") ;
		registrarConsulta(medico,paciente, proximoLunes10H);
		//when
		var medicoLibre	= medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA, proximoLunes10H);
		//then
		assertThat(medicoLibre).isNull();
		
	}
	@Test
	@DisplayName("deberia retornar un medico cuando realice la conslta en las base de datsos ")
	void testSeleccionarMedicoConEspecialidadEnFechaEscenario2() {
		
		var proximoLunes10H= LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);
		
		
		var medico= registraMedico("Jose","jose@mail.com","1456", Especialidad.CARDIOLOGIA);
		
		
		var medicoLibre	= medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA, proximoLunes10H);
		
		assertThat(medicoLibre).isEqualTo(medico);
		
	}

	private Medico registraMedico(String nombre, String email, String documento, Especialidad especialidad) {
		var medico= new Medico(datosMedico(nombre,email,documento,especialidad));
		tEM.persist(medico);
		return medico;
	}
	private Paciente registraPaciente(String nombre, String email, String documento) {
		var paciente= new Paciente(datosPaciente(nombre,email,documento));
		tEM.persist(paciente);
		return paciente;
	}
	
	private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad) {
		return new DatosRegistroMedico(
				nombre,email,"12322",documento,especialidad,datosDireccion()
				);
	}
	private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento ) {
		return new DatosRegistroPaciente(
				nombre,email,"12312",documento,datosDireccion()
				);
	}
	private DatosDireccion datosDireccion() {
		return new DatosDireccion(
				"loc","aaa","pavas","221","2"
				);
	}

	private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime proximoLunes10H) {
		tEM.persist(new Consulta(medico,paciente,proximoLunes10H));
		
	}

}

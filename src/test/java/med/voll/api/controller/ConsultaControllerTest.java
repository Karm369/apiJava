package med.voll.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito.Then;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import med.voll.api.domain.consultas.AgendaDeConsultasService;
import med.voll.api.domain.consultas.DatosAgendarConsulta;
import med.voll.api.domain.consultas.DatosDetalleConsulta;
import med.voll.api.domain.medico.Especialidad;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private JacksonTester<DatosAgendarConsulta> agentipoJack;
	
	@Autowired
	private JacksonTester<DatosDetalleConsulta> datosConsuJack;
	
	@Autowired 	// para no hacer una busqueda real en kla db
	private AgendaDeConsultasService agendaDeConsultasService;
	
	
	@Test
	@DisplayName("deberia renotrar 400 cuando invalidos")
	@WithMockUser
	void testAgendar1()throws Exception{
		
		var response = mvc.perform(post("/consultas")).andReturn().getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
	@Test
	@DisplayName("deberia renotrar 200 cuando validos")
	@WithMockUser
	void testAgendar2()throws Exception{
		
		var fecha= LocalDateTime.now().plusHours(1);
		var esp = Especialidad.CARDIOLOGIA;
		
		var datos = new DatosDetalleConsulta(null, 1l, 3l, fecha);
		
		
		when(agendaDeConsultasService.agendar(new DatosAgendarConsulta(1l, 3l,fecha,esp))).thenReturn(datos) ; //  (new DatosDetalleConsulta(null, 2l, 5l, fecha))
		
		var response = mvc.perform(post("/consultas").contentType(MediaType.APPLICATION_JSON)
				.content(agentipoJack.write(new DatosAgendarConsulta(1l, 3l,fecha,esp)).getJson())
				).andReturn().getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		
		var jsonesperado = datosConsuJack.write(datos).getJson();
		
		assertThat(response).isEqualTo(jsonesperado);
	}

}

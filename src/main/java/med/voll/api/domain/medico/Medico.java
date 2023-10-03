package med.voll.api.domain.medico;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

@Entity(name="Medico")
@Table(name="medicos")
@Getter
@NoArgsConstructor   // los construc no esán funcionando por lombok
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public @Data class Medico {  //aplicando los get and set el data no es necesario

	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String email;
	private String documento;
	private String telefono;
	private Boolean activo = true;
	
	@Enumerated(EnumType.STRING)
	private Especialidad especialidad;
	@Embedded
	private Direccion direccion;
	
	public Medico() {
		
	}
	
	public Medico(DatosRegistroMedico datosRegistroMed) {
		this.activo = true; //por defecto va a estar activo
		
		this.nombre=datosRegistroMed.nombre();
		this.email=datosRegistroMed.email();
		this.documento=datosRegistroMed.documento();
		this.telefono=datosRegistroMed.telefono();
		this.especialidad=datosRegistroMed.especialidad();
		this.direccion= new Direccion (datosRegistroMed.direccion());
		
	}

	
	
	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Especialidad getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public Long getId() {
		return id;
	}

	public void actualizarDatos(DatosActuMed datosActuMed) { 
		if(datosActuMed.nombre()!= null){  // si no es 
			this.nombre = datosActuMed.nombre();
		}
		if(datosActuMed.documento()!= null){
			this.documento = datosActuMed.documento();
		}
		if(datosActuMed.datosDireccion()!= null){
			this.direccion = direccion.ActuDatosDirecc(datosActuMed.datosDireccion());
		}
		
		
		
		
	}

	public void desactivarMedico( ) {
		//medico.activo = false;  // es el medico que pasamos el que desactivamos no este/this medico //MENTIRA y hay que borrar el param Medico medico
		this.activo = false;
		
	}
	
	
	
	
	
}	

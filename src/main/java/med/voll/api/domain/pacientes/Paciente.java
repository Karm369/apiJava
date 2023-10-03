package med.voll.api.domain.pacientes;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Paciente")
@Table(name = "pacientes")
@EqualsAndHashCode(of = "id")
public class Paciente {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String nombre;
	    private String email;
	    private String dni;
	    private String telefono;
	    
	    private Boolean activo = true;
	    
	    @Embedded
	    private Direccion direccion;
	    
	    public Paciente() {
			// TODO Auto-generated constructor stub
		}

		public Paciente(DatosRegistroPaciente datosRegistroPaciente) {
			this.activo=true;
			
			this.nombre = datosRegistroPaciente.nombre();
			this.email = datosRegistroPaciente.email();
			this.dni = datosRegistroPaciente.dni();
			this.telefono = datosRegistroPaciente.telefono();
			this.direccion= new Direccion (datosRegistroPaciente.direccion());
			
		}
		public void atualizarInformacion(DatosActualizacionPaciente datos) {
		    if (datos.nombre() != null)
		        this.nombre = datos.nombre();

		    if (datos.telefono() != null)
		        this.telefono = datos.telefono();

		    if (datos.direccion() != null)
		        direccion.ActuDatosDirecc(datos.direccion());
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

		public String getDni() {
			return dni;
		}

		public void setDni(String documentoIdentidad) {
			this.dni = documentoIdentidad;
		}

		public String getTelefono() {
			return telefono;
		}

		public void setTelefono(String telefono) {
			this.telefono = telefono;
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

		public void inactivar() {
		    this.activo = false;
		}
	    
	    
}


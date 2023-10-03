package med.voll.api.domain.direccion;


import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.medico.DatosActuMed;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public @Data class Direccion implements Serializable {
	
	private String calle;
	private String distrito;
	private String ciudad;
	private String numero;
	private String complemento;
	
	public Direccion(){
		
	}
	
	public Direccion(DatosDireccion datosdireccion) {
		this.calle = datosdireccion.calle();
		this.distrito = datosdireccion.distrito();
		this.ciudad = datosdireccion.ciudad();
		this.numero = datosdireccion.numero();
		this.complemento = datosdireccion.complemento();
	}
	
	public Direccion ActuDatosDirecc(DatosDireccion datosDireccion) {
		this.calle = datosDireccion.calle();
		this.distrito = datosDireccion.distrito();
		this.ciudad = datosDireccion.ciudad();
		this.numero = datosDireccion.numero();
		this.complemento = datosDireccion.complemento();
		return this;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getDistrito() {
		return distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	
}

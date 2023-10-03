package med.voll.api.domain.pacientes;

import med.voll.api.domain.direccion.DatosDireccion;

public record DatosRespPac(
		Long id,
		String nombre,
		String emali,
		String telefono,
		String documento,
		DatosDireccion direccion) {

	
}
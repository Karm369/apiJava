package med.voll.api.domain.medico;

import med.voll.api.domain.direccion.DatosDireccion;

public record DatosRespMed(
		Long id,
		String nombre,
		String emali,
		String telefono,
		String documento,
		DatosDireccion direccion) {

	
}

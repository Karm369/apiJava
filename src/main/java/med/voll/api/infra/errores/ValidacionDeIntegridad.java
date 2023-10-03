package med.voll.api.infra.errores;

public class ValidacionDeIntegridad extends RuntimeException {// si usamos throwable tenemos que usar el throws en la firma del metodo 

	public ValidacionDeIntegridad(String s) {	
		super(s);
	}
 
}

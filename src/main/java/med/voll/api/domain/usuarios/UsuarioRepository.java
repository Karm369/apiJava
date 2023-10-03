package med.voll.api.domain.usuarios;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {  // pido un usuaio pero damos un 
		//User details que es imp de userdetail

	
	//UserDetails findByLogin(String login); //Login por el private String loginUser

	UserDetails findByLogin(String username);

}

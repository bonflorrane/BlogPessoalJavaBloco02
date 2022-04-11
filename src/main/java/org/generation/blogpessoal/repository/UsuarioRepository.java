package org.generation.blogpessoal.repository;

import java.util.List;
import java.util.Optional;

import org.generation.blogpessoal.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	public Optional<Usuario>findByUsuario(String usuario);
	
	public List<Usuario>findAllByNomeContainingIgnoreCase(String nome);
	
	//optional pois o usuario pode estar cadastrado ou não. Ele também mostra uma lista, caso seja digitado apenas um nome (Julia) por exemplo
// optional consulta direto no banco de dados. Diferente do List que precisa de uma hierarquia, acessa o banco pela Api 
}

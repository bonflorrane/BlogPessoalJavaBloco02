package org.generation.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.generation.blogpessoal.model.Usuario;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)// a notação @ criação e inicializa o ambiente de teste. Random_Port: aplicação roda em qq porta
@TestInstance(TestInstance.Lifecycle.PER_CLASS)//conseguir instanciar e 
public class UsuarioRepositoryTest {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start() {
		usuarioRepository.save(new Usuario(0L,"DJ Cleiton Rasta", "djrasta@gmail.com", "pedrada","https://imagens.ne10.uol.com.br/veiculos/_midias/jpg/2020/07/10/806x444/1_dj_cleiton_rasta_perfil_body_image_1474918939-16274795.jpg\r\n"
				+ ""));
		usuarioRepository.save(new Usuario(0L,"DJ Laurinha Lero", "laurinha@gmail.com", "laura123","https://imagens.ne10.uol.com.br/veiculos/_midias/jpg/2020/07/10/806x444/1_dj_cleiton_rasta_perfil_body_image_1474918939-16274795.jpg\r\n"
				+ ""));
		usuarioRepository.save(new Usuario(0L,"Ednaldo Pereira", "ednaldo@banido.com", "naovalenada","https://imagens.ne10.uol.com.br/veiculos/_midias/jpg/2020/07/10/806x444/1_dj_cleiton_rasta_perfil_body_image_1474918939-16274795.jpg\r\n"
				+ ""));
		usuarioRepository.save(new Usuario(0L,"Mc Naninha", "naninha@imperio.com", "trabalho","https://i.imgur.com/FETvs2O.jpg\r\n"));
	}
	
	@Test
	@DisplayName("Retorna apenas um usuário")
	public void deveRetornarUmUsuario() {
		Optional<Usuario>usuario = usuarioRepository.findByUsuario("naninha@imperio.com");
		assertTrue(usuario.get().getUsuario().equals("naninha@imperio.com"));
		
	}
	
	@Test
	@DisplayName("Retorna dois usuários")
	public void deveRetornarDoisUsuarios() {
		List<Usuario>listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("DJ");
		assertEquals(2, listaDeUsuarios.size());//listaDeUsuario por deixa dos panos é um array, usamos size para deixar dinâmico o tamanho da listaDeUsuarios(mostra o número de usuarios que tem na função)
		
		assertTrue(listaDeUsuarios.get(0).getNome().equals("DJ Cleiton Rasta")); //get(0 - posição inicial do array)
		assertTrue(listaDeUsuarios.get(1).getNome().equals("DJ Laurinha Lero")); 
	}
	
	@AfterAll
	public void end() {
		usuarioRepository.deleteAll();
	}

}
	



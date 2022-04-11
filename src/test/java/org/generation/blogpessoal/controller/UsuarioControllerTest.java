package org.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.generation.blogpessoal.model.Usuario;
import org.generation.blogpessoal.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private TestRestTemplate testRestTemplate;//usado apenas no controller, puxando @RestController - métodos http

	@Test
	@Order(1)
	@DisplayName("Cadastrar apenas um usuário")
	public void deveCadastrarUmUsuario() {
		
		HttpEntity<Usuario>requisicao = new HttpEntity<Usuario>(new Usuario(0L,"Jose","jose@imperiobronze.com","trabalholindo","https://i.imgur.com/FETvs2O.jpg"
));
		ResponseEntity<Usuario>resposta=testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
		
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome()); //se o que eu mandei na minha requisição(digitei), é igual a minha resposta(banco de dados)
		assertEquals(requisicao.getBody().getUsuario(),resposta.getBody().getUsuario());
	}
	
	@Test
	@Order(2)
	@DisplayName("Não deve permitir duplicação do Usuário")
	public void naoDeveDuplicarUsuario() {
		usuarioService.cadastrarUsuario(new Usuario(0L,"Maria da Silva","maria_silva@gmail.com","jsdisdjisd","https://i.imgur.com/FETvs2O.jpg"));
		
		HttpEntity<Usuario>requisicao = new HttpEntity<Usuario>(new Usuario(0L,"Maria da Silva","maria_silva@gmail.com","jsdisdjisd","https://i.imgur.com/FETvs2O.jpg"));
		
		ResponseEntity<Usuario>resposta=testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
	}
	
	@Test
	@Order(3)
	@DisplayName("Alterar um Usuário")
	public void deveAtualizarUmUsuario() {
		
		Optional<Usuario>usuarioCreate=usuarioService.cadastrarUsuario(new Usuario(0L,"Juliana Andrews","ju@gmail.com","urosnhf","https://i.imgur.com/FETvs2O.jpg"));
		
		Usuario usuarioUpdate = new Usuario(usuarioCreate.get().getId(), "Juliana Andrews Ramos", "ju_ramos@gmail.com","ju123","https://i.imgur.com/FETvs2O.jpg");
		
		HttpEntity<Usuario>requisicao = new HttpEntity<Usuario>(usuarioUpdate);
		
		ResponseEntity<Usuario>resposta=testRestTemplate.withBasicAuth("root", "root").exchange("/usuarios/atualizar", HttpMethod.PUT,requisicao, Usuario.class);
	
		assertEquals(HttpStatus.OK,resposta.getStatusCode());
		assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome()); //se o que eu mandei na minha requisição(digitei), é igual a minha resposta(banco de dados)
		assertEquals(requisicao.getBody().getUsuario(),resposta.getBody().getUsuario());
	
	}
	
	@Test
	@Order(4)
	@DisplayName("Listar todos os Usuários")
	public void deveMostrarTodosUsuarios() {
		
		usuarioService.cadastrarUsuario(new Usuario(0L,"Sabrina Sanches","ss@gmail.com","457894","https://i.imgur.com/FETvs2O.jpg"));
		usuarioService.cadastrarUsuario(new Usuario(0L,"Ricardo Marques","marques09@gmail.com","oiweudh","https://i.imgur.com/FETvs2O.jpg"));
		
		ResponseEntity<String>resposta = testRestTemplate.withBasicAuth("root", "root").exchange("/usuarios/all",HttpMethod.GET,null,String.class);
		assertEquals(HttpStatus.OK,resposta.getStatusCode());
	}
	
	
	
}

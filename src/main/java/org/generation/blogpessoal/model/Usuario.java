package org.generation.blogpessoal.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name ="tb_usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	@Schema// emite um lembrete no Swagger de que deve ser digitado um email no valor do atributo
	@NotNull
	@Email // o atributo deve ser um email válido
	private String usuario; // em usuário será adicionado um email garantido pela palavra reservada (annotation)
	
	@NotNull
	private String senha;
	
	private String foto;
	
	@OneToMany(mappedBy="usuario", cascade =CascadeType.REMOVE)//um usuário para várias postagens. Remove = quando um usuário é retirado, suas postagens também são.
	@JsonIgnoreProperties("usuario")// quando encontrar usuario, não precisa fazer mais gets evitando recursividade
    private List<Postagem>postagens;
	
	//Metodos construtores para testes
	//Importante seguir a mesma ordem das declarações dos atributos de usuario acima
	// Construtor cheio
	public Usuario(Long id, String nome, String usuario, String senha, String foto) {
		this.id = id;
		this.nome= nome;
		this.usuario=usuario;
		this.senha=senha;
		this.foto=foto;
	}
	
	//Construtor vazio
	public Usuario() {}//dar liberdade para testar os atributos que quiser
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	
	public List<Postagem> getPostagens() {
		return postagens;
	}

	public void setPostagens(List<Postagem> postagens) {
		this.postagens = postagens;
	}

	
}

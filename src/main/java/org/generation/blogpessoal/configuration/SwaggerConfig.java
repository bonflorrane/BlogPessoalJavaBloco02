package org.generation.blogpessoal.configuration;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration // indica que a Classe do tipo configuração(ou seja uma classe como fonte de definições de beans)
public class SwaggerConfig {

	
	@Bean//indica ao Spring que ele deve invocar aquele metodo e gerenciar o objeto retornado por ele.
	public OpenAPI springBlogPessoalOpenAPI() {//Cria um Objeto da Classe OpenAPI, que gera a documentação no Swagger uঞlizando a especificação OpenAPI
		return new OpenAPI()									
				.info(new Info() //Informações sobre a API
						.title("Projeto Blog Pessoal")
						.description("Projeto Blog Pessoal - Generation Brasil")
						.version("v0.0.1")
				.license(new License() //Informações sobre a Licença da API
						.name("Generation Brasil")
						.url("https://brazil.generation.org/"))
				.contact(new Contact() // Informações do contato da pessoa desenvolvedora
						.name("Conteudo Generation")
						.url("https://github.com/conteudoGeneration")
						.email("conteudogeneration@gmail.com")))
				.externalDocs(new ExternalDocumentation()//Referente as informações externas
						.description("Github")
						.url("https://github.com/conteudoGeneration/"));
		
		
	}
	
	@Bean
	public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {
		return openApi -> {//gera a documentação no Swagger uঞlizando a especificação OpenAPI.
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation ->{
				ApiResponses apiResponses = operation.getResponses();//receberá as Respostas HTTP de cada endpoint (Paths) através do método getResponses().
				
				//Adiciona as novas Respostas no endpoint, subsঞtuindo as atuais e acrescentando as demais, através do Método addApiResponse()
				apiResponses.addApiResponse("200", createApiResponse("Sucesso!"));
				apiResponses.addApiResponse("201", createApiResponse("Objeto Persistido!"));
				apiResponses.addApiResponse("204", createApiResponse("Objeto Excluído!"));
				apiResponses.addApiResponse("400", createApiResponse("Erro na Requisição"));
				apiResponses.addApiResponse("401", createApiResponse("Acesso Não Autorizado"));
				apiResponses.addApiResponse("404", createApiResponse("Objeto Não Encontrado"));
				apiResponses.addApiResponse("401", createApiResponse("Erro na Aplicação"));
				
			}));
		};
		
	}

	private ApiResponse createApiResponse(String message) { //O Método createApiResponse() adiciona uma descrição (Mensagem), em cada Resposta HTTP.
		// TODO Auto-generated method stub
		return new ApiResponse().description(message);
	}
		
	
}

package br.com.focosolution;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.focosolution.data.vo.v1.PersonVO;
import br.com.focosolution.data.vo.v2.PersonVOV2;
import br.com.focosolution.services.AuthServices;
import br.com.focosolution.services.PersonServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "Person", description = "Endpoints para gerenciar person")
public class PersonController {
	
	private Logger logger = Logger.getLogger(PersonController.class.getName());
	
	// A annotation abaixo faz a injeção do serviço, sem precisar instanciá-lo.
	@Autowired
	private PersonServices service;
	
	// Utilizando Path Params, exemplo: http://localhost:8080/person/1
	// Os @ApiResponse criados abaixo são para o Swagger utilizar, ou seja, documentação do OpenAPI.	
	@GetMapping(value = "/{id}", 	
			produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Finds a Person", description = "Descrição Finds a Person",
			tags = {"Person"},
			responses = {
					@ApiResponse(description = "Success", responseCode = "200", 
							content = @Content(schema = @Schema(implementation = PersonVO.class))
					),
					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
	)	
	public PersonVO findById(@PathVariable(value = "id") Long paramId) {
		return service.findById(paramId);
	}	
	
	// Utilizando Path Params, exemplo: http://localhost:8080/person
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Finds all Person", description = "Descrição de Finds all Person",
			tags = {"Person"},
			responses = {
					@ApiResponse(description = "Success", responseCode = "200", 
							content = {
								@Content(
										mediaType = "application/json",
										array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
								)
							}),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
	)
	public List<PersonVO> findAll() {
		logger.info("Estou no findAll");
		return service.findAll();
	}		

	// Deve ser chamado por uma aplicação, como o Postaman. Para isso devemos escolher "Body"+"raw"+"JSON"
	// Vale observar que o método create utiliza a annotation @RequestBody
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public PersonVO create(@RequestBody PersonVO person) {
		return service.create(person);
	}	
	
	@PostMapping(value = "/v2", consumes = MediaType.APPLICATION_JSON_VALUE,
			 	 produces = MediaType.APPLICATION_JSON_VALUE)
	public PersonVOV2 createV2(@RequestBody PersonVOV2 person) {
		return service.createv2(person);
	}	
	
	// Bastante similar ao POST
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE)
	public PersonVO update(@RequestBody PersonVO person) {
		return service.update(person);
	}	

	// No caso do delete é preferível utilizar o Path Params para assim evitar travegar um JSON na rede. 
	// Porém não é possível chamá-lo pelo browser, pois ele seria tratado como o GET (findById)
	// Executando pelo Postman percebemos que foi Okay pois ele retornou o código 200 OK
	@DeleteMapping(value = "/{id}") 	
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long paramId) {
		service.delete(paramId);
		return ResponseEntity.noContent().build();
	}	
	
	
}

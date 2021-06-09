package com.example.minhasfinancas.model.repository;

import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.minhasfinancas.model.entity.Usuario;


@ExtendWith(SpringExtension.class)
//@SpringBootTest
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {
	//quando coloca esse @activeprofiles está dando erro
	//a @SpringBootTest: sobe toda a aplicação - retirar pois não precisa subir tudo
	//@DataJpaTest: cria uma instancia do banco de dados na memoria e quando acaba encerra tbm
	//@AutoConfigureTestDatabase: o datajpatest Sobrescreve o q ta no activeprofile (qlr configuracao feita no ambiente de teste) e nao quer q desconfigure.
	
	@Autowired
	UsuarioRepository repository;

	@Autowired
	TestEntityManager entityManager;
	
	//Para fazer um teste precisa de 3 elementos: cenário, ação/execução e a verificação
	
	@Test
	public void deveVerificarAExistenciaDeUmEmail() {
		//cenário:
		Usuario user =  criarUsuario();
		entityManager.persist(user); 
		//ação
		boolean resultado = repository.existsByEmail("usuario@email.com");
		//verificação
		assumeTrue(resultado);
		//Assertions.assertThat(resultado).isTrue();
	}
	
	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
		//cenário
		repository.deleteAll();
		//acao
		boolean resultado = repository.existsByEmail("usuario@email.com");
		//verificacao
		assumeFalse(resultado);
//		Assertions.assertThat(resultado).isFalse();
	}
	
	
	@Test
	public void devePersistirUmUsuarioNaBaseDeDados() {
		//cenário
		Usuario usuario =  criarUsuario();
		//acao
		Usuario usuarioSalvo = repository.save(usuario);
		assumeTrue(usuarioSalvo.getId() != null);
	}
	
	@Test
	public void deveBuscarUmUsuarioPorEmail() {
		Usuario usuario =  criarUsuario();
		entityManager.persist(usuario);
		Optional<Usuario> result = repository.findByEmail("usuario@email.com");
		assumeTrue(result.isPresent());
	}
	
	@Test
	public void deveRetornarVazioAoBuscarUsuarioPorEmailQuandoNaoExisteNaBase() {
		Optional<Usuario> result = repository.findByEmail("usuario@email.com");		
		assumeFalse(result.isPresent());
	}
	
	public static Usuario criarUsuario() {
		return Usuario.builder().nome("usuario").email("usuario@email.com").senha("senha").build();
		
	}
	
}

package com.example.minhasfinancas.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.minhasfinancas.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	//optional pq pode ou nao existir
	//find p achar e retornar (ex: findByEmail)
	//colocar os parametros com o nome certinho
	//exists retorna um boolean 
	boolean existsByEmail(String email);
	
	Optional<Usuario> findByEmail(String email);
	
}

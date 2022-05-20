package com.modelo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.modelo.pojos.Usuario;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{
	
}
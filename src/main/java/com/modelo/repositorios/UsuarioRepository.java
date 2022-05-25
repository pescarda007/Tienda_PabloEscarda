package com.modelo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.modelo.pojos.Usuario;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{
	// consulta difusa
    @Query(nativeQuery = true,value = " select  * from user where email like %?1% ")
    Usuario findUserByLikeEmail(String email);
}
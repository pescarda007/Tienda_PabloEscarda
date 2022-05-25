package com.modelo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.modelo.pojos.Rol;
@Repository
public interface RolRepository extends JpaRepository<Rol, Integer>{

}
package com.modelo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.modelo.pojos.Producto;


@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

}

package com.servicios;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modelo.pojos.Producto;
import com.modelo.repositorios.ProductoRepository;

@Service
public class ServiciosProducto {
	@Autowired
	ProductoRepository pr;
	
	public ArrayList<Producto> obtenerProductos(){
		return (ArrayList<Producto>) pr.findAll();
	}
	
	public Producto obtenerProductoById(int i){
		return pr.findById(i).get();
	}
	
	public void actualizar(Producto p) {
		pr.save(p);
	}
}
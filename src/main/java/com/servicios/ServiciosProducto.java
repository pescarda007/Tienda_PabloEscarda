package com.servicios;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modelo.pojos.Producto;
import com.modelo.pojos.Usuario;
import com.modelo.repositorios.ProductoRepository;

@Service
public class ServiciosProducto {
	@Autowired
	ProductoRepository prodrep;
	
	public ArrayList<Producto> obtenerProductos(){
		return (ArrayList<Producto>) prodrep.findAll();
	}
	
	public Producto obtenerProductoById(int i){
		return prodrep.findById(i).get();
	}
	
	public void crearProducto(Producto p) {
		p.setFecha_alta(Timestamp.valueOf(LocalDateTime.now()));
		prodrep.save(p);
	}
	public void actualizarProducto(Producto obj) {
		Producto actualiza = prodrep.findById(obj.getId()).get();
		actualiza.setId_categoria(obj.getId_categoria());
		actualiza.setDescripcion(obj.getDescripcion());
		actualiza.setNombre(obj.getNombre());
		actualiza.setStock(obj.getStock());
		actualiza.setPrecio(obj.getPrecio());
		actualiza.setImpuesto(obj.getImpuesto());
		actualiza.setImagen(obj.getImagen());
		prodrep.save(actualiza);
	}
	
	public void eliminarProducto(int id) {
		prodrep.delete(prodrep.findById(id).get());
	}
	
}
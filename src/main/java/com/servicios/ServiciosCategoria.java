package com.servicios;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modelo.pojos.Categoria;
import com.modelo.pojos.Rol;
import com.modelo.repositorios.CategoriaRepository;
import com.modelo.repositorios.RolRepository;

@Service
public class ServiciosCategoria {
	@Autowired
	CategoriaRepository cr;
	
	public ArrayList<Categoria> getCategorias(){
		return (ArrayList<Categoria>) cr.findAll();
	}
	
	public Categoria getCategoria(int id){
		return cr.findById(id).get();
	}	
}
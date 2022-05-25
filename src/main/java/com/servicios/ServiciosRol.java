package com.servicios;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modelo.pojos.Rol;
import com.modelo.repositorios.RolRepository;

@Service
public class ServiciosRol {
	@Autowired
	RolRepository rr;
	
	public ArrayList<Rol> getRoles() {
		return (ArrayList<Rol>)rr.findAll();
	}
	
	public Rol usuario() {
		Rol rol = rr.findById(1).get();
		return rol;
	}
	
	public Rol empleado() {
		return rr.findById(2).get();
	}
	
	public Rol admin() {
		return rr.findById(3).get();
	}
	public Rol getRol(int id) {
		return rr.findById(id).get();
	}
	
}
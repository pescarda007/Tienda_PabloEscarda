package com.servicios;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.modelo.pojos.Usuario;
import com.modelo.repositorios.UsuarioRepository;

@Service
public class ServiciosUsuario {
	@Autowired
	private UsuarioRepository ur;
	private ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
	
	public ArrayList<Usuario> obtenerUsuarios(){
		return (ArrayList<Usuario>) ur.findAll();
	}
	
	public Usuario getUsuario(String correo, String pass) {
		for(Usuario user : obtenerUsuarios()) {
			if(user.getEmail().equals(correo)) {
				if(user.getClave().equals(pass)) {
					return user;
				}
			}
		}
		return null;
	}
	
	public Usuario getUsuario(Integer id) {
		Usuario u = ur.findById(id).get();
		return u;
	}

	public Usuario crearUsuario(Usuario obj) {
		return ur.save(obj);
	}

	public void actualizarUsuario(Usuario obj) {
		ur.save(obj);
	}

	
	public void delete(int id) {
		Usuario obj = getUsuario(id);
		ur.delete(obj);
	}
	
	
}

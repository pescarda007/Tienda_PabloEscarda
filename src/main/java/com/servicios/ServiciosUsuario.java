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
		Usuario actualiza = ur.findById(obj.getId()).get();
		actualiza.setApellido1(obj.getApellido1());
		actualiza.setApellido2(obj.getApellido2());
		actualiza.setDireccion(obj.getDireccion());
		actualiza.setEmail(obj.getEmail());
		actualiza.setNombre(obj.getNombre());
		actualiza.setTelefono(obj.getTelefono());
		actualiza.setProvincia(obj.getProvincia());
		actualiza.setLocalidad(obj.getLocalidad());
		actualiza.setDni(obj.getDni());
		ur.save(actualiza);
	}
	
	public void delete(int id) {
		Usuario obj = getUsuario(id);
		ur.delete(obj);
	}
	
	
}

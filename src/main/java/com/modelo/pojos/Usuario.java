package com.modelo.pojos;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="usuarios")
@Data
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@JoinColumn(name="id_rol")
	@ManyToOne (cascade = CascadeType.ALL)
	private Rol rol;
	private String nombre;
	private String clave;
	private String email;
	private String apellido1;
	private String apellido2;
	private String direccion;
	private String provincia;
	private String localidad;
	private String telefono;
	private String dni;
	@Transient
	private int id_rol;
	public Usuario() {
		super();
		this.rol = null;
		this.nombre = "Anonimo";
	}
	
	public void setId_rol() {
		this.id_rol = this.rol.getId();
	}
	
	@Override
	public String toString() {
		return nombre;
	}

	public Usuario(int id, Rol rol, int id_rol, String nombre, String clave, String email, String apellido1,
			String apellido2, String direccion, String provincia, String localidad, String telefono, String dni) {
		super();
		this.id = id;
		this.rol = rol;
		this.nombre = nombre;
		this.clave = clave;
		this.email = email;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.direccion = direccion;
		this.provincia = provincia;
		this.localidad = localidad;
		this.telefono = telefono;
		this.dni = dni;
		setId_rol();
	}

}
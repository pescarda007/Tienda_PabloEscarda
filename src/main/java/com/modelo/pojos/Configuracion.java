package com.modelo.pojos;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="configuracion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Configuracion {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String clave;
	private String valor;
	private String tipo;
	
}

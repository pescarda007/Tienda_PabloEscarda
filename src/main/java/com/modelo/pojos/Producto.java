package com.modelo.pojos;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table(name="productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@JoinColumn(name="id_categoria")
	@ManyToOne (cascade = CascadeType.ALL)
	private Categoria id_categoria;
	private String descripcion;
	private String nombre;
	private int stock;
	private double precio;
	private Timestamp fecha_alta;
	private Timestamp fecha_baja;
	private float impuesto;
	private String imagen;
	@Transient
	private int cantidad;
}

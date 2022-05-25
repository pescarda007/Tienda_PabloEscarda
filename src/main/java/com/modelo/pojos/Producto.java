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

import org.springframework.lang.Nullable;

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
	@Nullable
	private String descripcion;
	private String nombre;
	private int stock;
	private double precio;
	@Nullable
	private Timestamp fecha_alta;
	@Nullable
	private Timestamp fecha_baja;
	@Nullable
	private float impuesto;
	@Nullable
	private String imagen;
	@Transient
	private int cantidad;
}

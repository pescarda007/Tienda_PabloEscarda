package com.modelo.pojos;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="detalles_pedido")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetallesPedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private int id;
	@JoinColumn(name="id_pedido")
	@ManyToOne (cascade = CascadeType.MERGE)
	private Pedido id_pedido;
	@JoinColumn(name="id_producto")
	@ManyToOne (cascade = CascadeType.MERGE)
	private Producto id_producto;
	private double precio_unidad;
	private int unidades;
	private float impuesto;
	private double total;

}

package com.modelo.pojos;

import java.sql.Timestamp;
import java.util.ArrayList;

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
@Table(name="pedidos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@JoinColumn(name="id_usuario")
	@ManyToOne (cascade = CascadeType.ALL)
	private Usuario id_usuario;
	private Timestamp fecha;
	private String metodo_pago;
	private String estado;
	private String num_factura;
	private double total;
	@Transient
	private ArrayList<DetallesPedido> detalles;

}

package com.servicios;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modelo.pojos.DetallesPedido;
import com.modelo.pojos.Pedido;
import com.modelo.pojos.Producto;
import com.modelo.pojos.Usuario;
import com.modelo.repositorios.PedidoRepository;

@Service
public class ServiciosPedido {
	@Autowired
	PedidoRepository pedrep;
	@Autowired
	ServiciosDetallesPedido detalles;
	

	public void crearPedido(Pedido p) {
		pedrep.save(p);
	}

	public ArrayList<Pedido> cargarPedidos() {
		ArrayList<Pedido> pedidos = (ArrayList<Pedido>) pedrep.findAll();
		for (Pedido p : pedidos) {
			cargarDetalles(p);
		}
		return pedidos;
	}

	public void cargarDetalles(Pedido p) {
		ArrayList<DetallesPedido> detallesPedido = new ArrayList<DetallesPedido>();
		for (DetallesPedido dp : detalles.cargarDetalles()) {
			if (dp.getId_pedido().getId() == p.getId()) {
				detallesPedido.add(dp);
			}
		}
		p.setDetalles(detallesPedido);
	}
	
	public Pedido getPedido(int id) {
	Pedido p = pedrep.findById(id).get();
	cargarDetalles(p);
	return p;
	}
	public void eliminarPedido(Pedido p) {
		pedrep.delete(p);
	}
	
	public void actualizarPedido(Pedido obj) {
		Pedido actualiza = pedrep.findById(obj.getId()).get();
		actualiza.setEstado(obj.getEstado());
		pedrep.save(actualiza);
	}
}
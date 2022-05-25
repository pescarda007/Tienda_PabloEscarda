package com.servicios;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modelo.pojos.DetallesPedido;
import com.modelo.repositorios.DetallesPedidoRepository;

@Service
public class ServiciosDetallesPedido {
	@Autowired
	DetallesPedidoRepository detpedrep;
	
	public void creardetalle(DetallesPedido dp) {
		detpedrep.save(dp);
	}
	
	public ArrayList<DetallesPedido> cargarDetalles(){
		ArrayList<DetallesPedido> detalles = new ArrayList<DetallesPedido>(); 
		
		detalles = (ArrayList<DetallesPedido>)detpedrep.findAll();
		
		return detalles;
	}
	
	public void eliminarDetalle(DetallesPedido detalle) {
		detpedrep.delete(detalle);
	}
	public ArrayList<DetallesPedido> getDetalle() {
		return (ArrayList<DetallesPedido>) detpedrep.findAll();
	}
}

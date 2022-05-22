package com.controlador;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.modelo.pojos.Producto;
import com.modelo.pojos.Usuario;
import com.constantes.ConstantesHTML;
import com.servicios.ServiciosProducto;
import com.servicios.ServiciosUsuario;

@Controller
@RequestMapping("")
public class ControladorPrincipal {
	@Autowired
	private ServiciosUsuario servicioUser;
	@Autowired
	private ServiciosProducto servicioproduct;

	@GetMapping("")
	public String irIndex() {
		return "redirect:/catalogo";
	}

	@GetMapping("/inicio")
	public String irLogin() {
		System.out.println("entro");
		return ConstantesHTML.inicio;
	}

	@PostMapping("/inicio")
	public String iniciarSesion(HttpSession sesion, Model model, @RequestParam String user, @RequestParam String pass) {
		String pagina = "";
		if (servicioUser.getUsuario(user, pass) != null) {
			Usuario usuario = servicioUser.getUsuario(user, pass);
			sesion.setAttribute("usuario", usuario);
			pagina = "redirect:/catalogo";
		} else {
			model.addAttribute("mensaje", "El usuario o la contraseña son incorrectos");
			pagina = ConstantesHTML.inicio;
		}
		return pagina;
	}

	@GetMapping("/registro")
	public String irRegistro(HttpSession sesion, Model model) {
		return ConstantesHTML.REGISTRO;
	}
	
	@GetMapping("/catalogo")
	public String irCatalogo(Model model, HttpSession sesion) {
		if (sesion.getAttribute("carrito") == null) {
			sesion.setAttribute("carrito", new ArrayList<Producto>());
		} else {
			sesion.setAttribute("carrito", sesion.getAttribute("carrito"));
		}
		ArrayList<Producto> productos = servicioproduct.obtenerProductos();
		model.addAttribute("listaProductos", productos);
		return ConstantesHTML.catalogo;
	}

	
	@GetMapping("/producto/tarjeta/{id}")
	public String productoMostrar(Model model, HttpSession sesion, @PathVariable("id") String id) {
		Producto producto = servicioproduct.obtenerProductoById(Integer.parseInt(id));
		model.addAttribute("producto",producto);
		return "CardProducto";
	}
	
	@GetMapping("/carrito")
	public String irCarrito(Model model, HttpSession sesion) {
		return "Carrito";
	}
	
	@GetMapping("/carrito/aniadir/{id}")
	public String aniadirCarrito(Model model, HttpSession sesion, @PathVariable("id") String id,
			@RequestParam(name = "cantidad", defaultValue = "1") String cantidad) {
		ArrayList<Producto> carrito = (ArrayList<Producto>) sesion.getAttribute("carrito");
		Producto p = servicioproduct.obtenerProductoById(Integer.parseInt(id));
		if(carrito.size() != 0) {
		for(Producto x : (ArrayList<Producto>)sesion.getAttribute("carrito")) {
			if(x.getId() == Integer.parseInt(id)) {
				x.setCantidad(x.getCantidad()+Integer.parseInt(cantidad));
			}else {
				p.setCantidad(p.getCantidad() + Integer.parseInt(cantidad));
				carrito.add(p);
			}
		}
		}else {
		p.setCantidad(p.getCantidad() + Integer.parseInt(cantidad));
		carrito.add(p);
		}
		sesion.setAttribute("carrito", carrito);
		return "Carrito";
	}
	
	@GetMapping("/carrito/eliminar/{id}")
	public String eliminarDeCarrito(Model model, HttpSession sesion, @PathVariable("id") String id) {
		ArrayList<Producto> carrito = (ArrayList<Producto>) sesion.getAttribute("carrito");
		
		for(Producto product : carrito){
			if(product.getId() == Integer.parseInt(id)){
			if(product.getCantidad()>0) {
				product.setCantidad(product.getCantidad()-1);
			}else {
				carrito.remove(product);
			}
			}
		}
		sesion.setAttribute("carrito", carrito);
		return "Carrito";
	}
	
	@PostMapping("/registro")
	public String crearUsuario(@ModelAttribute Usuario usuario){
		servicioUser.crearUsuario(usuario);
		return ConstantesHTML.inicio;
	}
	
	@GetMapping("/usuario/cerrar")
	public String cerrarSesion(Model model, HttpSession sesion) {		
		sesion.setAttribute("usuario", null);
		sesion.setAttribute("carrito", null);
		return ConstantesHTML.inicio;
	}

	@GetMapping("/usuario/comprar")
	public String comprar(Model model, HttpSession sesion, @RequestParam("total") String total) {
		ArrayList<Producto> miCarrito = (ArrayList<Producto>)sesion.getAttribute("carrito");
		for(Producto produc : miCarrito) {
			Producto p = servicioproduct.obtenerProductoById(produc.getId());
			p.setStock(p.getStock()-produc.getCantidad());
		}
		miCarrito.clear();
		sesion.setAttribute("carrito", miCarrito);
		return "redirect:/Carrito";
	}

	@GetMapping("/usuario/perfil")
	public String mostrarPerfil(Model model) {
		model.addAttribute("vista", true);
		return "Perfil";
	}

	@GetMapping("/usuario/editar")
	public String editarPerfil(Model model) {
		model.addAttribute("vista", false);
		return "Perfil";
	}
}
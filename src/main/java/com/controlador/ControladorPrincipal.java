package com.controlador;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
		return "redirect:/inicio";
	}

	@GetMapping("/inicio")
	public String irLogin() {
		System.out.println("entro");
		return ConstantesHTML.inicio;
	}

	@PostMapping("/inicio")
	public String iniciarSesion(HttpSession sesion,Model model, @RequestParam String user, @RequestParam String pass) {
		String pagina = "";
		if (servicioUser.getUsuario(user, pass) != null) {
			Usuario usuario = servicioUser.getUsuario(user, pass);
			sesion.setAttribute("usuario", usuario);
			if(sesion.getAttribute("carrito")==null) {
			sesion.setAttribute("carrito", new ArrayList<Producto>());
			}else {
				sesion.setAttribute("carrito",sesion.getAttribute("carrito"));
			}
			pagina = "redirect:/catalogo";
		} else {
			model.addAttribute("mensaje", "El usuario o la contraseña son incorrectos");
			pagina =ConstantesHTML.inicio; 
		}
		return pagina;
	}

	@GetMapping("/registro")
	public String irRegistro(HttpSession sesion,Model model) {
		if(sesion.getAttribute("carrito")==null) {
			sesion.setAttribute("carrito",new ArrayList<Producto>());
		}
		model.addAttribute("vista",false);
		return ConstantesHTML.REGISTRO;
	}

	@GetMapping("/catalogo")
	public String irCatalogo(Model model, HttpSession sesion) {
		ArrayList<Producto> productos = servicioproduct.obtenerProductos();
		model.addAttribute("listaProductos", productos);
		return ConstantesHTML.catalogo;
	}

	@GetMapping("/carrito")
	public String irCarrito(Model model, HttpSession sesion) {
		return "Carrito";
	}
	
	@GetMapping("/carrito/aniadir/{id}")
		public String aniadirCarrito(Model model, HttpSession sesion,@PathVariable("id") String id,@RequestParam(name="cantidad", defaultValue="1") String cantidad) {
		ArrayList<Producto> carrito = (ArrayList<Producto>)sesion.getAttribute("carrito");
		Producto p = servicioproduct.obtenerProductoById(Integer.parseInt(id));
		p.setCantidad(Integer.parseInt(cantidad));
		carrito.add(p);
		sesion.setAttribute("carrito",carrito);
		return ConstantesHTML.carrito;
	}
	
	@GetMapping("/usuario/cerrar")
	public String cerrarSesion(Model model, HttpSession sesion) {
		sesion.getAttribute("carrito");
		sesion.setAttribute("usuario",null);
		return ConstantesHTML.inicio;
	}
	
	@GetMapping("/usuario/comprar/{total}")
	public String comprar(Model model, HttpSession sesion, @PathVariable("total") String total) {
		sesion.getAttribute("carrito");
		
		return ConstantesHTML.inicio;
	}
	@GetMapping("/usuario/perfil")
	public String mostrarPerfil(Model model){
		model.addAttribute("vista",true);
		return "Perfil";
	}

}
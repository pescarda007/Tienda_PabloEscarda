package com.controlador;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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

import com.modelo.pojos.Categoria;
import com.modelo.pojos.DetallesPedido;
import com.modelo.pojos.Pedido;
import com.modelo.pojos.Producto;
import com.modelo.pojos.Usuario;
import com.constantes.ConstantesHTML;
import com.servicios.ServiciosCategoria;
import com.servicios.ServiciosDetallesPedido;
import com.servicios.ServiciosPedido;
import com.servicios.ServiciosProducto;
import com.servicios.ServiciosRol;
import com.servicios.ServiciosUsuario;

@Controller
@RequestMapping("")
public class ControladorPrincipal {
	@Autowired
	private ServiciosUsuario servicioUser;
	@Autowired
	private ServiciosRol servicioRol;
	@Autowired
	private ServiciosCategoria servicioCategoria;
	@Autowired
	private ServiciosProducto servicioproduct;
	@Autowired
	private ServiciosDetallesPedido serviciodetallePedido;
	@Autowired
	private ServiciosPedido servicioPedido;

	@GetMapping("")
	public String irIndex() {
		return "redirect:/catalogo";
	}

	@GetMapping("/inicio")
	public String irLogin() {
		return ConstantesHTML.INICIO;
	}

	@PostMapping("/inicio")
	public String iniciarSesion(HttpSession sesion, Model model, @RequestParam String user, @RequestParam String pass) {
		String pagina = "";
		Usuario login = servicioUser.getUsuario(user, pass);
		if (login != null) {
			Usuario usuario = login;
			sesion.setAttribute("usuario", usuario);
			if(usuario.getRol().getId() == 1) {
				pagina = "redirect:/carrito";
			}else {
				pagina = "index";
			}
		} else {
			model.addAttribute("mensaje", "El usuario o la contraseña son incorrectos");
			pagina = ConstantesHTML.INICIO;
		}
		return pagina;
	}
	
	@PostMapping("/registro")
	public String crearUsuario(@ModelAttribute Usuario usuario){
	usuario.setRol(servicioRol.usuario());
		servicioUser.crearUsuario(usuario);
		return ConstantesHTML.INICIO;
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
		return ConstantesHTML.CATALOGO;
	}
	
	@GetMapping("/producto/tarjeta/{id}")
	public String productoMostrar(Model model, HttpSession sesion, @PathVariable("id") String id) {
		Producto producto = servicioproduct.obtenerProductoById(Integer.parseInt(id));
		model.addAttribute("producto",producto);
		return "FichaProducto";
	}
	
	@GetMapping("/carrito")
	public String irCarrito(Model model, HttpSession sesion) {
		ArrayList<Producto> carrito = (ArrayList<Producto>) sesion.getAttribute("carrito");
		if(carrito!=null && carrito.size()!=0) {
			for(Producto miProducto : carrito) {
			double	totalp = ((miProducto.getPrecio()+(miProducto.getPrecio()*miProducto.getImpuesto()))*miProducto.getCantidad());
			total += totalp;
			}
			}else {
				total = 0;
			}
		model.addAttribute("total",total);
		return "Carrito";
	}
	private double total = 0;
	@GetMapping("/carrito/aniadir/{id}")
	public String aniadirCarrito(Model model, HttpSession sesion, @PathVariable("id") String id,
			@RequestParam(name = "cantidad", defaultValue = "1") String cantidad) {
		ArrayList<Producto> carrito = (ArrayList<Producto>) sesion.getAttribute("carrito");
		Producto p = null;
		if(carrito.size() != 0) {
			for(Producto miProducto : carrito) {
			if(miProducto.getId() == Integer.parseInt(id)) {
			miProducto.setCantidad(miProducto.getCantidad()+Integer.parseInt(cantidad));
			}else {
				p = servicioproduct.obtenerProductoById(Integer.parseInt(id));
				p.setCantidad(Integer.parseInt(cantidad));
			}
			}
		}else {	
		p = servicioproduct.obtenerProductoById(Integer.parseInt(id));
		p.setCantidad(Integer.parseInt(cantidad));
		}
		if(p != null) {
		carrito.add(p);
		}
		total=0;
		if(carrito.size()!=0) {
		for(Producto miProducto : carrito) {
		double	totalp = ((miProducto.getPrecio()+(miProducto.getPrecio()*miProducto.getImpuesto()))*miProducto.getCantidad());
		total += totalp;
		}
		}else {
			total = 0;
		}
		model.addAttribute("total",total);
		sesion.setAttribute("carrito", carrito);
		return "Carrito";
	}
	
	@GetMapping("/carrito/eliminar/{id}")
	public String eliminarDeCarrito(Model model, HttpSession sesion, @PathVariable("id") String id) {
		ArrayList<Producto> carrito = (ArrayList<Producto>) sesion.getAttribute("carrito");
		boolean eliminar = false;
		Producto p = null;
		for(Producto product : carrito){
			if(product.getId() == Integer.parseInt(id)){
			if(product.getCantidad()>1) {
				product.setCantidad(product.getCantidad()-1);
			}else {
				p = product;
				eliminar = true;
			}
			}
		}
		if(eliminar) {
		carrito.remove(p);
		}
		if(carrito.size()!=0) {
		for(Producto miProducto : carrito) {
			total = ((miProducto.getPrecio()+(miProducto.getPrecio()*miProducto.getImpuesto()))*miProducto.getCantidad());
		}
		}else{
			total = 0;
		}
		sesion.setAttribute("carrito", carrito);
		model.addAttribute("total",total);
		return "Carrito";
	}
	
	@GetMapping("/usuario/cerrar")
	public String cerrarSesion(Model model, HttpSession sesion) {		
		sesion.setAttribute("usuario", null);
		sesion.setAttribute("carrito", null);
		return "redirect:/catalogo";
	}

	
	@PostMapping("/usuario/comprar")
	public String comprar(Model model, HttpSession sesion, @RequestParam("total") String total,@RequestParam String metodopago) {
		ArrayList<Producto> miCarrito = (ArrayList<Producto>)sesion.getAttribute("carrito");
		Usuario user = ((Usuario)sesion.getAttribute("usuario"));
		String ir = "";
		if(user!=null && user.getRol()!= null && user.getRol().getId() == 1) {
		Usuario miusuario =	servicioUser.getUsuario(user.getId());
		Pedido pedido = new Pedido(0,miusuario,Timestamp.valueOf(LocalDateTime.now()),metodopago,"P","NA",Double.parseDouble(total),null);	
		servicioPedido.crearPedido(pedido);
		for(Producto produc : miCarrito) {
			Producto p = servicioproduct.obtenerProductoById(produc.getId());
			p.setStock(p.getStock()-produc.getCantidad());
			DetallesPedido detallesPedido = new DetallesPedido(0,pedido,produc,produc.getPrecio(),produc.getCantidad(),produc.getImpuesto(),Double.parseDouble(total));
			serviciodetallePedido.creardetalle(detallesPedido);
			servicioproduct.crearProducto(produc);
		}
		miCarrito.clear();	
		sesion.setAttribute("carrito", miCarrito);
		ir = "redirect:/carrito";
		}else {
			ir = "redirect:/inicio";
		}
		return ir;
	}

	@GetMapping("/usuario/perfil")
	public String mostrarPerfil(Model model) {
		model.addAttribute("vista", true);
		return "Perfil";
	}
	
	@GetMapping("/usuario/actualizar")
	public String actualizarPerfil(Model model) {
		System.out.println("habilitando actualizar");
		model.addAttribute("vista", false);
		return "Perfil";
	}
	
	@PostMapping("/usuario/actualizar")
	public String actualizarPerfil(Model model, HttpSession sesion, @ModelAttribute Usuario usuario) {
		model.addAttribute("vista", false);
		Usuario miusuario = (Usuario)sesion.getAttribute("usuario");
		miusuario = servicioUser.getUsuario(miusuario.getId());
		miusuario.setApellido1(usuario.getApellido1());
		miusuario.setApellido2(usuario.getApellido2());
		miusuario.setDireccion(usuario.getDireccion());
		miusuario.setEmail(usuario.getEmail());
		miusuario.setNombre(usuario.getNombre());
		miusuario.setTelefono(usuario.getTelefono());
		miusuario.setProvincia(usuario.getProvincia());
		miusuario.setLocalidad(usuario.getLocalidad());
		miusuario.setDni(usuario.getDni());
		servicioUser.actualizarUsuario(miusuario);
		return "redirect://usuario/perfil";
	}
	
	@GetMapping("/usuario/pedidos")
	public String visualizarPedidos(Model model, HttpSession sesion) {
		ArrayList<Pedido> mispedidos = new ArrayList<Pedido>();
		ArrayList<Pedido> pedidos = servicioPedido.cargarPedidos();
		for(Pedido p : pedidos) {
			if(((Usuario)sesion.getAttribute("usuario")).getId() == p.getId_usuario().getId()){
				mispedidos.add(p);
			}
		}
		model.addAttribute("pedidos",mispedidos);
		return "Pedidos";
	}
	
	@GetMapping("/usuario/pedido/{id}")
	public String visualizarDetallesPedido(Model model,@PathVariable String id) {
		ArrayList<Pedido> pedidos = servicioPedido.cargarPedidos();
		ArrayList<DetallesPedido> detalles = new ArrayList<DetallesPedido>();
		for(Pedido p : pedidos) {
			if(p.getId() == Integer.parseInt(id)) {
				detalles = p.getDetalles();
			}
		}
		model.addAttribute("detalles", detalles);
		return "DetallesPedido";
	}
	
	@GetMapping("/usuario/pedido/eliminar/{id}")
	public String eliminarPedido(Model model,@PathVariable String id) {
		ArrayList<Pedido> pedidos = servicioPedido.cargarPedidos();
		for(Pedido pedido : pedidos) {
			if(pedido.getId() == Integer.parseInt(id)) 
			{
			for(DetallesPedido pd : pedido.getDetalles()) {
				serviciodetallePedido.eliminarDetalle(pd);
			}
			servicioPedido.eliminarPedido(pedido);
			}
		}
		return "redirect:/usuario/pedidos";
	}
	
	@GetMapping("/usuario/pedido/eliminardetalle/{id}")
	public String eliminarDetallesPedido(Model model,@PathVariable String id) {
		ArrayList<DetallesPedido> detalles = serviciodetallePedido.getDetalle();
		int idpedido=0;
		double precio=0;
		for(DetallesPedido detalle : detalles ) {
			if(detalle.getId() == Integer.parseInt(id)) {
			idpedido=detalle.getId_pedido().getId();
			precio = detalle.getTotal();
			serviciodetallePedido.eliminarDetalle(detalle);
			}	
		}
		Pedido p = servicioPedido.getPedido(idpedido);
		p.setTotal(p.getTotal()-precio);
		servicioPedido.eliminarPedido(p);
		if(p.getTotal()>0) {
		servicioPedido.crearPedido(p);
		}
		return "redirect:/usuario/pedido/"+ idpedido;
	}
	
	//EMPLEADO Y ADMIN
	//manejar productos
	@GetMapping("/emple/productos")
	public String irProductos(Model model) {
		ArrayList<Producto> productos = servicioproduct.obtenerProductos();
		model.addAttribute("productos", productos);
		return "ListaProductos";
	}
	
	@GetMapping("/emple/producto/aniadir")
	public String iraniadirProductos(Model model) {
		ArrayList<Categoria> categorias = servicioCategoria.getCategorias();
		model.addAttribute("categorias", categorias);
		return "RegistroProducto";
	}
	
	@PostMapping("/emple/producto/aniadir")
	public String aniadirProductos(Model model,@ModelAttribute Producto producto,@RequestParam(name = "categoria") String id) {
		producto.setId_categoria(servicioCategoria.getCategoria(Integer.parseInt(id)));
		servicioproduct.crearProducto(producto);
		return "redirect:/emple/productos";
	}
	
	@GetMapping("/emple/producto/{id}")
	public String irProducto(Model model, @PathVariable String id) {
		ArrayList<Producto> productos = servicioproduct.obtenerProductos();
		Producto producto = null;
		for(Producto p : productos) {
			if(p.getId()==Integer.parseInt(id)) {
				producto = p;
			}
		}
		model.addAttribute("producto", producto);
		model.addAttribute("vista", true);
		return "PerfilProducto";
	}
	@GetMapping("/emple/producto/actualizar/{id}")
	public String iractualizarProducto(Model model, @PathVariable String id) {
		ArrayList<Producto> productos = servicioproduct.obtenerProductos();
		Producto producto = null;
		for(Producto p : productos) {
			if(p.getId()==Integer.parseInt(id)) {
				producto = p;
			}
		}
		model.addAttribute("producto", producto);
		model.addAttribute("vista", false);
		return "PerfilProducto";
	}
	@PostMapping("/emple/producto/actualizar")
	public String actualizarProducto(Model model, HttpSession sesion, @ModelAttribute Producto producto) {
		model.addAttribute("vista", false);
		Producto miProducto = servicioproduct.obtenerProductoById(producto.getId());
		miProducto.setId_categoria(producto.getId_categoria());
		miProducto.setDescripcion(producto.getDescripcion());
		miProducto.setNombre(producto.getNombre());
		miProducto.setStock(producto.getStock());
		miProducto.setPrecio(producto.getPrecio());
		miProducto.setImpuesto(producto.getImpuesto());
		miProducto.setImagen(producto.getImagen());
		servicioproduct.actualizarProducto(miProducto);
		return "redirect:/emple/productos";
	}
	@GetMapping("/emple/producto/eliminar/{id}")
	public String eliminarProducto(Model model, @PathVariable String id) {
		ArrayList<Producto> productos = servicioproduct.obtenerProductos();
		Producto producto = null;
		for(Producto p : productos) {
			if(p.getId()==Integer.parseInt(id)) {
				producto = p;
			}
		}
		servicioproduct.eliminarProducto(producto.getId());
		return "redirect:/emple/productos";
	}
	//manejar pedidos
	@GetMapping("/emple/pedidos")
	public String irPedidos(Model model) {
		ArrayList<Pedido> mispedidos = new ArrayList<Pedido>();
		ArrayList<Pedido> pedidos = servicioPedido.cargarPedidos();
		for(Pedido p : pedidos) {
			if(p.getEstado().equalsIgnoreCase("P")){
				mispedidos.add(p);
			}
		}
		model.addAttribute("pedidos",mispedidos);
		return "Pedidos";
	}
	@GetMapping("/emple/pedido/actualizar/{id}")
	public String irPedidos(Model model,@PathVariable String id) {
		Pedido pedido=null;
		ArrayList<Pedido> pedidosr = servicioPedido.cargarPedidos();
		for(Pedido p : pedidosr) {
			if(p.getId() == Integer.parseInt(id)) {
				p.setEstado("E");
				Random rd = new Random();
				p.setNum_factura(1230 + rd.nextInt() +"TSPE");
				pedido = p;
			}
		}
		servicioPedido.actualizarPedido(pedido);
		return "redirect:/emple/pedidos";
	}
	
	@GetMapping("/admin/pedido/eliminar/{id}")
	public String delPedidos(Model model,@PathVariable String id) {
		ArrayList<Pedido> pedidos = servicioPedido.cargarPedidos();
		Pedido pedido = null;
		for(Pedido p : pedidos) {
			if(p.getId() == Integer.parseInt(id)) {
			pedido = p;
			}
		}
		servicioPedido.eliminarPedido(pedido);
		return "redirect:/emple/pedidos";
	}
	//manejar usuarios
	@GetMapping("/emple/usuarios")
	public String mostrarUsuarios(Model model, HttpSession sesion) {
		ArrayList<Usuario> usuarios = servicioUser.obtenerUsuarios();
		ArrayList<Usuario> mostrados = new ArrayList<Usuario>();
		int rol = 0;
		if(((Usuario)sesion.getAttribute("usuario")).getRol().getId() == 2) {
			rol = 2;
		}else {
			rol = 0;
		}
		for(Usuario u : usuarios) {
			if(u.getRol().getId() != rol && u.getRol().getId() != 3) {
			mostrados.add(u);
			}
		}
		model.addAttribute("usuarios", mostrados);
		return "ListaUsuarios";
	}
	private boolean actualizar = false;
		@GetMapping("/emple/usuario/{id}")
		public String mostrarUsuario(Model model, HttpSession sesion,@PathVariable String id ) {
			ArrayList<Usuario> usuarios = servicioUser.obtenerUsuarios();	
			Usuario usuario = new Usuario();
			for(Usuario u : usuarios) {
				if(u.getId() == Integer.parseInt(id) ) {
					usuario = u;
				}
			}
			model.addAttribute("usuario", usuario);
			model.addAttribute("vista", actualizar?false:true);
			return "PerfilUsuario";
		}
		
		@GetMapping("/emple/usuario/actualizar/{id}")
		public String actualizarUsuario(@PathVariable String id) {
			actualizar = true;
			return "redirect:/emple/usuario/" + id;
		}
		@PostMapping("/emple/usuario/actualizar")
		public String actualizarUsuario(@ModelAttribute Usuario usuario, @RequestParam String idUsuario) {
			Usuario miusuario = servicioUser.getUsuario(Integer.parseInt(idUsuario));
			miusuario.setApellido1(usuario.getApellido1());
			miusuario.setApellido2(usuario.getApellido2());
			miusuario.setDireccion(usuario.getDireccion());
			miusuario.setEmail(usuario.getEmail());
			miusuario.setNombre(usuario.getNombre());
			miusuario.setTelefono(usuario.getTelefono());
			miusuario.setProvincia(usuario.getProvincia());
			miusuario.setLocalidad(usuario.getLocalidad());
			miusuario.setDni(usuario.getDni());
			servicioUser.actualizarUsuario(miusuario);		
			actualizar = false;
			return "redirect:/emple/usuarios";
		}
		
		@GetMapping("/admin/usuario/eliminar/{id}")
		public String eliminarUsuario(Model model, HttpSession sesion,@PathVariable String id ) {
			Usuario usuario = servicioUser.getUsuario(Integer.parseInt(id));	
			
			servicioUser.delete(usuario.getId());
			return "redirect:/emple/usuarios";
		}
		@GetMapping("/emple/usuario/crear")
		public String ircrearUsuario(Model model, HttpSession sesion,@ModelAttribute Usuario usuario ) {
			return "RegistroAvanzado";
		}
		@PostMapping("/emple/usuario/crear")
		public String crearUsuario(Model model, HttpSession sesion,@ModelAttribute Usuario usuario, @RequestParam String rolid) {
			usuario.setRol(servicioRol.getRol(Integer.parseInt(rolid)));
			servicioUser.crearUsuario(usuario);
			return "redirect:/emple/usuarios";
		}	
}
/**
* Scripts para el funcionamiento de los jsp
*/

/*Fincionamiento añadir producto cantidad x a carrito*/
//function ponerCantidad(cantidad, id) {
//	var url = "@{/carrito/aniadir/{id}(id=${"+ id +"})(cantidad=${"+cantidad+"})}";
//	alert(url);
//	document.getElementById(id).href = url;
//}

function ponerCantidad(id, enlace) {
	const inputCantidad = document.getElementById("cantidad"+id);
	const cantidad = inputCantidad.value;
	var url = "/carrito/aniadir/"+ id +"?cantidad="+cantidad;
	enlace.href = url;
}

function informarUsuario(){
	if(confirm()){
		
	}
}
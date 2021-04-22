package ar.edu.unahur.obj2.caralibro

class Usuario {
  val publicaciones = mutableListOf<Publicacion>()
  var amigos= mutableListOf<Usuario>()
  var listaDePermitidos= mutableListOf<Usuario>()
  var listaDeExcluidos= mutableListOf<Usuario>()
  fun agregarAmigo(amigo:Usuario){
    check(!this.amigos.contains(amigo)){"el usuario ingresado ya esta en la lista de amigos"}
    amigos.add(amigo)
  }
  fun agregarUsuarioAListaDePermitidos(usuario: Usuario){
    listaDePermitidos.add(usuario)
  }
  fun agregarUsuarioAListaDeExcluidos(usuario: Usuario){
    listaDeExcluidos.add(usuario)
  }

  fun agregarPublicacion(publicacion: Publicacion) {
    publicaciones.add(publicacion)
  }
  fun darMeGusta(publicacion: Publicacion){
    check(!publicacion.usuariosQueLeGusta.contains(this)){"Este usuario ya le ha dado me gusta a la publicacion"}
    publicacion.usuariosQueLeGusta.add(this)
    publicacion.contadorDeLikes+=1
  }
  fun puedeVer(usuarioQueQuiereVerla:Usuario,publicacion: Publicacion)=publicacion.puedeSerVistaPor(usuarioQueQuiereVerla,this )

  fun espacioDePublicaciones() = publicaciones.sumBy { it.espacioQueOcupa() }
  fun esMasAmistosoQue(usuario:Usuario)=this.amigos.size>usuario.amigos.size
}


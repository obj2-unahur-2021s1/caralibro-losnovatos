package ar.edu.unahur.obj2.caralibro

class Usuario {
  val publicaciones = mutableListOf<Publicacion>()

  fun agregarPublicacion(publicacion: Publicacion) {
    publicaciones.add(publicacion)
  }
  fun darMeGusta(publicacion: Publicacion){
    check(!publicacion.usuariosQueLeGusta.contains(this)){"Este usuario ya le ha dado me gusta a la publicacion"}
    publicacion.usuariosQueLeGusta.add(this)
    publicacion.cantidadDeMeGustas+=1
  }

  fun espacioDePublicaciones() = publicaciones.sumBy { it.espacioQueOcupa() }
}

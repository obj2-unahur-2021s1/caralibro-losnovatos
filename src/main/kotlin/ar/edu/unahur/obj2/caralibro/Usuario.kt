package ar.edu.unahur.obj2.caralibro

class Usuario {
  val publicaciones = mutableListOf<Publicacion>()

  fun agregarPublicacion(publicacion: Publicacion) {
    publicaciones.add(publicacion)
  }
  fun darMeGusta(publicacion: Publicacion){
    publicacion.usuariosQueLeGusta.add(this)
    publicacion.cantidadDeMeGustas+=1
  }

  fun espacioDePublicaciones() = publicaciones.sumBy { it.espacioQueOcupa() }
}

package ar.edu.unahur.obj2.caralibro

import kotlin.math.ceil

abstract class Publicacion {
  abstract fun espacioQueOcupa(): Int
  var usuariosQueLeGusta= mutableListOf<Usuario>()
  var cantidadDeMeGustas=0
}

class Foto(val alto: Int, val ancho: Int) : Publicacion() {
  var factorDeCompresion= 0.7
  override fun espacioQueOcupa() = ceil(alto * ancho * factorDeCompresion).toInt()
  fun cambiarFactorDeCompresion(nuevoFactor: Double){
    this.factorDeCompresion= nuevoFactor.toDouble()
  }
}

class Texto(val contenido: String) : Publicacion() {
  override fun espacioQueOcupa() = contenido.length
}

class Video(val segundos: Int, var calidad: Calidad) : Publicacion() {
  override fun espacioQueOcupa()=segundos*calidad.factorDeMultiplicacion
  fun cambiarACalidad(calidad: Calidad){
    this.calidad=calidad
  }
}
enum class Calidad(val factorDeMultiplicacion: Int) {
  SD(1),
  HD720p(3),
  HD1080p(HD720p.factorDeMultiplicacion*2)
  // hay que corregir y encontrar la mejor manera de implementar los distintos tipos de Video
}

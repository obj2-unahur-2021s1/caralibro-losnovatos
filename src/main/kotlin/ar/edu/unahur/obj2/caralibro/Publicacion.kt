package ar.edu.unahur.obj2.caralibro

import kotlin.math.ceil

abstract class Publicacion {
  abstract fun espacioQueOcupa(): Int
}

class Foto(val alto: Int, val ancho: Int) : Publicacion() {
  val factorDeCompresion = 0.7
  override fun espacioQueOcupa() = ceil(alto * ancho * factorDeCompresion).toInt()
}

class Texto(val contenido: String) : Publicacion() {
  override fun espacioQueOcupa() = contenido.length
}

class Video(val segundos: Int, var calidad: Calidad) : Publicacion() {
  override fun espacioQueOcupa(): Int {
    var calidad = when {
      calidad == Calidad.SD -> return segundos
      calidad == Calidad.HD720p -> return segundos * 3
      calidad == Calidad.HD1080p -> return segundos * 3 * 2
      else -> return error("Debe seleccionar una calidad válida.")
    }
  }

  fun cambiarAHD720p() {
    calidad = Calidad.HD720p
  }
  fun cambiarAHD1080p() {
    calidad = Calidad.HD1080p
  }
}

enum class Calidad(val calidad: Int) {
  SD(0),
  HD720p(3),
  HD1080p(6)
  // hay que corregir y encontrar la mejor manera de implementar los distintos tipos de Video
}
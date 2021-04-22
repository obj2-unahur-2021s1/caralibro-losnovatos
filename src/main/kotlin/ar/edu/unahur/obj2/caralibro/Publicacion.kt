package ar.edu.unahur.obj2.caralibro

import kotlin.math.ceil

abstract class Publicacion(var privacidad: Privacidad) {
  abstract fun espacioQueOcupa(): Int
  var usuariosQueLeGusta= mutableListOf<Usuario>()
  var cantidadDeMeGustas=0
  fun cambiarPrivacidad(privacidad: Privacidad){
    this.privacidad=privacidad
  }
  fun puedeSerVistaPor(usuarioQueQuiereVerla: Usuario,usuarioQuePublica: Usuario)=privacidad.usuariosQuePuedenVer(usuarioQueQuiereVerla,usuarioQuePublica)
}

class Foto(val alto: Int, val ancho: Int, privacidad: Privacidad) : Publicacion(privacidad) {
  override fun espacioQueOcupa() = ceil(alto * ancho * factorDeCompresion.factorActual).toInt()
}

class Texto(val contenido: String, privacidad: Privacidad) : Publicacion(privacidad) {
  override fun espacioQueOcupa() = contenido.length
}

class Video(val segundos: Int, var calidad: Calidad, privacidad: Privacidad) : Publicacion(privacidad) {
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
abstract class Privacidad(){
  abstract fun usuariosQuePuedenVer(usuarioQuePublica:Usuario,usuarioQueQuiereVerla:Usuario):Boolean
}
object publica: Privacidad() {
  override fun usuariosQuePuedenVer(usuarioQuePublica: Usuario,usuarioQueQuiereVerla: Usuario)=true
}
object soloAmigos:Privacidad(){
  override fun usuariosQuePuedenVer(usuarioQuePublica: Usuario,usuarioQueQuiereVerla: Usuario):Boolean{
    if(usuarioQuePublica==usuarioQueQuiereVerla){
      return true
    }else{
      return usuarioQuePublica.amigos.contains(usuarioQueQuiereVerla)
    }
  }

}
object conListaDePermitidos:Privacidad(){
  override fun usuariosQuePuedenVer(usuarioQuePublica: Usuario, usuarioQueQuiereVerla: Usuario): Boolean {
    if(usuarioQuePublica==usuarioQueQuiereVerla){
      return true
    }else {
      return usuarioQuePublica.listaDePermitidos.contains(usuarioQueQuiereVerla)
    }
  }
}
object conListaDeExcluidos:Privacidad(){
  override fun usuariosQuePuedenVer(usuarioQuePublica: Usuario, usuarioQueQuiereVerla: Usuario): Boolean {
    if (usuarioQuePublica == usuarioQueQuiereVerla) {
      return true
    } else {
      return !usuarioQuePublica.listaDeExcluidos.contains(usuarioQueQuiereVerla)
    }
  }
}
object factorDeCompresion{
  var factorActual=0.7
  fun cambiarFactorDeCompresion(nuevoFactor: Double){
    this.factorActual= nuevoFactor
  }
}


package ar.edu.unahur.obj2.caralibro

import kotlin.math.ceil

abstract class Publicacion(var privacidad: Privacidad) {
  abstract fun espacioQueOcupa(): Int
  var usuariosQueLeGusta= mutableListOf<Usuario>()
  var contadorDeLikes=0
  fun contadorDeLikes() = contadorDeLikes
  fun cambiarPrivacidad(privacidad: Privacidad){
    this.privacidad=privacidad
  }
  fun puedeSerVistaPor(usuarioQueQuiereVerla: Usuario,usuarioQuePublica: Usuario)= privacidad.usuariosQuePuedenVer(usuarioQueQuiereVerla,usuarioQuePublica)
  fun leDioLike(usuario: Usuario) = usuariosQueLeGusta.contains(usuario)
}

class Foto(val alto: Int, val ancho: Int, privacidad: Privacidad) : Publicacion(privacidad) {
  override fun espacioQueOcupa() = ceil(alto * ancho * FactorDeCompresion.factorActual).toInt()
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
}
abstract class Privacidad(){
  abstract fun usuariosQuePuedenVer(usuarioQuePublica:Usuario,usuarioQueQuiereVerla:Usuario):Boolean
}
object Publica: Privacidad() {
  override fun usuariosQuePuedenVer(usuarioQuePublica: Usuario,usuarioQueQuiereVerla: Usuario)=true
}
object SoloAmigos: Privacidad(){
  override fun usuariosQuePuedenVer(usuarioQuePublica: Usuario,usuarioQueQuiereVerla: Usuario):Boolean{
    if(usuarioQuePublica==usuarioQueQuiereVerla){
      return true
    }else{
      return usuarioQuePublica.amigos.contains(usuarioQueQuiereVerla)
    }
  }

}
object Permitidos: Privacidad(){
  override fun usuariosQuePuedenVer(usuarioQuePublica: Usuario, usuarioQueQuiereVerla: Usuario): Boolean {
    if(usuarioQuePublica==usuarioQueQuiereVerla){
      return true
    }else {
      return usuarioQuePublica.listaDePermitidos.contains(usuarioQueQuiereVerla)
    }
  }
}
object Excluidos: Privacidad(){
  override fun usuariosQuePuedenVer(usuarioQuePublica: Usuario, usuarioQueQuiereVerla: Usuario): Boolean {
    if (usuarioQuePublica == usuarioQueQuiereVerla) {
      return true
    } else {
      return !usuarioQuePublica.listaDeExcluidos.contains(usuarioQueQuiereVerla)
    }
  }
}
object FactorDeCompresion {
  var factorActual = 0.7
  fun cambiarFactorDeCompresion(nuevoFactor: Double) {
    this.factorActual = nuevoFactor
  }
}


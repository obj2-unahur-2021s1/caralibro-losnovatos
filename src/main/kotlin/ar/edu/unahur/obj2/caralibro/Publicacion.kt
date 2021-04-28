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
  abstract fun puedeSerVistaPor(usuarioQueQuiereVerla: Usuario,usuarioQuePublica: Usuario):Boolean
  fun leDioLike(usuario: Usuario) = usuariosQueLeGusta.contains(usuario)
}

class Foto(val alto: Int, val ancho: Int, privacidad: Privacidad) : Publicacion(privacidad) {
  override fun espacioQueOcupa() = ceil(alto * ancho * FactorDeCompresion.factorActual).toInt()
  override fun puedeSerVistaPor(usuarioQueQuiereVerla: Usuario, usuarioQuePublica: Usuario)=privacidad.usuariosQuePuedenVer(usuarioQueQuiereVerla,usuarioQuePublica)
}

class Texto(val contenido: String, privacidad: Privacidad) : Publicacion(privacidad) {
  override fun espacioQueOcupa() = contenido.length
  override fun puedeSerVistaPor(usuarioQueQuiereVerla: Usuario, usuarioQuePublica: Usuario)=privacidad.usuariosQuePuedenVer(usuarioQueQuiereVerla,usuarioQuePublica)
}

class Video(val segundos: Int, var calidad: Calidad, privacidad: Privacidad) : Publicacion(privacidad) {
  override fun espacioQueOcupa()=segundos*calidad.factorDeMultiplicacion
  override fun puedeSerVistaPor(usuarioQueQuiereVerla: Usuario, usuarioQuePublica: Usuario)=privacidad.usuariosQuePuedenVer(usuarioQueQuiereVerla,usuarioQuePublica)
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
  abstract fun usuariosQuePuedenVer(usuarioQueQuiereVer:Usuario,usuarioQuePublico:Usuario):Boolean
}
object Publica: Privacidad() {
  override fun usuariosQuePuedenVer(usuarioQueQuiereVer: Usuario,usuarioQuePublico: Usuario)=true
}
object SoloAmigos: Privacidad(){
  override fun usuariosQuePuedenVer(usuarioQueQuiereVer: Usuario,usuarioQuePublico: Usuario):Boolean{
    if(usuarioQuePublico==usuarioQueQuiereVer){
      return true
    }else{
      return usuarioQuePublico.amigos.contains(usuarioQueQuiereVer)
    }
  }

}
object Permitidos: Privacidad(){
  override fun usuariosQuePuedenVer(usuarioQueQuiereVer: Usuario, usuarioQuePublico: Usuario): Boolean {
    if(usuarioQuePublico==usuarioQueQuiereVer){
      return true
    }else {
      return usuarioQuePublico.listaDePermitidos.contains(usuarioQueQuiereVer)
    }
  }
}
object Excluidos: Privacidad(){
  override fun usuariosQuePuedenVer(usuarioQueQuiereVer: Usuario, usuarioQuePublico: Usuario)=!usuarioQuePublico.listaDeExcluidos.contains(usuarioQueQuiereVer)

}
object FactorDeCompresion {
  var factorActual = 0.7
  fun cambiarFactorDeCompresion(nuevoFactor: Double) {
    this.factorActual = nuevoFactor
  }
}


import ar.edu.unahur.obj2.caralibro.*
import kotlin.math.ceil

// Pueden usar este archivo para hacer pruebas rápidas,
// de la misma forma en que usaban el REPL de Wollok.

// OJO: lo que esté aquí no será tenido en cuenta
// en la corrección ni reemplaza a los tests.
val foto=Foto(720,1024,Publica)
val pepe=Usuario()
val juan=Usuario()
pepe.darMeGusta(foto)
juan.darMeGusta(foto)
foto.contadorDeLikes
foto.usuariosQueLeGusta.contains(pepe)
repeat(11) {pepe.agregarPublicacion(foto)}
pepe.publicaciones.size
ceil(pepe.publicaciones.size * 0.9).toInt()
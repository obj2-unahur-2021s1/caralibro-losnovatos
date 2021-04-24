package ar.edu.unahur.obj2.caralibro

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

class UsuarioTest : DescribeSpec({
  describe("Caralibro") {
    val saludoCumpleanios = Texto("Felicidades Pepito, que los cumplas muy feliz",Publica)
    val fotoEnCuzco = Foto(768, 1024, SoloAmigos)
    val videoFiesta = Video(30, Calidad.SD, Excluidos)
    val pepe=Usuario()
    val juan = Usuario()
    val sofia=Usuario()

    describe("Una publicación") {
      describe("de tipo foto") {
        it("ocupa ancho * alto * compresion bytes") {
          fotoEnCuzco.espacioQueOcupa().shouldBe(550503)
        }
      }
      describe("de tipo foto con otro factor de compresion"){
        it("ocupa ancho*alto*nuevaCompresion"){
          FactorDeCompresion.cambiarFactorDeCompresion(0.8)
          fotoEnCuzco.espacioQueOcupa().shouldBe(629146)
        }
      }

      describe("de tipo texto") {
        it("ocupa tantos bytes como su longitud") {
          saludoCumpleanios.espacioQueOcupa().shouldBe(45)
        }
      }

      describe("de tipo video") {
        describe("calidad SD") {
          it("ocupa bytes igual a la duración en segundos") {
            videoFiesta.espacioQueOcupa().shouldBe(30)
          }
        }

        describe("calidad HD720p") {
          it("ocupa bytes igual al triple de duración en segundos") {
            videoFiesta.cambiarACalidad(Calidad.HD720p)
            videoFiesta.espacioQueOcupa().shouldBe(90)
          }
        }

        describe("calidad HD1080p") {
          it("ocupa bytes igual al doble de duración en segundos que tendría en HD720p") {
            videoFiesta.cambiarACalidad(Calidad.HD1080p)
            videoFiesta.espacioQueOcupa().shouldBe(180)
          }
        }
      }
    }

    describe("Un usuario") {
      it("puede calcular el espacio que ocupan sus publicaciones") {
        val juana = Usuario()
        FactorDeCompresion.cambiarFactorDeCompresion(0.7)
        juana.agregarPublicacion(fotoEnCuzco)
        juana.agregarPublicacion(saludoCumpleanios)
        juana.espacioDePublicaciones().shouldBe(550548)
      }

     it("le da me gusta a una publicación"){
       pepe.darMeGusta(fotoEnCuzco)
       fotoEnCuzco.usuariosQueLeGusta.contains(pepe).shouldBeTrue()
     }

      it("consulta cuantas veces le dieron like a una publicación") {
        pepe.darMeGusta(videoFiesta)
        sofia.darMeGusta(videoFiesta)
        juan.darMeGusta(videoFiesta)
        pepe.cantidadDeLikesDePublicacion(videoFiesta).shouldBe(3)
      }

      it("no puede dar me gusta 2 veces a una misma publicación") {
        pepe.darMeGusta(fotoEnCuzco)
        shouldThrowAny {
          pepe.darMeGusta(fotoEnCuzco)
        }
      }

      describe("puede ver publicacion") {
        it("de foto en Cuzco de su amigo Juan.") {
          juan.agregarPublicacion(fotoEnCuzco)
          juan.agregarAmigo(pepe)
          pepe.puedeVer(juan,fotoEnCuzco).shouldBeTrue()
        }

        it("puede ver su propia publicación.") {
          pepe.agregarPublicacion(fotoEnCuzco)
          pepe.puedeVer(pepe,fotoEnCuzco).shouldBeTrue()
        }
      }

      it("no puede ver el video que subió Sofia."){
        sofia.agregarUsuarioAListaDeExcluidos(pepe)
        sofia.agregarPublicacion(videoFiesta)
        pepe.puedeVer(sofia,videoFiesta).shouldBeFalse()
      }

      it("es mas amistoso que otro usuario.") {
        val leonel = Usuario()
        pepe.agregarAmigo(sofia)
        pepe.agregarAmigo(leonel)
        pepe.agregarAmigo(juan)
        leonel.agregarAmigo(sofia)
        pepe.esMasAmistosoQue(leonel).shouldBeTrue()
        // pepe tiene 3 amigos mientras que leonel tiene 2
      }

      it("sabe cual es el amigo mas popular.") {
        pepe.agregarAmigo(sofia)
        pepe.agregarAmigo(juan)
        pepe.agregarPublicacion(saludoCumpleanios)
        sofia.agregarPublicacion(videoFiesta)
        sofia.darMeGusta(saludoCumpleanios)
        juan.darMeGusta(videoFiesta)
        pepe.darMeGusta(videoFiesta)
        pepe.amigoMasPopular().shouldBe(sofia)
      }

      describe("le da like a 9 publicaciones") {
        val saludo1 = Texto("Hola", Publica)
        val saludo2 = Texto("Como va?", Publica)
        val saludo3 = Texto("todo bien?", Publica)
        val saludo4 = Texto("Trabajas mañana?", Publica)
        val saludo5 = Texto("Trabajas mañana?", Publica)
        val saludo6 = Texto("Trabajas mañana?", Publica)
        val saludo7 = Texto("Trabajas mañana?", Publica)
        val saludo8 = Texto("Trabajas mañana?", Publica)
        val saludo9 = Texto("Trabajas mañana?", Publica)
        val saludo10 = Texto("Trabajas mañana?", Publica)

        pepe.agregarAmigo(sofia)
        pepe.agregarPublicacion(saludo1)
        pepe.agregarPublicacion(saludo2)
        pepe.agregarPublicacion(saludo3)
        pepe.agregarPublicacion(saludo4)
        pepe.agregarPublicacion(saludo5)
        pepe.agregarPublicacion(saludo6)
        pepe.agregarPublicacion(saludo7)
        pepe.agregarPublicacion(saludo8)
        pepe.agregarPublicacion(saludo9)
        pepe.agregarPublicacion(saludo10)
        sofia.darMeGusta(saludo1)
        sofia.darMeGusta(saludo2)
        sofia.darMeGusta(saludo3)
        sofia.darMeGusta(saludo4)
        sofia.darMeGusta(saludo5)
        sofia.darMeGusta(saludo6)
        sofia.darMeGusta(saludo7)
        sofia.darMeGusta(saludo8)
        sofia.darMeGusta(saludo9)

        it("de Pepe") {
          pepe.meGustasDeUnUsuarioEnMiPublicacion(sofia).shouldBe(9)
        }

        it("de un total de 10 publicaciones, por lo que es stalker") {
          pepe.meStalkea(sofia).shouldBeTrue()
        }

        it("de un total de 11 publicaciones, por lo que NO es stalker") {
          pepe.agregarPublicacion(fotoEnCuzco)
          pepe.meStalkea(sofia).shouldBeFalse()
        }
      }
    }
  }
})

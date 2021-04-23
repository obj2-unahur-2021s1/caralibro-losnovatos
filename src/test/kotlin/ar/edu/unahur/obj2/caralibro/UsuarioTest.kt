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
    }

    it("le da me gusta a una publicación"){
      pepe.darMeGusta(fotoEnCuzco)
      fotoEnCuzco.usuariosQueLeGusta.contains(pepe).shouldBeTrue()
    }

    it("consulta cuantas veces le dieron like a una publicación") {
      pepe.darMeGusta(videoFiesta)
      sofia.darMeGusta(videoFiesta)
      juan.darMeGusta(videoFiesta)
      videoFiesta.contadorDeLikes.shouldBe(3)
    }

    it("no puede dar me gusta 2 veces a una misma publicación") {
      pepe.darMeGusta(fotoEnCuzco)
      shouldThrowAny {
        pepe.darMeGusta(fotoEnCuzco)
      }
    }

    describe("puede ver publicacion") {
      it("de foto en Cuzco que subió su amigo Juan.") {
        juan.agregarPublicacion(fotoEnCuzco)
        juan.agregarAmigo(pepe)
        pepe.puedeVer(juan,fotoEnCuzco).shouldBeTrue()
      }

      it("puede ver su propia publicación") {
        pepe.agregarPublicacion(fotoEnCuzco)
        pepe.puedeVer(pepe,fotoEnCuzco).shouldBeTrue()
      }
    }

    it("no puede ver el video que subió Sofia"){
      sofia.agregarUsuarioAListaDeExcluidos(pepe)
      sofia.agregarPublicacion(videoFiesta)
      pepe.puedeVer(sofia,videoFiesta).shouldBeFalse()
    }

    it("es mas amistoso que otro usuario") {
      val leonel = Usuario()
      pepe.agregarAmigo(sofia)
      pepe.agregarAmigo(leonel)
      pepe.agregarAmigo(juan)
      leonel.agregarAmigo(sofia)
      pepe.esMasAmistosoQue(leonel).shouldBeTrue()
    }
  }
})

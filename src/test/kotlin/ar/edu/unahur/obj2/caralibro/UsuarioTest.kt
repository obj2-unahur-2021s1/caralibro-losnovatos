package ar.edu.unahur.obj2.caralibro

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

class UsuarioTest : DescribeSpec({
  describe("Caralibro") {
    val saludoCumpleanios = Texto("Felicidades Pepito, que los cumplas muy feliz")
    val fotoEnCuzco = Foto(768, 1024)
    val videoFiesta = Video(30, Calidad.SD)
    val pepe=Usuario()
    val juan = Usuario()

    describe("Una publicación") {
      describe("de tipo foto") {
        it("ocupa ancho * alto * compresion bytes") {
          fotoEnCuzco.espacioQueOcupa().shouldBe(550503)
        }
      }
      describe("de tipo foto con otro factor de compresion"){
        it("ocupa ancho*alto*nuevaCompresion"){
          fotoEnCuzco.cambiarFactorDeCompresion(0.8)
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
            videoFiesta.cambiarACalidad(calidad = Calidad.HD720p)
            videoFiesta.espacioQueOcupa().shouldBe(90)
          }
        }

        describe("calidad HD1080p") {
          it("ocupa bytes igual al doble de duración en segundos que tendría en HD720p") {
            videoFiesta.cambiarACalidad(calidad=Calidad.HD1080p)
            videoFiesta.espacioQueOcupa().shouldBe(180)
          }
        }
      }
    }

    describe("Un usuario") {
      it("puede calcular el espacio que ocupan sus publicaciones") {
        val juana = Usuario()
        juana.agregarPublicacion(fotoEnCuzco)
        juana.agregarPublicacion(saludoCumpleanios)
        juana.espacioDePublicaciones().shouldBe(550548)
      }
    }
    describe("la publicacion le gusta a pepe y cantidad de me gustas"){
      it("pepe le da me gusta"){
        pepe.darMeGusta(fotoEnCuzco)
        fotoEnCuzco.usuariosQueLeGusta.contains(pepe).shouldBeTrue()
        fotoEnCuzco.cantidadDeMeGustas.shouldBe(1)
      }
      it("no permite dar me gusta") {
        pepe.darMeGusta(fotoEnCuzco)
        shouldThrowAny {
          pepe.darMeGusta(fotoEnCuzco)
        }
      }
    }


  }
})

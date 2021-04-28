package ar.edu.unahur.obj2.caralibro

import io.kotest.matchers.shouldBe
import io.kotest.core.spec.style.DescribeSpec

class PublicacionTest : DescribeSpec ({
    val fotoEnCuzco = Foto(768, 1024, SoloAmigos)
    val saludoCumpleanios = Texto("Felicidades Pepito, que los cumplas muy feliz",Publica)
    val videoFiesta = Video(30, Calidad.SD, Excluidos)

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
})
package pixelhigh

import processing.interpolate
import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {

    val testImage = ImageIO.read(File("images/in/carlow.png"))

    for (i in 0..10) {
        val img = interpolate(testImage, 2, true)
        val outputFile = File("images/out/testimage_$i.png")
        ImageIO.write(img, "png", outputFile)
        println("Image $i done")
    }


//    val img = interpolate(testImage, 3, true)
//    val outputFile = File("images/out/testimage.png")
//    ImageIO.write(img, "png", outputFile)

}


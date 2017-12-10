package pixelhigh

import processing.interpolate
import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {

	val testImage = ImageIO.read(File("images/in/hyde.png"))

	for (i in 0..6) {
		val img = interpolate(testImage, i, colorSimilarityTreshold = 30)
		val outputFile = File("images/out/testimage_$i.png")
		ImageIO.write(img, "png", outputFile)
		Runtime.getRuntime().exec("convert images/out/testimage_$i.png -interpolate Nearest -filter point -resize 2000x2000 images/out/_testimage_${i}_res.png")
		println("Image $i done")
	}

	Runtime.getRuntime().exec("convert -loop 0 -delay 100 images/out/*res.png images/out/out.gif")
//
//    val img = interpolate(testImage, 3, true)
//    val outputFile = File("images/out/testimage.png")
//    ImageIO.write(img, "png", outputFile)

}


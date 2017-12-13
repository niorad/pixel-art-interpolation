package com.niorad.nioscale

import com.niorad.processing.interpolate
import kotlinx.coroutines.experimental.*
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {

    val testImage = ImageIO.read(File("images/in/pixularComp.png"))
    val testMask = ImageIO.read(File("images/in/pixularCompMask.png"))

    val allImages = List(50) { i ->
        launch(CommonPool) {
            doAsyncProcessing(i, testImage, testMask, 3, true, i * 5)
        }
    }

    runBlocking {
        allImages.forEach { it.join() }
        Runtime.getRuntime().exec("convert -loop 0 -delay 10 images/out/*.png images/out/out.gif")
    }

}

suspend fun doAsyncProcessing(
        index: Int,
        testImage: BufferedImage,
        maskImage: BufferedImage,
        i: Int,
        rand: Boolean,
        sim: Int
): Boolean {
    val img = interpolate(testImage, i, rand, sim, maskImage)
    val outputFile = File("images/out/testimage_$index.png")
    ImageIO.write(img, "png", outputFile)
    println("Image $index done")
    return true
}

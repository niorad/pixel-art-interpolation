package com.niorad.nioscale

import com.niorad.processing.interpolate
import com.sun.org.apache.xpath.internal.operations.Bool
import kotlinx.coroutines.experimental.*
import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.imageio.ImageIO

fun main(args: Array<String>) {

    val testImage = ImageIO.read(File("images/in/hyde.png"))

    val allImages = List(100) { i ->
        launch(CommonPool) {
            doAsyncProcessing(i, testImage, 4, true, 30)
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
        i: Int,
        rand: Boolean,
        sim: Int
): Boolean {
    val img = interpolate(testImage, i, rand, sim)
    val outputFile = File("images/out/testimage_$index.png")
    ImageIO.write(img, "png", outputFile)
    println("Image $index done")
    return true
}

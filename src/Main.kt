package pixelhigh

import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.imageio.ImageIO

fun main(args: Array<String>) {

    val testImage = ImageIO.read(File("images/in/mar.png"))

    val image1x = interpolateImage(testImage)
    val image2x = interpolateImage(image1x)
    val image3x = interpolateImage(image2x)
    val image4x = interpolateImage(image3x)

    val outputFile = File("images/out/testimage.png")
    ImageIO.write(image2x, "png", outputFile)

}


fun interpolateImage(sourceImage: BufferedImage): BufferedImage {

    val imgWidth = sourceImage.width
    val imgHeight = sourceImage.height
    val targetImage = BufferedImage(imgWidth * 2, imgHeight * 2, BufferedImage.TYPE_INT_ARGB)

    for (i in 0..(imgHeight - 1)) {
        for (j in 0..(imgWidth - 1)) {

            val color = sourceImage.getRGB(j, i)
            val cursorX = j * 2
            val cursorY = i * 2
            var substituteRightBottomColor = color
            var substituteLeftBottomColor = color
            var substituteLeftTopColor = color
            var substituteRightTopColor = color


            if (j < imgWidth - 2 && i < imgHeight - 2) {
                val rightColor = sourceImage.getRGB(j + 1, i)
                val bottomColor = sourceImage.getRGB(j, i + 1)
                val bottomRightColor = sourceImage.getRGB(j + 1, i + 1)
                if (rightColor == bottomColor && bottomColor == bottomRightColor) {
                    substituteRightBottomColor = rightColor
                }
            }

            if (j > 0 && i < imgHeight - 2) {
                val leftColor = sourceImage.getRGB(j - 1, i)
                val bottomColor = sourceImage.getRGB(j, i + 1)
                val bottomLeftColor = sourceImage.getRGB(j - 1, i + 1)
                if (leftColor == bottomColor && bottomColor == bottomLeftColor) {
                    substituteLeftBottomColor = leftColor
                }
            }

            if (j < imgWidth - 2 && i > 0) {
                val rightColor = sourceImage.getRGB(j + 1, i)
                val topColor = sourceImage.getRGB(j, i - 1)
                val topRightColor = sourceImage.getRGB(j + 1, i - 1)
                if (topColor == rightColor && rightColor == topRightColor) {
                    substituteRightTopColor = rightColor
                }
            }

            if (j > 0 && i > 0) {
                val leftColor = sourceImage.getRGB(j - 1, i)
                val topColor = sourceImage.getRGB(j, i - 1)
                val topLeftColor = sourceImage.getRGB(j - 1, i - 1)
                if (topColor == leftColor && leftColor == topLeftColor) {
                    substituteLeftTopColor = leftColor
                }
            }

            targetImage.setRGB(cursorX, cursorY, substituteLeftTopColor)
            targetImage.setRGB(cursorX + 1, cursorY + 1, substituteRightBottomColor)
            targetImage.setRGB(cursorX, cursorY + 1, substituteLeftBottomColor)
            targetImage.setRGB(cursorX + 1, cursorY, substituteRightTopColor)

        }
    }

    return targetImage

}


fun getNoise(): Int {
    val min = -30
    val max = 30
    val retval = Random().nextInt(max - min + 1) + min
    println(retval)
    return retval
}



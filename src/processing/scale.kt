package processing

import util.choose
import java.awt.image.BufferedImage

/**
 * Applies a simple interpolation-method recursively to a BufferedImage.
 * @param sourceImage
 * @param iterations How often the method will be applied. Every iteration quadruples the pixels (double width and height)
 * @param randomize Enables skipping of some interpolation-steps, to get more interesting results
 * @return the final processed image
 */
fun interpolate(sourceImage: BufferedImage, iterations: Int = 1, randomize: Boolean = false): BufferedImage {

    if (iterations > 5) {
        println("That's a lot of iterations, this may get slow")
    }

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


            if (j < imgWidth - 2 && i < imgHeight - 2 && (choose() && randomize)) {
                val rightColor = sourceImage.getRGB(j + 1, i)
                val bottomColor = sourceImage.getRGB(j, i + 1)
                val bottomRightColor = sourceImage.getRGB(j + 1, i + 1)
                if (rightColor == bottomColor && bottomColor == bottomRightColor) {
                    substituteRightBottomColor = rightColor
                }
            }

            if (j > 0 && i < imgHeight - 2 && (choose() && randomize)) {
                val leftColor = sourceImage.getRGB(j - 1, i)
                val bottomColor = sourceImage.getRGB(j, i + 1)
                val bottomLeftColor = sourceImage.getRGB(j - 1, i + 1)
                if (leftColor == bottomColor && bottomColor == bottomLeftColor) {
                    substituteLeftBottomColor = leftColor
                }
            }

            if (j < imgWidth - 2 && i > 0 && (choose() && randomize)) {
                val rightColor = sourceImage.getRGB(j + 1, i)
                val topColor = sourceImage.getRGB(j, i - 1)
                val topRightColor = sourceImage.getRGB(j + 1, i - 1)
                if (topColor == rightColor && rightColor == topRightColor) {
                    substituteRightTopColor = rightColor
                }
            }

            if (j > 0 && i > 0 && (choose() && randomize)) {
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

    return if(iterations > 0) {
        interpolate(targetImage, iterations - 1, randomize)
    } else {
        targetImage
    }

}


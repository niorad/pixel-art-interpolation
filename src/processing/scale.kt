package processing

import util.choose
import util.isColorSimilar
import java.awt.image.BufferedImage

/**
 * Applies a simple interpolation-method recursively to a BufferedImage.
 * @param sourceImage
 * @param iterations How often the method will be applied. Every iteration quadruples the pixels (double width and height)
 * @param randomize Enables skipping of some interpolation-steps, to get more interesting results
 * @param colorSimilarityTreshold How similar can colors be so they're seen as "same" at interpolation (0-255)
 * @return the final processed image
 */
fun interpolate(
        sourceImage: BufferedImage,
        iterations: Int = 1,
        randomize: Boolean = false,
        colorSimilarityTreshold: Int = 0
): BufferedImage {

    if (iterations == 0) return sourceImage

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

            val skip = if (randomize) choose() else false

            if (j < imgWidth - 2 && i < imgHeight - 2 && (!skip)) {
                val rightColor = sourceImage.getRGB(j + 1, i)
                val bottomColor = sourceImage.getRGB(j, i + 1)
                val bottomRightColor = sourceImage.getRGB(j + 1, i + 1)
                if (isColorSimilar(rightColor, bottomColor, colorSimilarityTreshold) && isColorSimilar(bottomColor, bottomRightColor, colorSimilarityTreshold)) {
                    substituteRightBottomColor = rightColor
                }
            }

            if (j > 0 && i < imgHeight - 2 && (!skip)) {
                val leftColor = sourceImage.getRGB(j - 1, i)
                val bottomColor = sourceImage.getRGB(j, i + 1)
                val bottomLeftColor = sourceImage.getRGB(j - 1, i + 1)
                if (isColorSimilar(leftColor, bottomColor, colorSimilarityTreshold) && isColorSimilar(bottomColor, bottomLeftColor, colorSimilarityTreshold)) {
                    substituteLeftBottomColor = leftColor
                }
            }

            if (j < imgWidth - 2 && i > 0 && (!skip)) {
                val rightColor = sourceImage.getRGB(j + 1, i)
                val topColor = sourceImage.getRGB(j, i - 1)
                val topRightColor = sourceImage.getRGB(j + 1, i - 1)
                if (isColorSimilar(topColor, rightColor, colorSimilarityTreshold) && isColorSimilar(rightColor, topRightColor, colorSimilarityTreshold)) {
                    substituteRightTopColor = rightColor
                }
            }

            if (j > 0 && i > 0 && (!skip)) {
                val leftColor = sourceImage.getRGB(j - 1, i)
                val topColor = sourceImage.getRGB(j, i - 1)
                val topLeftColor = sourceImage.getRGB(j - 1, i - 1)
                if (isColorSimilar(topColor, leftColor, colorSimilarityTreshold) && isColorSimilar(leftColor, topLeftColor, colorSimilarityTreshold)) {
                    substituteLeftTopColor = leftColor
                }
            }

            targetImage.setRGB(cursorX, cursorY, substituteLeftTopColor)
            targetImage.setRGB(cursorX + 1, cursorY + 1, substituteRightBottomColor)
            targetImage.setRGB(cursorX, cursorY + 1, substituteLeftBottomColor)
            targetImage.setRGB(cursorX + 1, cursorY, substituteRightTopColor)

        }
    }

    return interpolate(targetImage, iterations - 1, randomize)

}


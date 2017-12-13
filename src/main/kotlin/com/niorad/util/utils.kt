package com.niorad.util

import java.awt.Color
import java.awt.image.BufferedImage
import java.util.*

data class NioColor(val R: Int, val G: Int, val B: Int, val A: Int)
data class NioRect(val x: Int, val y: Int, val w: Int, val h: Int)

/**
 * Returns true or false randomly
 * @return True if a random number is 0 (50/50 chance)
 */
fun choose(): Boolean {
    return Random().nextInt(2) == 0
}

/**
 * Checks if a color is similar to another color by simply making sure that in no channel the difference is higher than n
 * @param colorA
 * @param colorB
 * @param treshold The maximum difference a channel is allowed to have to still be similar
 */
fun isColorSimilar(colorA: Int, colorB: Int, treshold: Int = 0): Boolean {

    val a = intToColor(colorA)
    val b = intToColor(colorB)

    val diffR = Math.abs(a.R - b.R)
    val diffG = Math.abs(a.G - b.G)
    val diffB = Math.abs(a.B - b.B)

    return (diffR <= treshold && diffG <= treshold && diffB <= treshold)

}

/**
 * Converts a color-int to NioColor, so RGBA is usable
 */
fun intToColor(colorInt: Int): NioColor {
    val retCol = Color(colorInt)
    return NioColor(retCol.red, retCol.green, retCol.blue, retCol.alpha)
}


/**
 * Scales an Image up one step without any interpolation (nearest neighbor)
 */
fun simpleScaleUp(sourceImage: BufferedImage): BufferedImage {
    val imgWidth = sourceImage.width
    val imgHeight = sourceImage.height
    val targetImage = BufferedImage(imgWidth * 2, imgHeight * 2, BufferedImage.TYPE_INT_ARGB)

    for (i in 0..(imgHeight - 1)) {
        for (j in 0..(imgWidth - 1)) {
            val color = sourceImage.getRGB(j, i)
            val cursorX = j * 2
            val cursorY = i * 2
            targetImage.setRGB(cursorX, cursorY, color)
            targetImage.setRGB(cursorX + 1, cursorY + 1, color)
            targetImage.setRGB(cursorX, cursorY + 1, color)
            targetImage.setRGB(cursorX + 1, cursorY, color)
        }
    }
    return targetImage
}

package util

import sun.awt.image.PixelConverter
import java.awt.Color
import java.util.*

data class NioColor(val R: Int, val G: Int, val B: Int, val A: Int)

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
fun isColorSimilar(colorA: Int, colorB: Int, treshold: Int = 0) : Boolean {

    val a = intToColor(colorA)
    val b = intToColor(colorB)

    val diffR = Math.abs(a.R - b.R)
    val diffG = Math.abs(a.G - b.G)
    val diffB = Math.abs(a.B - b.B)

    return (diffR < treshold && diffG < treshold && diffB < treshold)

}


fun intToColor(colorInt: Int) : NioColor {
    val retCol = Color(colorInt)
    return NioColor(retCol.red, retCol.green, retCol.blue, retCol.alpha)
}

package util

import kotlin.test.assertFalse

internal class UtilsKtTest {

	@org.junit.jupiter.api.Test
	fun closeColorsShouldBeSimilar() {
		assert(isColorSimilar(13158600, 13224393, 1))
	}

	@org.junit.jupiter.api.Test
	fun colorsShouldNotBeSimilar() {
		assertFalse(isColorSimilar(13158600, 16435400, 20))
	}

	@org.junit.jupiter.api.Test
	fun intToRgbConversionShouldWork() {
		val colorA = NioColor(100, 100, 100, 255)
		val colorB = intToColor(6579300)
		assert(colorA == colorB)
	}
}

package se.tink.core.extensions

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import se.tink.core.models.misc.Amount
import se.tink.core.models.misc.ExactNumber
import java.math.BigDecimal
import java.math.MathContext


internal class AmountExtensionTest {

    @Test
    fun compare() {
        val ten = Amount(ExactNumber.TEN, "SEK")
        val two = Amount(ExactNumber.TWO, "SEK")
        assertThat(ten > two)
        assertThat(two < ten)
        assertThat(ten == two).isFalse()
        assertThat(ten < two).isFalse()
        assertThat(two > ten).isFalse()

        val alsoTwo = Amount(ExactNumber.TWO, "SEK")
        assertThat(two > alsoTwo).isFalse()
        assertThat(alsoTwo > two).isFalse()
        assertThat(two == alsoTwo)
        assertThat(two >= alsoTwo)
        assertThat(two <= alsoTwo)
    }

    @Test
    fun `can not compare Amounts with different currency codes`() {

        Assertions.assertThrows(
            IllegalArgumentException::class.java
        ) {
            val first = Amount(ExactNumber.TEN, "SEK")
            val second = Amount(ExactNumber.TWO, "EUR")
            first > second
        }

        Assertions.assertThrows(
            IllegalArgumentException::class.java
        ) {
            val first = Amount(ExactNumber.TEN, "SEK")
            val second = Amount(ExactNumber.TWO, "")
            first > second
        }

    }

    @Test
    fun subtract() {
        val two = Amount(ExactNumber.TWO, "SEK")
        val one = Amount(ExactNumber.ONE, "SEK")
        val expected = Amount(ExactNumber.ONE, "SEK")
        assertThat(two - one).isEqualTo(expected)

        val ten = Amount(ExactNumber.TEN, "SEK")
        val six = Amount(ExactNumber(BigDecimal(6)), "SEK")
        val four = Amount(ExactNumber(BigDecimal(4)), "SEK")
        assertThat(ten - six).isEqualTo(four)

        val zero = Amount(ExactNumber.ZERO, "SEK")
        assertThat(ten - zero).isEqualTo(ten)


        val minusFive = Amount(ExactNumber(BigDecimal(-5)), "SEK")
        val minusTwo = Amount(ExactNumber(BigDecimal(-2)), "SEK")
        val minusThree = Amount(ExactNumber(BigDecimal(-3)), "SEK")
        assertThat(minusFive - minusThree).isEqualTo(minusTwo)
        assertThat(one - minusThree).isEqualTo(four)
        assertThat(minusTwo - one).isEqualTo(minusThree)
    }

    @Test
    fun `can not subtract Amount with different currency code`() {

        Assertions.assertThrows(
            IllegalArgumentException::class.java
        ) {
            val first = Amount(ExactNumber.TEN, "EUR")
            val second = Amount(ExactNumber.TWO, "SEK")
            first - second
        }

        Assertions.assertThrows(
            IllegalArgumentException::class.java
        ) {
            val first = Amount(ExactNumber.TEN, "EUR")
            val second = Amount(ExactNumber.TWO, "")
            first > second
        }
    }

    @Test
    fun `divide by Double`() {
        assertThat(Amount(ExactNumber.ONE, "SEK")).isEqualTo(Amount(ExactNumber.TWO, "SEK") / 2.0)
        assertThat(Amount(ExactNumber(200, 1), "SEK")).isEqualTo(
            Amount(
                ExactNumber(220, 1),
                "SEK"
            ) / 1.1
        )

        val values = arrayOf(
            BigDecimal(2938.2391283),
            BigDecimal(0.000000002),
            BigDecimal(-400.34832873874),
            BigDecimal.ONE,
            BigDecimal(Double.MAX_VALUE),
            BigDecimal(Double.MIN_VALUE),
            BigDecimal.valueOf(2000004080923L, 4),
            BigDecimal.valueOf(Long.MAX_VALUE, 1),
            BigDecimal.valueOf(Long.MIN_VALUE, Int.MAX_VALUE - 305)
        )

        val divisors = arrayOf(
            343.55,
            400.0,
            -400.0,
            3432798432.234324,
            Double.MIN_VALUE,
            Double.MAX_VALUE,
            0.000000000000000000000000000000000000000001
        )

        for (value in values) {
            for (divisor in divisors) {
                val amount = Amount(ExactNumber(value), "SEK")
                val expected = Amount(
                    ExactNumber(
                        amount.value.asBigDecimal().divide(
                            BigDecimal(divisor),
                            MathContext.DECIMAL64
                        )
                    ),
                    amount.currencyCode
                )
                val result = amount / divisor
                assertThat(result).isEqualTo(expected)
            }
        }
    }

    @Test
    fun `cannot divide by 0 (Double)`() {
        Assertions.assertThrows(
            ArithmeticException::class.java
        ) {
            Amount(ExactNumber.TEN, "SEK") / 0.0
        }
    }

    @Test
    fun average() {
        run {
            val list = listOf(
                Amount(ExactNumber.TEN, "SEK"),
                Amount(ExactNumber(BigDecimal(6)), "SEK"),
                Amount(ExactNumber(BigDecimal(5)), "SEK")
            )
            val expected = Amount(ExactNumber((BigDecimal(7))), "SEK")

            assertThat(list.average()).isEqualTo(expected)
        }
        run {
            val list = listOf(
                Amount(ExactNumber.ONE, "SEK"),
                Amount(ExactNumber.ONE, "SEK"),
                Amount(ExactNumber.ONE, "SEK"),
                Amount(ExactNumber.ONE, "SEK"),
                Amount(ExactNumber.ONE, "SEK"),
                Amount(ExactNumber.ONE, "SEK"),
                Amount(ExactNumber.ONE, "SEK")
            )
            val expected = Amount(ExactNumber.ONE, "SEK")

            assertThat(list.average()).isEqualTo(expected)
        }
    }

    @Test
    fun `average fails for inconsistent currencies`() {
        Assertions.assertThrows(
            IllegalArgumentException::class.java
        ) {
            listOf(
                Amount(ExactNumber.ONE, "SEK"),
                Amount(ExactNumber.ONE, "EUR")
            ).average()
        }
    }
}

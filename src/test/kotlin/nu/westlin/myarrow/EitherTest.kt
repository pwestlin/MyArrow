package nu.westlin.myarrow

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@Suppress("RemoveRedundantBackticks")
internal class EitherTest {

    private val leftValue = "left value"
    private val left = Either.left(leftValue)

    private val rightValue = "right value"
    private val right = Either.right(rightValue)

    @Test
    fun `catch - no exception`() {
        assertThat(Either.catch { "foo" }.fold({ it }, { it })).isEqualTo("foo")
    }

    @Test
    fun `catch - exception`() {
        data class TestException(val msg: String): RuntimeException(msg)

        assertThat(Either.catch { throw TestException("exception") }.fold(
            { it },
            { it })
        ).isEqualTo(TestException("exception"))
    }

    @Nested
    inner class LeftTest {


        @Test
        fun `create`() {
            assertThat(left).isInstanceOf(Either.Left::class.java)
        }

        @Test
        fun `fold`() {
            assertThat(left.fold({ it }, { it })).isEqualTo(leftValue)
        }

        @Test
        fun `map`() {
            assertThat(left.map { "left" }).isEqualTo(left)
        }
    }

    @Nested
    inner class RightTest {

        @Test
        fun `create Right`() {
            assertThat(right).isInstanceOf(Either.Right::class.java)
        }

        @Test
        fun `fold`() {
            assertThat(right.fold({ it }, { it })).isEqualTo(rightValue)
        }

        @Test
        fun `map`() {
            assertThat(right.map { "$it is right" }).isEqualTo(Either.right("$rightValue is right"))
        }
    }

    @Nested
    inner class EitherTopLevelFunctionsTest {

        @Test
        fun `create Left`() {
            with(Left("left")) {
                assertThat(this).isInstanceOf(Either.Left::class.java)
                assertThat(this.fold({ it }, { it })).isEqualTo("left")
            }
        }

        @Test
        fun `create Right`() {
            with(Right("right")) {
                assertThat(this).isInstanceOf(Either.Right::class.java)
                assertThat(this.fold({ it }, { it })).isEqualTo("right")
            }
        }
    }
}



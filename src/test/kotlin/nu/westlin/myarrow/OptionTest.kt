package nu.westlin.myarrow

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class OptionTest {

    @Nested
    inner class CompanionObject {

        @Test
        fun `just create it`() {
            with(Option.just(7)) {
                assertThat(this.getOrElse { -1 }).isEqualTo(7)
            }
        }

        @Test
        fun `from nullable - not null`() {
            with(Option.fromNullable("a")) {
                assertThat(this.getOrElse { -1 }).isEqualTo("a")
            }
        }

        @Test
        fun `from nullable - null`() {
            with(Option.fromNullable(null)) {
                assertThat(this.getOrElse { -1 }).isEqualTo(-1)
            }
        }

        @Test
        fun `none is None`() {
            val intOption: Option<Int> = Option.empty()
            with(intOption) {
                assertThat(this).isEqualTo(None)
            }
        }

    }

    @Test
    fun `isEmpty - is empty`() {
        assertThat(Option.just(1).isEmpty()).isFalse()
    }

    @Test
    fun `fold if empty`() {
        assertThat(Option.empty<Int>().fold({ "empty" }, { "not empty" })).isEqualTo("empty")
    }

    @Test
    fun `fold if not empty`() {
        assertThat(Option.just(42).fold({ -1 }, { it })).isEqualTo(42)
    }

    @Test
    fun `map if empty`() {
        assertThat(Option.empty<Int>().map { it.toString() }).isEqualTo(None)
    }

    @Test
    fun `map if not empty`() {
        assertThat(Option.just(94).map { it.toString() }).isEqualTo(Option.just("94"))
    }

    @Nested
    inner class TopLevelFunctions {

        @Test
        fun `getOrElse - not empty`() {
            assertThat(Option.just(7).getOrElse { -1 }).isEqualTo(7)
        }

        @Test
        fun `getOrElse - empty`() {
            assertThat(Option.empty<Int>().getOrElse { -1 }).isEqualTo(-1)
        }

        @Test
        fun `none should be None`() {
            assertThat(none<String>()).isEqualTo(None)
        }
    }

}
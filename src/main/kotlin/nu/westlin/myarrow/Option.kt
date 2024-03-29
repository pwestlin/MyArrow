package nu.westlin.myarrow

sealed class Option<out A> {
    companion object {
        fun <A> just(a: A): Option<A> = Some(a)

        fun <A> fromNullable(a: A?): Option<A> = a?.let { Some(a) } ?: None

        fun <A> empty(): Option<A> = None
    }

    abstract fun isEmpty(): Boolean

    inline fun <R> fold(ifEmpty: () -> R, ifSome: (A) -> R): R = when (this) {
        is None -> ifEmpty()
        is Some<A> -> ifSome(a)
    }

    fun <B> map(f: (A) -> B): Option<B> =
      flatMap { a -> Some(f(a)) }

    fun <B> flatMap(f: (A) -> Option<B>): Option<B> =
      when (this) {
        is None -> this
        is Some -> f(a)
      }

    fun orNull(): A? = fold({ null }, ::identity)
}

data class Some<A>(val a: A) : Option<A>() {
    override fun isEmpty(): Boolean = false
    override fun toString(): String = "Some($a)"
}

object None : Option<Nothing>() {
    override fun isEmpty() = true

    override fun toString(): String = "None"
}

fun <A> identity(a: A): A = a

fun <T> Option<T>.getOrElse(default: () -> T): T = fold({ default() }, ::identity)

fun <A> none(): Option<A> = None


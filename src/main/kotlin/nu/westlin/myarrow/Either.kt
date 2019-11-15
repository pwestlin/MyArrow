package nu.westlin.myarrow

sealed class Either<out A, out B> {
    data class Left<out A> internal constructor(val a: A) : Either<A, Nothing>()
    data class Right<out B> internal constructor(val b: B) : Either<Nothing, B>()

    fun <C> fold(ifLeft: (A) -> C, ifRight: (B) -> C): C = when (this) {
        is Left -> ifLeft(a)
        is Right -> ifRight(b)
    }

    fun <C> map(f: (B) -> C): Either<A, C> = when (this) {
        is Left -> this
        is Right -> right(f(b))
    }

    companion object {
        fun <L> left(a: L): Either<L, Nothing> = Left(a)

        fun <R> right(b: R): Either<Nothing, R> = Right(b)

        fun <R> catch(f: () -> R): Either<Throwable, R> = try {
            Right(f())
        } catch (e: Throwable) {
            Left(e)
        }
    }
}

@Suppress("FunctionName")
fun <L> Left(a: L): Either<L, Nothing> = Either.left(a)

@Suppress("FunctionName")
fun <R> Right(b: R): Either<Nothing, R> = Either.right(b)
package io.craigmiller160.funcoasthiattendance.function

import arrow.core.Either

typealias TryEither<T> = Either<Throwable, T>

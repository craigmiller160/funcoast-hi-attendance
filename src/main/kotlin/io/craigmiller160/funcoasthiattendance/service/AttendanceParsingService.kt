package io.craigmiller160.funcoasthiattendance.service

import arrow.core.Either
import io.craigmiller160.funcoasthiattendance.function.TryEither
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service

@Service
class AttendanceParsingService(private val resourceLoader: ResourceLoader) {
  companion object {
    private const val FILE = "attendance.csv"
  }
  fun parse(): TryEither<String> =
    Either.catch { resourceLoader.getResource(FILE).inputStream.reader().readText() }
}

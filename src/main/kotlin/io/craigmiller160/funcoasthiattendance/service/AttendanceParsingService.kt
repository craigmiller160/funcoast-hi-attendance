package io.craigmiller160.funcoasthiattendance.service

import arrow.core.Either
import com.opencsv.CSVReader
import io.craigmiller160.funcoasthiattendance.function.TryEither
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service

@Service
class AttendanceParsingService(private val resourceLoader: ResourceLoader) {
  companion object {
    private const val FILE = "attendance.csv"
  }
  fun parse(): TryEither<Sequence<Array<String>>> =
    Either.catch {
      CSVReader(resourceLoader.getResource(FILE).inputStream.reader())
        .readAll()
        .asSequence()
        .drop(1)
    }
}

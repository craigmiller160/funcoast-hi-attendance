package io.craigmiller160.funcoasthiattendance.service

import arrow.core.Either
import com.opencsv.CSVReader
import io.craigmiller160.funcoasthiattendance.function.TryEither
import io.craigmiller160.funcoasthiattendance.model.AttendanceRecord
import io.craigmiller160.funcoasthiattendance.model.AttendanceStatus
import org.slf4j.LoggerFactory
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service

@Service
class AttendanceParsingService(private val resourceLoader: ResourceLoader) {
  companion object {
    private const val FILE = "classpath:attendance.csv"
  }
  private val log = LoggerFactory.getLogger(javaClass)
  fun parse(): TryEither<List<AttendanceRecord>> {
    log.debug("Parsing CSV")
    return Either.catch {
        CSVReader(resourceLoader.getResource(FILE).inputStream.reader()).readAll()
      }
      .map { records ->
        records.drop(1).map {
          AttendanceRecord(
            name = it[0],
            phone = it[1],
            email = it[2],
            status = AttendanceStatus.fromString(it[3]),
            panel = it[4])
        }
      }
  }
}

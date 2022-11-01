package io.craigmiller160.funcoasthiattendance.service

import arrow.core.Either
import arrow.core.sequence
import io.craigmiller160.funcoasthiattendance.function.TryEither
import io.craigmiller160.funcoasthiattendance.model.AttendanceRecord
import io.craigmiller160.funcoasthiattendance.model.AttendanceStatus
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BackstopService(private val jdbcTemplate: NamedParameterJdbcTemplate) {
  companion object {
    private const val INSERT_BACKSTOP =
      """
      INSERT INTO backstop (person_id, member, "returning")
      VALUES (:personId, :member, :returning)
    """
  }
  private val log = LoggerFactory.getLogger(javaClass)
  @Transactional
  fun addToBackstop(records: List<AttendanceRecord>): TryEither<List<AttendanceRecord>> {
    log.debug("Adding backstop entries for members")
    return records
      .filter { it.status == AttendanceStatus.MEMBER }
      .map { createBackstopForPerson(it) }
      .sequence()
      .map { records }
  }

  private fun createBackstopForPerson(record: AttendanceRecord): TryEither<AttendanceRecord> =
    Either.catch {
      val params =
        MapSqlParameterSource()
          .addValue("personId", record.dbId)
          .addValue("member", record.status == AttendanceStatus.MEMBER)
          .addValue("returning", record.status == AttendanceStatus.RETURNING)
      jdbcTemplate.update(INSERT_BACKSTOP, params)

      record
    }
}

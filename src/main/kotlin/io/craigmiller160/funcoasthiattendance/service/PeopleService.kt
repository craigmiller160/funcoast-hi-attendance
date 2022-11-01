package io.craigmiller160.funcoasthiattendance.service

import arrow.core.Either
import arrow.core.sequence
import io.craigmiller160.funcoasthiattendance.function.TryEither
import io.craigmiller160.funcoasthiattendance.model.AttendanceRecord
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PeopleService(private val jdbcTemplate: NamedParameterJdbcTemplate) {
  companion object {
    private const val INSERT_PERSON =
      """
      INSERT INTO people (name, phone, email)
      VALUES (:name, :phone, :email)
    """
    private const val GET_ID = """
      SELECT currval('people_person_id_seq')
    """
  }
  private val log = LoggerFactory.getLogger(javaClass)
  @Transactional
  fun createPeople(records: List<AttendanceRecord>): TryEither<List<AttendanceRecord>> {
    log.debug("Creating people records")
    return records.map { createPerson(it) }.sequence()
  }

  private fun createPerson(record: AttendanceRecord): TryEither<AttendanceRecord> =
    Either.catch {
      val params =
        MapSqlParameterSource()
          .addValue("name", record.name)
          .addValue("phone", record.phone)
          .addValue("email", record.email)
      jdbcTemplate.update(INSERT_PERSON, params)
      val id = jdbcTemplate.queryForObject(GET_ID, MapSqlParameterSource(), Long::class.java)!!
      record.copy(dbId = id)
    }
}

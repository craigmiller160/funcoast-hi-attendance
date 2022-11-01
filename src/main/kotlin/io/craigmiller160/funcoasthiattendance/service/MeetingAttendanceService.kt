package io.craigmiller160.funcoasthiattendance.service

import arrow.core.Either
import arrow.core.sequence
import io.craigmiller160.funcoasthiattendance.config.FuncoastAttendanceConfig
import io.craigmiller160.funcoasthiattendance.function.TryEither
import io.craigmiller160.funcoasthiattendance.model.AttendanceRecord
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service

@Service
class MeetingAttendanceService(
  private val jdbcTemplate: NamedParameterJdbcTemplate,
  private val attendanceConfig: FuncoastAttendanceConfig
) {
  companion object {
    private const val INSERT_MEETING =
      """
            INSERT INTO meeting_attendance (person_id, meeting_date)
            VALUES (:personId, :meetingDate)
        """
  }
  private val log = LoggerFactory.getLogger(javaClass)
  fun addFirstMeeting(records: List<AttendanceRecord>): TryEither<List<AttendanceRecord>> {
    log.debug("Adding first meeting entries for: ${attendanceConfig.firstMeetingDate}")
    return records.map { addFirstMeetingToRecord(it) }.sequence()
  }

  private fun addFirstMeetingToRecord(record: AttendanceRecord): TryEither<AttendanceRecord> =
    Either.catch {
      val params =
        MapSqlParameterSource()
          .addValue("personId", record.dbId)
          .addValue("meetingDate", attendanceConfig.firstMeetingDate)
      jdbcTemplate.update(INSERT_MEETING, params)
      record
    }
}

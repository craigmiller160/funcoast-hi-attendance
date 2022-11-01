package io.craigmiller160.funcoasthiattendance.service

import arrow.core.Either
import io.craigmiller160.funcoasthiattendance.function.TryEither
import io.craigmiller160.funcoasthiattendance.model.AttendanceRecord
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service

@Service
class MeetingAttendanceService(private val jdbcTemplate: NamedParameterJdbcTemplate) {
  companion object {
    private const val INSERT_MEETING =
      """
            INSERT INTO meeting_attendance (person_id, meeting_date)
            VALUES (:personId, :meetingDate)
        """
  }
  fun addLastMeeting(records: List<AttendanceRecord>): TryEither<List<AttendanceRecord>> =
    Either.Right(records)
}

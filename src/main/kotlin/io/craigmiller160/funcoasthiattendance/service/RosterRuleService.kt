package io.craigmiller160.funcoasthiattendance.service

import arrow.core.Either
import io.craigmiller160.funcoasthiattendance.function.TryEither
import io.craigmiller160.funcoasthiattendance.model.AttendanceRecord
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RosterRuleService(private val jdbcTemplate: NamedParameterJdbcTemplate) {
  companion object {
    private const val INSERT_RULE =
      """
            INSERT INTO roster_rules (start_date, end_present, num_meetings_become_member, out_of_meetings_become_member, num_meetings_stay_member, out_of_meetings_stay_member)
            VALUES ('2022-01-01'::date, true, 2, 3, 1, 3)
        """
  }
  private val log = LoggerFactory.getLogger(javaClass)

  @Transactional
  fun createRules(records: List<AttendanceRecord>): TryEither<List<AttendanceRecord>> {
    log.debug("Creating roster rules")
    return Either.catch {
      jdbcTemplate.update(INSERT_RULE, MapSqlParameterSource())
      records
    }
  }
}

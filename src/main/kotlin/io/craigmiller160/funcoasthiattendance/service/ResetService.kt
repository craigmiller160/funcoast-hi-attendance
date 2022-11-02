package io.craigmiller160.funcoasthiattendance.service

import arrow.core.Either
import io.craigmiller160.funcoasthiattendance.function.TryEither
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service

@Service
class ResetService(private val jdbcTemplate: NamedParameterJdbcTemplate) {
  companion object {
    private const val WIPE_PANEL_MEMBERS = "DELETE FROM panel_membership"
    private const val WIPE_PANELS = "DELETE FROM panels"
    private const val WIPE_MEETING_ATTENDANCE = "DELETE FROM meeting_attendance"
    private const val WIPE_ROSTERS = "DELETE FROM rosters"
    private const val WIPE_ROSTER_RULES = "DELETE FROM roster_rules"
    private const val WIPE_BACKSTOP = "DELETE FROM backstop"
    private const val WIPE_PEOPLE = "DELETE FROM people"
  }

  private val log = LoggerFactory.getLogger(javaClass)
  fun resetAllData(): TryEither<Unit> =
    Either.catch {
      log.debug("Resetting all data")
      jdbcTemplate.update(WIPE_ROSTER_RULES, MapSqlParameterSource())
      jdbcTemplate.update(WIPE_ROSTERS, MapSqlParameterSource())
      jdbcTemplate.update(WIPE_PANEL_MEMBERS, MapSqlParameterSource())
      jdbcTemplate.update(WIPE_PANELS, MapSqlParameterSource())
      jdbcTemplate.update(WIPE_MEETING_ATTENDANCE, MapSqlParameterSource())
      jdbcTemplate.update(WIPE_BACKSTOP, MapSqlParameterSource())
      jdbcTemplate.update(WIPE_PEOPLE, MapSqlParameterSource())
    }
}

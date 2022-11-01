package io.craigmiller160.funcoasthiattendance.service

import arrow.core.Either
import io.craigmiller160.funcoasthiattendance.function.TryEither
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service

@Service
class ResetService(private val jdbcTemplate: NamedParameterJdbcTemplate) {
  companion object {
    private const val WIPE_BACKSTOP = "DELETE FROM backstop"
    private const val WIPE_PEOPLE = "DELETE FROM people"
  }
  fun resetAllData(): TryEither<Unit> =
    Either.catch {
      jdbcTemplate.update(WIPE_BACKSTOP, MapSqlParameterSource())
      jdbcTemplate.update(WIPE_PEOPLE, MapSqlParameterSource())
    }
}

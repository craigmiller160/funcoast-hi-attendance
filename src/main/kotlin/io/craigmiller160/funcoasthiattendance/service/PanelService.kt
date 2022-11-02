package io.craigmiller160.funcoasthiattendance.service

import arrow.core.Either
import arrow.core.sequence
import io.craigmiller160.funcoasthiattendance.function.TryEither
import io.craigmiller160.funcoasthiattendance.model.Day
import io.craigmiller160.funcoasthiattendance.model.Panel
import java.time.LocalTime
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service

@Service
class PanelService(private val jdbcTemplate: NamedParameterJdbcTemplate) {
  companion object {
    private const val INSERT_PANEL =
      """
            INSERT INTO panels (name, address1, address2, day, time, restrictions)
            VALUES (:name, :address1, :address2, :day, :time, :restrictions)
        """
    private const val GET_ID = """
          SELECT currval('panels_panel_id_seq')
        """

    private val PANELS =
      listOf(
        Panel(
          name = "River Oaks",
          address1 = "12012 Boyette Rd",
          address2 = "Riverview, FL 33569",
          day = Day.TUESDAY,
          time = LocalTime.of(18, 30),
          restrictions = ""),
        Panel(
          name = "7th Summit",
          address1 = "https://us02web.zoom.us/j/89472266859",
          address2 = "Password: 022512",
          day = Day.WEDNESDAY,
          time = LocalTime.of(19, 0),
          restrictions = ""),
        Panel(
          name = "Riverside Recovery",
          address1 = "4004 N Riverside Dr",
          address2 = "Tampa, FL 33603",
          day = Day.WEDNESDAY,
          time = LocalTime.of(19, 0),
          restrictions = ""),
        Panel(
          name = "Gracepoint",
          address1 = "2214 E Henry Ave",
          address2 = "Tampa, FL 33610",
          day = Day.WEDNESDAY,
          time = LocalTime.of(19, 30),
          restrictions = ""),
        Panel(
          name = "COVE (DACCO) Family",
          address1 = "https://us02web.zoom.us/j/89381226699",
          address2 = "Password: 806522",
          day = Day.THURSDAY,
          time = LocalTime.of(19, 0),
          restrictions = "Women Only"),
        Panel(
          name = "COVE (DACCO) R3",
          address1 = "3107 N 50th St",
          address2 = "Tampa, FL 33619",
          day = Day.FRIDAY,
          time = LocalTime.of(18, 30),
          restrictions = "Men Only"),
        Panel(
          name = "COVE (DACCO) R1)",
          address1 = "https://us02web.zoom.us/j/84006618716",
          address2 = "Password: 076306",
          day = Day.FRIDAY,
          time = LocalTime.of(19, 0),
          restrictions = "Women Only"))
  }
  private val log = LoggerFactory.getLogger(javaClass)

  fun createPanels(): TryEither<List<Panel>> {
    log.debug("Creating panels")
    return PANELS.map { createPanel(it) }.sequence()
  }

  private fun createPanel(panel: Panel): TryEither<Panel> =
    Either.catch {
      val params =
        MapSqlParameterSource()
          .addValue("name", panel.name)
          .addValue("address1", panel.address1)
          .addValue("address2", panel.address2)
          .addValue("day", panel.day.number)
          .addValue("time", panel.time)
          .addValue("restrictions", panel.restrictions)
      jdbcTemplate.update(INSERT_PANEL, params)
      val id = jdbcTemplate.queryForObject(GET_ID, MapSqlParameterSource(), Long::class.java)!!
      panel.copy(dbId = id)
    }
}

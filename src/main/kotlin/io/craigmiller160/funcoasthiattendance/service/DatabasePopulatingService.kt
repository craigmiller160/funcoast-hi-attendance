package io.craigmiller160.funcoasthiattendance.service

import arrow.core.flatMap
import io.craigmiller160.funcoasthiattendance.function.TryEither
import io.craigmiller160.funcoasthiattendance.model.AttendanceRecord
import io.craigmiller160.funcoasthiattendance.model.PanelsAndAttendance
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DatabasePopulatingService(
  private val peopleService: PeopleService,
  private val backstopService: BackstopService,
  private val meetingAttendanceService: MeetingAttendanceService,
  private val rosterRuleService: RosterRuleService,
  private val panelService: PanelService
) {
  private val log = LoggerFactory.getLogger(javaClass)
  @Transactional
  fun populate(records: List<AttendanceRecord>): TryEither<PanelsAndAttendance> {
    log.debug("Populating database records")
    return panelService
      .createPanels()
      .flatMap { panels ->
        peopleService.createPeople(records).map { PanelsAndAttendance(it, panels) }
      }
      .flatMap { data ->
        backstopService
          .addToBackstop(data.attendance)
          .flatMap { meetingAttendanceService.addFirstMeeting(it) }
          .map { data.copy(attendance = it) }
      }
      .flatMap { data -> rosterRuleService.createRules().map { data } }
  }
}

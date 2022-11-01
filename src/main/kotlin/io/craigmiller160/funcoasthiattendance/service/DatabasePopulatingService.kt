package io.craigmiller160.funcoasthiattendance.service

import arrow.core.flatMap
import io.craigmiller160.funcoasthiattendance.function.TryEither
import io.craigmiller160.funcoasthiattendance.model.AttendanceRecord
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DatabasePopulatingService(
  private val peopleService: PeopleService,
  private val backstopService: BackstopService,
  private val meetingAttendanceService: MeetingAttendanceService,
  private val rosterRuleService: RosterRuleService
) {
  private val log = LoggerFactory.getLogger(javaClass)
  @Transactional
  fun populate(records: List<AttendanceRecord>): TryEither<List<AttendanceRecord>> {
    log.debug("Populating database records")
    return peopleService
      .createPeople(records)
      .flatMap { backstopService.addToBackstop(it) }
      .flatMap { meetingAttendanceService.addFirstMeeting(it) }
      .flatMap { rosterRuleService.createRules(it) }
  }
}

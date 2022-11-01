package io.craigmiller160.funcoasthiattendance.service

import arrow.core.flatMap
import io.craigmiller160.funcoasthiattendance.function.TryEither
import io.craigmiller160.funcoasthiattendance.model.AttendanceRecord
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DatabasePopulatingService(
  private val peopleService: PeopleService,
  private val backstopService: BackstopService,
  private val rosterRuleService: RosterRuleService
) {
  @Transactional
  fun populate(records: List<AttendanceRecord>): TryEither<List<AttendanceRecord>> =
    peopleService
      .createPeople(records)
      .flatMap { backstopService.addToBackstop(it) }
      .flatMap { rosterRuleService.createRules(it) }
}

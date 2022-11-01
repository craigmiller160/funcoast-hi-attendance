package io.craigmiller160.funcoasthiattendance.service

import arrow.core.flatMap
import arrow.core.sequence
import io.craigmiller160.funcoasthiattendance.function.TryEither
import io.craigmiller160.funcoasthiattendance.model.AttendanceRecord
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DatabasePopulatingService(
  private val peopleService: PeopleService,
  private val backstopService: BackstopService
) {
  @Transactional
  fun populate(records: List<AttendanceRecord>): TryEither<List<AttendanceRecord>> =
    peopleService.createPeople(records).flatMap { recs ->
      recs.map { backstopService.addToBackstop(it) }.sequence()
    }
}

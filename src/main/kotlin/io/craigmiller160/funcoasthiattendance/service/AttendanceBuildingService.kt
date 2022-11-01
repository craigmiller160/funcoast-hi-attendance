package io.craigmiller160.funcoasthiattendance.service

import arrow.core.flatMap
import arrow.core.sequence
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AttendanceBuildingService(
  private val resetService: ResetService,
  private val attendanceParsingService: AttendanceParsingService,
  private val personService: PersonService,
  private val backstopService: BackstopService
) {
  @Transactional
  fun build() {
    resetService
      .resetAllData()
      .flatMap { attendanceParsingService.parse() }
      .flatMap { records -> records.map { personService.createPerson(it) }.sequence() }
      .flatMap { records -> records.map { backstopService.addToBackstop(it) }.sequence() }
      .map { it.toList() } // TODO better way to end this needed
  }
}

package io.craigmiller160.funcoasthiattendance.service

import arrow.core.flatMap
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AttendanceBuildingService(
  private val resetService: ResetService,
  private val attendanceParsingService: AttendanceParsingService,
  private val databasePopulatingService: DatabasePopulatingService
) {
  @Transactional
  fun build() {
    resetService
      .resetAllData()
      .flatMap { attendanceParsingService.parse() }
      .flatMap { databasePopulatingService.populate(it) }
  }
}

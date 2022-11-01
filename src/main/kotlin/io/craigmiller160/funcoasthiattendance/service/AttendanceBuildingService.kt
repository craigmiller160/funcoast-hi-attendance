package io.craigmiller160.funcoasthiattendance.service

import arrow.core.flatMap
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AttendanceBuildingService(
  private val resetService: ResetService,
  private val attendanceParsingService: AttendanceParsingService,
  private val databasePopulatingService: DatabasePopulatingService,
  private val funcoastApiService: FuncoastApiService
) {
  private val log = LoggerFactory.getLogger(javaClass)
  @Transactional
  fun build() {
    resetService
      .resetAllData()
      .flatMap { attendanceParsingService.parse() }
      .flatMap { databasePopulatingService.populate(it) }
      .flatMap { funcoastApiService.calculateRoster() }
      .fold(this::logException, this::logSuccess)
  }

  private fun logException(ex: Throwable) {
    log.error("Error building attendance data", ex)
  }

  private fun logSuccess(empty: Unit) {
    log.info("Successfully built attendance data")
  }
}

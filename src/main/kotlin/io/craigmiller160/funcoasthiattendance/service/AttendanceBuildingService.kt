package io.craigmiller160.funcoasthiattendance.service

import org.springframework.stereotype.Service

@Service
class AttendanceBuildingService(private val attendanceParsingService: AttendanceParsingService) {
  fun build() {
    attendanceParsingService.parse().map { recs -> recs.forEach { println(it) } }
  }
}

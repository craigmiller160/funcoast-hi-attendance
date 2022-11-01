package io.craigmiller160.funcoasthiattendance

import io.craigmiller160.funcoasthiattendance.service.AttendanceBuildingService
import javax.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FuncoastHiAttendanceApplication(
  private val attendanceBuildingService: AttendanceBuildingService
) {
  @PostConstruct
  fun run() {
    attendanceBuildingService.build()
  }
}

fun main(args: Array<String>) {
  runApplication<FuncoastHiAttendanceApplication>(*args)
}

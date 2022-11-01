package io.craigmiller160.funcoasthiattendance

import io.craigmiller160.funcoasthiattendance.service.AttendanceBuildingService
import javax.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
@ConfigurationPropertiesScan
class FuncoastHiAttendanceApplication(
  private val attendanceBuildingService: AttendanceBuildingService
) {
  @PostConstruct
  @Async
  fun run() {
    attendanceBuildingService.build()
  }
}

fun main(args: Array<String>) {
  runApplication<FuncoastHiAttendanceApplication>(*args)
}

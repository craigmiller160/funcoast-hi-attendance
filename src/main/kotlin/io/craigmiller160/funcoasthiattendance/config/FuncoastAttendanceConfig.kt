package io.craigmiller160.funcoasthiattendance.config

import java.time.LocalDate
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.format.annotation.DateTimeFormat

@ConstructorBinding
@ConfigurationProperties(prefix = "funcoast.attendance")
class FuncoastAttendanceConfig(
  @DateTimeFormat(pattern = "yyyy-MM-dd") val firstMeetingDate: LocalDate
)

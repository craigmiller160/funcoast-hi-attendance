package io.craigmiller160.funcoasthiattendance.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "funcoast.attendance")
class FuncoastAttendanceConfig(private val firstMeetingDate: String)

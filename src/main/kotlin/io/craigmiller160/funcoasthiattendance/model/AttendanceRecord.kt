package io.craigmiller160.funcoasthiattendance.model

data class AttendanceRecord(
  val name: String,
  val phone: String,
  val email: String,
  val status: AttendanceStatus,
  val panel: String,
  val dbId: Long = 0
)

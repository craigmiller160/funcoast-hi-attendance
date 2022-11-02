package io.craigmiller160.funcoasthiattendance.model

import java.time.LocalTime

data class Panel(
  val name: String,
  val address1: String,
  val address2: String,
  val day: Day,
  val time: LocalTime,
  val restrictions: String,
  val dbId: Long = 0
)

package io.craigmiller160.funcoasthiattendance.model

enum class AttendanceStatus {
  MEMBER,
  RETURNING;

  companion object {
    fun fromString(value: String): AttendanceStatus =
      when (value) {
        "Returning" -> RETURNING
        else -> MEMBER
      }
  }
}

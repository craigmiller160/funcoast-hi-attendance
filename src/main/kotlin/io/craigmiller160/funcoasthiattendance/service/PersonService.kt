package io.craigmiller160.funcoasthiattendance.service

import io.craigmiller160.funcoasthiattendance.function.TryEither
import io.craigmiller160.funcoasthiattendance.model.AttendanceRecord
import org.springframework.stereotype.Service

@Service
class PersonService {
  fun createPerson(record: AttendanceRecord): TryEither<AttendanceRecord> = TODO()
}

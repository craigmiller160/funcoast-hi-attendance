package io.craigmiller160.funcoasthiattendance.service

import io.craigmiller160.funcoasthiattendance.function.TryEither
import io.craigmiller160.funcoasthiattendance.model.AttendanceRecord
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PersonService {
  @Transactional fun createPerson(record: AttendanceRecord): TryEither<AttendanceRecord> = TODO()
}

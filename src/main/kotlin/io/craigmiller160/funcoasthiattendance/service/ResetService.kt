package io.craigmiller160.funcoasthiattendance.service

import io.craigmiller160.funcoasthiattendance.function.TryEither
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ResetService {
  @Transactional fun resetAllData(): TryEither<Unit> = TODO()
}

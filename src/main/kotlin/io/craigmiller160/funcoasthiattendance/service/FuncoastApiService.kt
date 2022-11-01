package io.craigmiller160.funcoasthiattendance.service

import io.craigmiller160.funcoasthiattendance.function.TryEither
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class FuncoastApiService(private val webClient: WebClient) {
  fun calculateRoster(): TryEither<Unit> = TODO()
}

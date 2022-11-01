package io.craigmiller160.funcoasthiattendance.service

import io.craigmiller160.funcoasthiattendance.function.TryEither
import io.craigmiller160.funcoasthiattendance.model.OAuth2Values
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class FuncoastApiService(private val webClient: WebClient, private val oAuth2Values: OAuth2Values) {
  fun calculateRoster(): TryEither<Unit> = TODO()

  private fun authenticate() {}
}

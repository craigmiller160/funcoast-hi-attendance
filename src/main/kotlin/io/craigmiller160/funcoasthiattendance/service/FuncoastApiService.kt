package io.craigmiller160.funcoasthiattendance.service

import arrow.core.Either
import io.craigmiller160.funcoasthiattendance.function.TryEither
import io.craigmiller160.funcoasthiattendance.model.AuthenticationResponse
import io.craigmiller160.funcoasthiattendance.model.OAuth2Values
import java.util.Base64
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

@Service
class FuncoastApiService(private val webClient: WebClient, private val oAuth2Values: OAuth2Values) {
  fun calculateRoster(): TryEither<Unit> {
    return authenticate().map {
      println(it)
      Unit
    }
  }

  private fun authenticate(): TryEither<AuthenticationResponse> {
    val result =
      webClient
        .post()
        .uri("/oauth2/oauth/token")
        .header("Authorization", createAuthenticateBasicAuthHeader())
        .body(
          BodyInserters.fromFormData("grant_type", "password")
            .with("username", oAuth2Values.userName)
            .with("password", oAuth2Values.password))
        .retrieve()
        .bodyToMono(AuthenticationResponse::class.java)
        .block()!!
    return Either.Right(result) // TODO clean this up a lot
  }

  private fun createAuthenticateBasicAuthHeader(): String {
    val raw = "${oAuth2Values.clientKey}:${oAuth2Values.clientSecret}"
    return Base64.getEncoder().encodeToString(raw.toByteArray())
  }
}

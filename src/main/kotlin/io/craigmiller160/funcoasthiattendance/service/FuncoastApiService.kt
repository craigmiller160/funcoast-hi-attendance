package io.craigmiller160.funcoasthiattendance.service

import arrow.core.Either
import arrow.core.flatMap
import io.craigmiller160.funcoasthiattendance.exception.FuncoastApiException
import io.craigmiller160.funcoasthiattendance.function.TryEither
import io.craigmiller160.funcoasthiattendance.model.AuthenticationResponse
import io.craigmiller160.funcoasthiattendance.model.OAuth2Values
import java.util.Base64
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class FuncoastApiService(private val webClient: WebClient, private val oAuth2Values: OAuth2Values) {
  private val log = LoggerFactory.getLogger(javaClass)
  fun calculateRoster(): TryEither<Unit> {
    log.debug("Calling API to calculate new roster")
    return authenticate()
      .flatMap { auth ->
        webClient
          .post()
          .uri("/funcoast-hi/api/roster/calculate")
          .header("Authorization", "Bearer ${auth.accessToken}")
          .retrieve()
          .onStatus(this::isErrorStatus, this::handleError)
          .bodyToMono(String::class.java)
          .map { Either.Right(it) as TryEither<String> } // IDE is wrong, casting is needed here
          .onErrorResume { Mono.just(Either.Left(it)) }
          .block()
          ?: Either.Right("")
      }
      .map { Unit }
  }

  private fun authenticate(): TryEither<AuthenticationResponse> {
    return webClient
      .post()
      .uri("/oauth2/oauth/token")
      .header("Authorization", createAuthenticateBasicAuthHeader())
      .body(
        BodyInserters.fromFormData("grant_type", "password")
          .with("username", oAuth2Values.userName)
          .with("password", oAuth2Values.password))
      .retrieve()
      .onStatus(this::isErrorStatus, this::handleError)
      .bodyToMono(AuthenticationResponse::class.java)
      .map {
        Either.Right(it) as TryEither<AuthenticationResponse>
      } // IDE is wrong, casting is needed here
      .onErrorResume { Mono.just(Either.Left(it)) }
      .block()!!
  }

  private fun isErrorStatus(status: HttpStatus): Boolean =
    status.is4xxClientError || status.is5xxServerError

  private fun handleError(response: ClientResponse): Mono<out Throwable> =
    response.bodyToMono(String::class.java).map {
      FuncoastApiException(response.rawStatusCode(), it)
    }

  private fun createAuthenticateBasicAuthHeader(): String {
    val raw = "${oAuth2Values.clientKey}:${oAuth2Values.clientSecret}"
    return "Basic ${Base64.getEncoder().encodeToString(raw.toByteArray())}"
  }
}

package io.craigmiller160.funcoasthiattendance.service

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class FuncoastApiService(private val webClient: WebClient) {
  companion object {
    private const val TARGET_HOST = "https://apps-craigmiller160.ddns.net"
    private const val OAUTH2_ROUTE = "/oauth2"
    private const val FUNCOAST_ROUTE = "/funcoast-hi"
    private const val AUTHENTICATE_URI = "/oauth/token"
    private const val CALCULATE_ROSTER_URI = "/roster/calculate"
  }
}

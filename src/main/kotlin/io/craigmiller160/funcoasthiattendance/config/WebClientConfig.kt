package io.craigmiller160.funcoasthiattendance.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {
  companion object {
    private const val TARGET_HOST = "https://apps-craigmiller160.ddns.net"
    private const val OAUTH2_ROUTE = "/oauth2"
    private const val FUNCOAST_ROUTE = "/funcoast-hi"
    private const val AUTHENTICATE_URI = "/oauth/token"
    private const val CALCULATE_ROSTER_URI = "/roster/calculate"
  }
  @Bean fun webClient(): WebClient = WebClient.create(TARGET_HOST)
}

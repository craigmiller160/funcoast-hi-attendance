package io.craigmiller160.funcoasthiattendance.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {
  companion object {
    private const val TARGET_HOST = "https://apps-craigmiller160.ddns.net"
  }
  @Bean fun webClient(): WebClient = WebClient.create(TARGET_HOST)
}

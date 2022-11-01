package io.craigmiller160.funcoasthiattendance.config

import io.craigmiller160.funcoasthiattendance.model.OAuth2Values
import java.io.File
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.yaml.snakeyaml.Yaml

@Configuration
class OAuth2Config {
  @Bean
  fun oauth2Values(): OAuth2Values {
    val file = File("./oauth2.yml")
    if (!file.exists()) {
      throw IllegalStateException("Must configure oauth2.yml file")
    }

    val yaml = Yaml().load<Map<String, Any>>(file.reader())
    return yamlToValues(yaml)
  }

  private fun yamlToValues(yaml: Map<String, Any>): OAuth2Values {
    val oauth2 = getMap(yaml, "oauth2")
    val funcoast = getMap(oauth2, "funcoast-hi")
    val user = getMap(oauth2, "user")
    return OAuth2Values(
      clientKey = getString(funcoast, "client-key"),
      clientSecret = getString(funcoast, "client-secret"),
      userName = getString(user, "name"),
      password = getString(user, "password"))
  }

  private fun getMap(yaml: Map<String, Any>, key: String): Map<String, Any> =
    yaml[key] as Map<String, Any>
  private fun getString(yaml: Map<String, Any>, key: String): String = yaml[key] as String
}

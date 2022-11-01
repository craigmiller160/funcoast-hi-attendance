package io.craigmiller160.funcoasthiattendance.model

data class AuthenticationResponse(
  val accessToken: String,
  val refreshToken: String,
  val tokenId: String
)

package io.craigmiller160.funcoasthiattendance.exception

class FuncoastApiException(status: Int, body: String) :
  RuntimeException("Error calling Funcoast H&I API. Status: $status Body: $body")

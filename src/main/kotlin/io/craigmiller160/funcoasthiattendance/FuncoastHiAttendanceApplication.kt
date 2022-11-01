package io.craigmiller160.funcoasthiattendance

import javax.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class FuncoastHiAttendanceApplication {
  @Async @PostConstruct fun run() {}
}

fun main(args: Array<String>) {
  runApplication<FuncoastHiAttendanceApplication>(*args)
}

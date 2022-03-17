package com.example.microservice.payment.component

import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration


var httpClient: HttpClient = HttpClient.create().responseTimeout(Duration.ofSeconds(1))

@Component
class SecurityServiceClient(
  val webClient: WebClient = WebClient.builder().clientConnector(ReactorClientHttpConnector(httpClient)).build()
) {


  fun requestAdvice(user: String, value: Int): String? {
    return webClient.post()
      .uri("localhost:8080/api/security")
      .bodyValue("$user:$value")
      .retrieve()
      .bodyToMono(String::class.java)
      .block()
  }
}

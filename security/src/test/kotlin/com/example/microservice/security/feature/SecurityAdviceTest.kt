package com.example.microservice.security.feature

import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.web.reactive.server.WebTestClient

class SecurityAdviceTest : WithWebEnvironment() {

  @Test
  fun whereWeHandleAthenasRequest() {
    // TODO? refactor to step libraries or screenplay
    sendLowRiskRequest().apply {
      ensureSucessfulStatus(this)
      ensureOkResponse(this)
    }
  }

  @Test
  fun whereWeHandleRikoRequest() {
    val responseSpec = sendHighRiskRequest()
    ensureSucessfulStatus(responseSpec)
    ensure2faResponse(responseSpec)
  }

  private fun ensureOkResponse(responseSpec: WebTestClient.ResponseSpec) {
    responseSpec.expectBody().consumeWith {
      val responseBody: ByteArray? = it.responseBody
      val bytes: ByteArray = responseBody!!
      assertThat(String(bytes), `is`("OK"))
    }
  }

  private fun ensure2faResponse(responseSpec: WebTestClient.ResponseSpec) {
    responseSpec.expectBody().consumeWith {
      val responseBody: ByteArray? = it.responseBody
      val bytes: ByteArray = responseBody!!
      assertThat(String(bytes), `is`("do 2fa"))
    }
  }

  private fun ensureSucessfulStatus(responseSpec: WebTestClient.ResponseSpec) {
    responseSpec.expectStatus().is2xxSuccessful
  }

  private fun sendLowRiskRequest(): WebTestClient.ResponseSpec =
    webTestClient.post()
      .uri("http://localhost:$port/api/security")
      .bodyValue("""athena:99""")
      .exchange()

  private fun sendHighRiskRequest(): WebTestClient.ResponseSpec =
    webTestClient.post()
      .uri("http://localhost:$port/api/security")
      .bodyValue("""riko:1000000""")
      .exchange()

}

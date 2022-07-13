package com.example.microservice.security.feature

import com.example.microservice.security.Payment
import com.example.microservice.security.PaymentHistoryService
import com.example.microservice.security.model.response.SecurityAdviceResponse
import com.google.gson.Gson
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.*
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

class SecurityAdviceTest : WithWebEnvironment() {

  @MockkBean(relaxed = true)
  lateinit var paymentHistory: PaymentHistoryService

  @Test
  internal fun whereRikoHasBadHistory() {
    Given.badHistoryFor("riko", paymentHistory)
    sendLowRiskRequest("riko").apply {
      ensureSucessfulStatus(this)
      ensure2faResponse(this)
    }
  }

  @Test
  fun whereWeHandleAthenasRequest() {
    // TODO? refactor to step libraries or screenplay
    sendLowRiskRequest("""athena""").apply {
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
      assertThat(String(bytes), `is`("{\"message\":\"OK\"}"))
    }
  }

  private fun ensure2faResponse(responseSpec: WebTestClient.ResponseSpec) {
    responseSpec.expectBody().consumeWith {
      val responseBody: ByteArray? = it.responseBody
      val bytes: ByteArray = responseBody!!
      val string = String(bytes)
      val response = Gson().fromJson(string, SecurityAdviceResponse::class.java)
      assertThat(response, `is`(SecurityAdviceResponse("do 2fa")))
    }
  }

  private fun ensureSucessfulStatus(responseSpec: WebTestClient.ResponseSpec) {
    responseSpec.expectStatus().is2xxSuccessful
  }

  private fun sendLowRiskRequest(user: String): WebTestClient.ResponseSpec {
    return webTestClient.post()
      .uri("http://localhost:$port/api/security")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue("""{"name":"$user", "amount":99}""")
      .exchange()
  }

  private fun sendHighRiskRequest(): WebTestClient.ResponseSpec =
    webTestClient.post()
      .uri("http://localhost:$port/api/security")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue("""{"name":"riko", "amount":1000000}""")
      .exchange()

}

object Given {
  fun badHistoryFor(user: String, paymentHistoryMock: PaymentHistoryService) {
    every { paymentHistoryMock.getHistory(user)} returns listOf(Payment(user, true))
  }

}

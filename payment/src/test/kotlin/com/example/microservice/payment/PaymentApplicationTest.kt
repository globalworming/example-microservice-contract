package com.example.microservice.payment

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentApplicationTest {

  @LocalServerPort
  var port: Int = 0

  @Autowired
  lateinit var webTestClient: WebTestClient

  @Test
  internal fun `where athena makes a low risk payment`() {
    val response = webTestClient.post().uri("/pay").bodyValue(
      """{user: 'Athena', amount: 5.99}"""
    ).exchange()
    ensureOkResponse(response)
  }

  private fun ensureOkResponse(responseSpec: WebTestClient.ResponseSpec) {
    responseSpec.expectBody().consumeWith {
      val responseBody: ByteArray? = it.responseBody
      val bytes: ByteArray = responseBody!!
      MatcherAssert.assertThat(String(bytes), CoreMatchers.`is`("payment sucessful"))
    }
  }


}
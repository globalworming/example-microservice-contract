package com.example.microservice.payment.securityServiceClient

import com.example.microservice.payment.Given
import com.example.microservice.payment.Step
import com.example.microservice.payment.UsingMockWebServer
import com.example.microservice.payment.component.SecurityServiceClient
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class ResponseSpecification : UsingMockWebServer() {

  @Autowired
  lateinit var securityServiceClient: SecurityServiceClient

  @Test
  internal fun `where security service returns OK`() {
    Given.mockWebSeverReturnsOk(securityService)
    val response = Step.requestAdviceForAthena(securityServiceClient)
    assertThat(response, `is`("OK"))
  }

  @Test
  internal fun `where security service advises 2fa`() {
    Given.mockWebSeverReturns2faAdvice(securityService)
    val response = Step.requestAdviceForRiko(securityServiceClient)
    assertThat(response, `is`("do 2fa"))
  }
}


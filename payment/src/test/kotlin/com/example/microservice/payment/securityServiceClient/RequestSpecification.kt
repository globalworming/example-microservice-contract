package com.example.microservice.payment.securityServiceClient

import com.example.microservice.payment.Ensure
import com.example.microservice.payment.Given
import com.example.microservice.payment.Step
import com.example.microservice.payment.UsingMockWebServer
import com.example.microservice.payment.component.SecurityServiceClient
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class RequestSpecification : UsingMockWebServer() {

  @Autowired
  lateinit var securityServiceClient: SecurityServiceClient

  @Test
  fun `where we send low risk request to security service`() {
    Given.mockWebSeverReturnsOk(securityService)
    Step.requestAdviceForAthena(securityServiceClient)
    Ensure.requestMatchesLowRiskRequest(securityService)
  }

  @Test
  fun `where we send high risk request to security service`() {
    Given.mockWebSeverReturns2faAdvice(securityService)
    Step.requestAdviceForRiko(securityServiceClient)
    Ensure.requestMatchesHighRiskRequest(securityService)
  }
}


package com.example.microservice.payment


import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach


open class UsingMockWebServer {
  lateinit var securityService: MockWebServer

  @BeforeEach
  internal fun setUp() {
    securityService = MockWebServer()
    securityService.start(8080)
  }

  @AfterEach
  internal fun tearDown() {
    securityService.shutdown()
  }
}

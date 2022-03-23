package com.example.microservice.payment

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

class Given {
  companion object {
    fun mockWebSeverReturnsOk(webserver: MockWebServer) {
      webserver.enqueue(MockResponse().setBody("""OK"""))
    }

    fun mockWebSeverReturns2faAdvice(webserver: MockWebServer) {
      webserver.enqueue(MockResponse().setBody("""do 2fa"""))
    }

  }
}
package com.example.microservice.payment

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

class Given {
  companion object {
    fun returnsOk(webserver: MockWebServer) {
      webserver.enqueue(MockResponse().setBody("""OK"""))
    }

  }
}
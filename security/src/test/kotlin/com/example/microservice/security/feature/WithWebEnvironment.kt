package com.example.microservice.security.feature

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
class WithWebEnvironment {
  @LocalServerPort
  var port: Int = 0

  @Autowired
  lateinit var webTestClient: WebTestClient

}

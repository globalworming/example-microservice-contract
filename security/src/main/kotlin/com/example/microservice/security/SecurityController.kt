package com.example.microservice.security

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SecurityController {

  @PostMapping("/api/security")
  fun securityAdvice(@RequestBody body: String): String {
    if (body.contains("athena")) {
      return "OK"
    }
    return "do 2fa"
  }

}
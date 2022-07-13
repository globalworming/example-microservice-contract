package com.example.microservice.security

import com.example.microservice.security.model.request.SecurityAdviceRequest
import com.example.microservice.security.model.response.SecurityAdviceResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SecurityController(val paymentHistoryService: PaymentHistoryService) {

  @PostMapping("/api/security")
  fun securityAdvice(@RequestBody body: SecurityAdviceRequest): SecurityAdviceResponse {
    val (name, amount) = body

    if (paymentHistoryService.getHistory(name).any { it.cardDeclined }) {
      return SecurityAdviceResponse("do 2fa")
    }

    if (amount < 1000) {
      return SecurityAdviceResponse("OK")
    }

    return SecurityAdviceResponse("do 2fa")
  }

}
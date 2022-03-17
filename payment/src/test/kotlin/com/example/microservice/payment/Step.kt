package com.example.microservice.payment

import com.example.microservice.payment.component.SecurityServiceClient

class Step {
  companion object {
    fun requestAdviceForAthena(securityServiceClient: SecurityServiceClient): String? {
      val user = "athena"
      val value = 99
      return securityServiceClient.requestAdvice(user, value)
    }

  }
}
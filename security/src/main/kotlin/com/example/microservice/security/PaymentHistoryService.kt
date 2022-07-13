package com.example.microservice.security

import org.springframework.stereotype.Service

@Service
class PaymentHistoryService {

  val userToPayments: Map<String, List<Payment>> = mutableMapOf()

  fun getHistory(user: String): List<Payment> {
    return userToPayments.getOrDefault(user, emptyList())
  }
}

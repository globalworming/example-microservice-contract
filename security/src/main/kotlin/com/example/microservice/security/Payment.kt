package com.example.microservice.security

data class Payment(
  val user: String,
  val cardDeclined: Boolean
)

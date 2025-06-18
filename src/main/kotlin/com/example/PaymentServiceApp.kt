package com.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PaymentServiceApp

fun main(args: Array<String>) {
    runApplication<PaymentServiceApp>(*args)
}

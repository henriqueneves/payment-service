package com.example

import com.example.application.domain.service.PayoutService
import com.example.application.port.inbound.PayoutUseCase
import com.example.application.port.outbound.DeliveryRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfiguration {

    @Bean
    fun payoutService(
        deliveryRepository: DeliveryRepository
    ): PayoutUseCase = PayoutService(deliveryRepository)

}
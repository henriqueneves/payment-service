package com.example.application.domain.service

import com.example.application.domain.valueobject.Money
import com.example.application.domain.valueobject.Money.Companion.toMoney
import com.example.application.port.inbound.PayoutUseCase
import com.example.application.port.outbound.DeliveryRepository
import com.example.application.domain.valueobject.Constants
import com.example.application.domain.valueobject.DeliverySequence

class PayoutService(
    private val deliveryRepository: DeliveryRepository
) : PayoutUseCase {

    override fun calculate(driverId: Int): Money {
        val completedDeliveries = findDeliveries(driverId).filter {
            it.isSuccessfullyCompleted()
        }.sortedBy { it.startedAt() }

        return DeliverySequence.groupBySequence(completedDeliveries).sumOf { sequence ->
            val totalMinutes = sequence.sumOf { it.completionTimeInMinutes() }.toBigDecimal()

            sequence.size.toBigDecimal() * totalMinutes * Constants.BASE_RATE_PER_MINUTE
        }.toMoney()
    }

    private fun findDeliveries(driverId: Int) = deliveryRepository.findByDriverId(driverId)

}
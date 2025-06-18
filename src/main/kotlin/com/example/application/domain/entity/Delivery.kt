package com.example.application.domain.entity

import java.time.Duration
import java.time.LocalDateTime

data class Delivery(
    val orderId: Int,
    val driverId: Int,
    val statuses: List<DeliveryStatus>,
) {

    data class DeliveryStatus(
        val status: Status,
        val occurredAt: LocalDateTime,
    ) {

        enum class Status {
            PICKED_UP, DELIVERED, CANCELED;
        }

    }

    fun completedAt() = requireNotNull(
        getStatusOrNull(DeliveryStatus.Status.DELIVERED)?.occurredAt
    ) { "Completed status not found." }

    fun startedAt() = requireNotNull(
        getStatusOrNull(DeliveryStatus.Status.PICKED_UP)?.occurredAt
    ) { "Starting status not found." }

    fun completionTimeInMinutes() = Duration.between(startedAt(), completedAt()).toMinutes()

    fun isSuccessfullyCompleted() = getStatusOrNull(status = DeliveryStatus.Status.PICKED_UP) != null
            && getStatusOrNull(status = DeliveryStatus.Status.DELIVERED) != null

    private fun getStatusOrNull(status: DeliveryStatus.Status) =
        statuses.firstOrNull {
            it.status == status
        }

}
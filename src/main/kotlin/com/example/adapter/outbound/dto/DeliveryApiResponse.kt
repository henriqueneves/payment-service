package com.example.adapter.outbound.dto

import com.example.application.domain.entity.Delivery
import com.example.application.domain.entity.Delivery.DeliveryStatus.Status
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DeliveryApiResponse(
    val id: Int,
    val driverId: Int,
    val status: String,
    val timestamp: String,
) {

    companion object {

        fun List<DeliveryApiResponse>.toDomain() : List<Delivery> {
            val formatter = DateTimeFormatter.ISO_DATE_TIME

            return this
                .groupBy { it.id }
                .map { (id, group) ->
                    Delivery(
                        orderId = id,
                        driverId = group.first().driverId,
                        statuses = group.map {
                            Delivery.DeliveryStatus(
                                status = Status.valueOf(it.status),
                                occurredAt = LocalDateTime.parse(it.timestamp, formatter)
                            )
                        }
                    )
                }

        }
    }
}
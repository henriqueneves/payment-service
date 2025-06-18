package com.example.adapter.outbound.dto

import com.example.adapter.outbound.dto.DeliveryApiResponse.Companion.toDomain
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDateTime
import com.example.application.domain.entity.Delivery.DeliveryStatus.Status

class DeliveryApiResponseTest {

    @Test
    fun `should convert DeliveryApiResponse list to domain Delivery list`() {

        val input = listOf(
            DeliveryApiResponse(
                id = 1,
                driverId = 101,
                status = "PICKED_UP",
                timestamp = "2025-06-18T10:00:00"
            ),
            DeliveryApiResponse(
                id = 1,
                driverId = 101,
                status = "DELIVERED",
                timestamp = "2025-06-18T11:00:00"
            ),
            DeliveryApiResponse(
                id = 2,
                driverId = 101,
                status = "PICKED_UP",
                timestamp = "2025-06-18T10:00:00"
            ),
            DeliveryApiResponse(
                id = 2,
                driverId = 101,
                status = "CANCELED",
                timestamp = "2025-06-19T12:00:00"
            )
        )

        val deliveries = input.toDomain()

        assertEquals(2, deliveries.size)

        val firstDelivery = deliveries.find { it.orderId == 1 }
        assertNotNull(firstDelivery)
        assertEquals(101, firstDelivery!!.driverId)
        assertEquals(2, firstDelivery.statuses.size)
        assertEquals(Status.PICKED_UP, firstDelivery.statuses[0].status)
        assertEquals(Status.DELIVERED, firstDelivery.statuses[1].status)

        val secondDelivery = deliveries.find { it.orderId == 2 }
        assertNotNull(secondDelivery)
        assertEquals(101, secondDelivery!!.driverId)
        assertEquals(2, secondDelivery.statuses.size)
        assertEquals(Status.PICKED_UP, secondDelivery.statuses[0].status)
        assertEquals(Status.CANCELED, secondDelivery.statuses[1].status)
    }
}
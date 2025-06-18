package com.example.application.domain.valueobject

import com.example.application.domain.entity.Delivery
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class DeliverySequenceTest {

    @Test
    fun `it should return empty list when input is empty`() {
        val result = DeliverySequence.groupBySequence(emptyList())
        assertTrue(result.isEmpty())
    }

    @Test
    fun `it should group all deliveries into one group when gaps are within tolerance`() {
        val d1 = createDelivery(
            startedAt = time(10, 0),
            completedAt = time(10, 30)
        )
        val d2 = createDelivery(
            startedAt = time(10, 35),
            completedAt = time(11, 0)
        )
        val d3 = createDelivery(
            startedAt = time(11, 4),
            completedAt = time(11, 40)
        )
        val deliveries = listOf(d1, d2, d3)

        val result = DeliverySequence.groupBySequence(deliveries)

        assertEquals(1, result.size)
        assertEquals(3, result.first().size)
    }

    @Test
    fun `it should split into separate groups when gaps exceed tolerance`() {
        val d1 = createDelivery(
            startedAt = time(10, 0),
            completedAt = time(10, 30)
        )
        val d2 = createDelivery(
            startedAt = time(11, 0),
            completedAt = time(11, 30)
        )
        val d3 = createDelivery(
            startedAt = time(11, 35),
            completedAt = time(12, 0)
        )
        val deliveries = listOf(d1, d2, d3)

        val result = DeliverySequence.groupBySequence(deliveries)

        assertEquals(2, result.size)
        assertEquals(listOf(d1), result[0])
        assertEquals(listOf(d2, d3), result[1])
    }

    @Test
    fun `it should place each delivery in its own group if gaps are large`() {
        val d1 = createDelivery(
            startedAt = time(8, 0),
            completedAt = time(8, 30)
        )
        val d2 = createDelivery(
            startedAt = time(9, 0),
            completedAt = time(9, 30)
        )
        val d3 = createDelivery(
            startedAt = time(10, 15),
            completedAt = time(10, 45)
        )
        val deliveries = listOf(d1, d2, d3)

        val result = DeliverySequence.groupBySequence(deliveries)

        assertEquals(3, result.size)
    }

    private fun time(hour: Int, minute: Int): LocalDateTime =
        LocalDateTime.of(2025, 6, 18, hour, minute)

    private fun createDelivery(startedAt: LocalDateTime, completedAt: LocalDateTime) =
        Delivery(
            orderId = 1,
            driverId = 1,
            statuses = listOf(
                Delivery.DeliveryStatus(
                    status = Delivery.DeliveryStatus.Status.PICKED_UP,
                    occurredAt = startedAt
                ),
                Delivery.DeliveryStatus(
                    status = Delivery.DeliveryStatus.Status.DELIVERED,
                    occurredAt = completedAt
                )
            )
        )
}
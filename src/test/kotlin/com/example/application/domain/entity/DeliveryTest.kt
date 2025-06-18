package com.example.application.domain.entity

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class DeliveryTest {

    private val pickupTime = LocalDateTime.of(2025, 6, 18, 10, 0)
    private val deliveryTime = LocalDateTime.of(2025, 6, 18, 10, 45)

    @Test
    fun `completedAt should return delivered timestamp`() {
        val delivery = deliveryWithStatuses(
            Delivery.DeliveryStatus(Delivery.DeliveryStatus.Status.DELIVERED, deliveryTime)
        )

        assertEquals(deliveryTime, delivery.completedAt())
    }

    @Test
    fun `startedAt should return picked up timestamp`() {
        val delivery = deliveryWithStatuses(
            Delivery.DeliveryStatus(Delivery.DeliveryStatus.Status.PICKED_UP, pickupTime)
        )

        assertEquals(pickupTime, delivery.startedAt())
    }

    @Test
    fun `completionTimeInMinutes should return correct duration`() {
        val delivery = deliveryWithStatuses(
            Delivery.DeliveryStatus(Delivery.DeliveryStatus.Status.PICKED_UP, pickupTime),
            Delivery.DeliveryStatus(Delivery.DeliveryStatus.Status.DELIVERED, deliveryTime)
        )

        assertEquals(45, delivery.completionTimeInMinutes())
    }

    @Test
    fun `isSuccessfullyCompleted should return true when both picked up and delivered exist`() {
        val delivery = deliveryWithStatuses(
            Delivery.DeliveryStatus(Delivery.DeliveryStatus.Status.PICKED_UP, pickupTime),
            Delivery.DeliveryStatus(Delivery.DeliveryStatus.Status.DELIVERED, deliveryTime)
        )

        assertTrue(delivery.isSuccessfullyCompleted())
    }

    @Test
    fun `isSuccessfullyCompleted should return false if delivered is missing`() {
        val delivery = deliveryWithStatuses(
            Delivery.DeliveryStatus(Delivery.DeliveryStatus.Status.PICKED_UP, pickupTime)
        )

        assertFalse(delivery.isSuccessfullyCompleted())
    }

    @Test
    fun `isSuccessfullyCompleted should return false if picked up is missing`() {
        val delivery = deliveryWithStatuses(
            Delivery.DeliveryStatus(Delivery.DeliveryStatus.Status.DELIVERED, deliveryTime)
        )

        assertFalse(delivery.isSuccessfullyCompleted())
    }

    @Test
    fun `startedAt should throw exception if PICKED_UP is missing`() {
        val delivery = deliveryWithStatuses(
            Delivery.DeliveryStatus(Delivery.DeliveryStatus.Status.DELIVERED, deliveryTime)
        )

        val exception = assertThrows<IllegalArgumentException> {
            delivery.startedAt()
        }

        assertEquals("Starting status not found.", exception.message)
    }

    @Test
    fun `completedAt should throw exception if DELIVERED is missing`() {
        val delivery = deliveryWithStatuses(
            Delivery.DeliveryStatus(Delivery.DeliveryStatus.Status.PICKED_UP, pickupTime)
        )

        val exception = assertThrows<IllegalArgumentException> {
            delivery.completedAt()
        }

        assertEquals("Completed status not found.", exception.message)
    }

    private fun deliveryWithStatuses(vararg statuses: Delivery.DeliveryStatus): Delivery {
        return Delivery(
            orderId = 1,
            driverId = 100,
            statuses = statuses.toList()
        )
    }
}
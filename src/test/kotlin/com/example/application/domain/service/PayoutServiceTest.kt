package com.example.application.domain.service
import com.example.application.domain.entity.Delivery
import com.example.application.domain.valueobject.Constants
import com.example.application.domain.valueobject.Money.Companion.toMoney
import com.example.application.port.outbound.DeliveryRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.*
import java.math.BigDecimal
import java.time.Duration
import java.time.LocalDateTime

class PayoutServiceTest {

    private lateinit var deliveryRepository: DeliveryRepository
    private lateinit var payoutService: PayoutService

    @BeforeEach
    fun setUp() {
        deliveryRepository = mock(DeliveryRepository::class.java)
        payoutService = PayoutService(deliveryRepository)
    }

    @Test
    fun `it should calculate payout based on successful deliveries`() {
        // Given
        val pickupTime1 = LocalDateTime.of(2025, 6, 18, 10, 0)
        val deliveryTime1 = LocalDateTime.of(2025, 6, 18, 10, 30)

        val pickupTime2 = LocalDateTime.of(2025, 6, 18, 11, 0)
        val deliveryTime2 = LocalDateTime.of(2025, 6, 18, 11, 45)

        val delivery1 = createDelivery(1, 100, pickupTime1, deliveryTime1)
        val delivery2 = createDelivery(2, 100, pickupTime2, deliveryTime2)

        val driverId = 100

        `when`(deliveryRepository.findByDriverId(driverId)).thenReturn(listOf(delivery1, delivery2))

        // When
        val result = payoutService.calculate(driverId)

        // Then
        val totalMinutes1 = Duration.between(pickupTime1, deliveryTime1).toMinutes()
        val totalMinutes2 = Duration.between(pickupTime2, deliveryTime2).toMinutes()

        val group1Total = BigDecimal(totalMinutes1 + totalMinutes2)
        val expected = (group1Total * Constants.BASE_RATE_PER_MINUTE).toMoney()

        assertEquals(expected, result)
    }

    @Test
    fun `it should calculate payout based on in sequence successful deliveries`() {
        // Given
        val pickupTime1 = LocalDateTime.of(2025, 6, 18, 10, 0)
        val deliveryTime1 = LocalDateTime.of(2025, 6, 18, 10, 30)

        val pickupTime2 = LocalDateTime.of(2025, 6, 18, 10, 30)
        val deliveryTime2 = LocalDateTime.of(2025, 6, 18, 11, 15)

        val delivery1 = createDelivery(1, 100, pickupTime1, deliveryTime1)
        val delivery2 = createDelivery(2, 100, pickupTime2, deliveryTime2)

        val driverId = 100

        `when`(deliveryRepository.findByDriverId(driverId)).thenReturn(listOf(delivery1, delivery2))

        // When
        val result = payoutService.calculate(driverId)

        // Then
        val totalMinutes1 = Duration.between(pickupTime1, deliveryTime1).toMinutes()
        val totalMinutes2 = Duration.between(pickupTime2, deliveryTime2).toMinutes()

        val group1Total = BigDecimal(totalMinutes1 + totalMinutes2)
        val bonusBySequence = BigDecimal.TWO
        val expected = (bonusBySequence * group1Total * Constants.BASE_RATE_PER_MINUTE).toMoney()

        assertEquals(expected, result)
    }

    private fun createDelivery(orderId: Int, driverId: Int, start: LocalDateTime, end: LocalDateTime): Delivery {
        return Delivery(
            orderId = orderId,
            driverId = driverId,
            statuses = listOf(
                Delivery.DeliveryStatus(Delivery.DeliveryStatus.Status.PICKED_UP, start),
                Delivery.DeliveryStatus(Delivery.DeliveryStatus.Status.DELIVERED, end)
            )
        )
    }
}
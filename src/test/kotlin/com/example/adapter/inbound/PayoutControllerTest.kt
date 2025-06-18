package com.example.adapter.inbound

import com.example.adapter.inbound.dto.CalculatePayoutRequest
import com.example.application.domain.valueobject.Money
import com.example.application.port.inbound.PayoutUseCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.math.BigDecimal

@ExtendWith(SpringExtension::class)
class PayoutControllerUnitTest {

    private val payoutUseCase = mock(PayoutUseCase::class.java)
    private val controller = PayoutController(payoutUseCase)

    @Test
    fun `should return correct payout`() {
        val request = CalculatePayoutRequest(42)
        `when`(payoutUseCase.calculate(42)).thenReturn(Money(BigDecimal("123.45")))

        val response = controller.calculatePayout(request)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(BigDecimal("123.45"), response.body?.payout)
    }
}
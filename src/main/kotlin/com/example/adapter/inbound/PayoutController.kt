package com.example.adapter.inbound

import com.example.application.port.inbound.PayoutUseCase
import com.example.adapter.inbound.dto.CalculatePayoutRequest
import com.example.adapter.inbound.dto.CalculatePayoutResponse
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/payout")
class PayoutController(
    val payoutUseCase: PayoutUseCase
) {

    @PostMapping
    fun calculatePayout(
        @RequestBody request: CalculatePayoutRequest
    ): ResponseEntity<CalculatePayoutResponse> {
        val payout = payoutUseCase.calculate(request.driverId)
        return ok(CalculatePayoutResponse(payout.amount))
    }

}
package com.example.application.port.inbound

import com.example.application.domain.valueobject.Money

interface PayoutUseCase {

    fun calculate(driverId: Int) : Money

}
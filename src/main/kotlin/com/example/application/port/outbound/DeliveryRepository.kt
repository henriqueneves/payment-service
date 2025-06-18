package com.example.application.port.outbound

import com.example.application.domain.entity.Delivery

interface DeliveryRepository {

    fun findByDriverId(driverId: Int): List<Delivery>

}
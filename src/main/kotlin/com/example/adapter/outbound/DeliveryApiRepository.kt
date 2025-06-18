package com.example.adapter.outbound

import com.example.adapter.outbound.dto.DeliveryApiResponse
import com.example.adapter.outbound.dto.DeliveryApiResponse.Companion.toDomain
import com.example.application.domain.entity.Delivery
import com.example.application.port.outbound.DeliveryRepository
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.stereotype.Component
import java.net.URI

@Component
class DeliveryApiRepository : DeliveryRepository {

    override fun findByDriverId(driverId: Int): List<Delivery> =
        deserialize(
            response = getRequest(driverId)
        ).toDomain()

    private fun getRequest(driverId: Int): String = URI(
        "http://localhost:8081/deliveries?driverId=$driverId" //Wiremock
    ).toURL().readText()

    private fun deserialize(response: String) =
        jacksonObjectMapper().readValue(
            response,
            object : TypeReference<List<DeliveryApiResponse>>() {}
        )

}
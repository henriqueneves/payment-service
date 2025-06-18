package com.example.application.domain.valueobject

import com.example.application.domain.entity.Delivery
import java.time.Duration

object DeliverySequence {

     fun groupBySequence(deliveries: List<Delivery>): List<List<Delivery>> {
        val tolerance = Duration.ofMinutes(Constants.SEQUENCE_GAP_TOLERANCE_IN_MINUTES)

        if (deliveries.isEmpty()) return emptyList()

        val grouped = mutableListOf<MutableList<Delivery>>()
        var currentGroup = mutableListOf<Delivery>()

        for (i in deliveries.indices) {
            val current = deliveries[i]

            if (currentGroup.isEmpty()) {
                currentGroup.add(current)
            } else {
                val previous = currentGroup.last()
                val gap = Duration.between(previous.completedAt(), current.startedAt())

                if (gap <= tolerance) {
                    currentGroup.add(current)
                } else {
                    grouped.add(currentGroup)
                    currentGroup = mutableListOf(current)
                }
            }
        }

        if (currentGroup.isNotEmpty()) {
            grouped.add(currentGroup)
        }

        return grouped
    }

}
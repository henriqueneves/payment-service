package com.example.application.domain.valueobject

import java.math.BigDecimal
import java.math.RoundingMode

@JvmInline
value class Money( val amount: BigDecimal ){

    companion object {
        fun BigDecimal.toMoney() = Money(this.setScale(2, RoundingMode.HALF_UP))
    }

}
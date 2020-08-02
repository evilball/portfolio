package com.videodemons.zacksportfolio.controller.response

import java.math.BigDecimal
import java.time.LocalDate

data class Tick(
        val symbol: String,
        val date: LocalDate,
        val open: BigDecimal,
        val low: BigDecimal,
        val high: BigDecimal,
        val close: BigDecimal)
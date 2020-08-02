package com.videodemons.zacksportfolio.controller.response

import java.math.BigDecimal

data class CalculatedPortfolio(
        val portfolio: List<CalculatedEntry>,
        val totalVolatility: Double,
        val base: Double,
        val baseSum: BigDecimal,
        val adjustedSum: BigDecimal
)
package com.videodemons.portfolio.controller.response

import java.math.BigDecimal

data class CalculatedEntry(
        val ticker: String,
        val volatility: Double,
        val inversedVolatility: Double,
        val sum: BigDecimal,
        val adjustedSum: BigDecimal,
        val entryPrice: BigDecimal,
        val lot: BigDecimal 
)
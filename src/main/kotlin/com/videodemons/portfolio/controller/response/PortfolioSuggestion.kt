package com.videodemons.portfolio.controller.response

import java.math.BigDecimal

data class PortfolioSuggestion(
        val totalSum: BigDecimal,
        val adjustedSum: BigDecimal,
        val removedTickers: Collection<ZacksTickerInfo>,
        val newTickers: Collection<ZacksTickerInfo>,
        val portfolio: Collection<PortfolioEntry>
)
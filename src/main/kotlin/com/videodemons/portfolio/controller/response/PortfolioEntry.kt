package com.videodemons.portfolio.controller.response

data class PortfolioEntry (
    val tickerInfo: ZacksTickerInfo,
    val calculatedEntry: CalculatedEntry
)
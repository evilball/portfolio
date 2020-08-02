package com.videodemons.zacksportfolio.controller.response

data class PortfolioEntry (
    val tickerInfo: ZacksTickerInfo,
    val calculatedEntry: CalculatedEntry
)
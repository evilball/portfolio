package com.videodemons.portfolio.controller.response

class QuoteStatistic (
    val ticker: String,
    val volatility: Double,
    val inversedVolatility: Double,
    val quoutes: List<Tick>
)
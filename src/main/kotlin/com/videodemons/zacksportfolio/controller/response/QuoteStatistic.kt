package com.videodemons.zacksportfolio.controller.response

class QuoteStatistic (
    val ticker: String,
    val volatility: Double,
    val inversedVolatility: Double,
    val quoutes: List<Tick>
)
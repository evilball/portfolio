package com.videodemons.zacksportfolio.controller.response

data class ZacksTickerInfo(val ticker: String,
                      val rank: Int,
                      val value: ZacksScore,
                      val growth: ZacksScore,
                      val momentum: ZacksScore) {
    
    val totalScore: Double 
        get() = (1 + rank * 0.5) * (value.score * 1.3 + growth.score * 1.2 + momentum.score * 1.1)
    
    constructor(ticker: String, rank: Int, value: String, growth: String, momentum: String):
            this(ticker, rank, ZacksScore.fromString(value), ZacksScore.fromString(growth), ZacksScore.fromString(momentum))
}
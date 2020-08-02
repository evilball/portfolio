package com.videodemons.zacksportfolio.service.zacks

interface Tickers {
    fun readTickers(): Collection<String>
    fun removeTicker(ticker: String): Collection<String>
    fun addTicker(ticker: String): Collection<String>
    fun addTickers(tickers: Collection<String>): Collection<String>
    fun saveTickers(tickers: Collection<String>): Collection<String>
}
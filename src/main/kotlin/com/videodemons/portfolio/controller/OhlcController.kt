package com.videodemons.portfolio.controller

import com.videodemons.portfolio.controller.response.Tick
import com.videodemons.portfolio.service.yahoo.YahooFinanceService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tickers/ohlc")
class OhlcController(
        private val yahooFinanceService: YahooFinanceService) {
    
    @GetMapping("{ticker}")
    fun ohlc(@PathVariable("ticker") ticker: String): Collection<Tick> {
        return yahooFinanceService.getQuotes(ticker)
    }            
}
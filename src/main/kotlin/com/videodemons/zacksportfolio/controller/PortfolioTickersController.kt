package com.videodemons.zacksportfolio.controller

import com.videodemons.zacksportfolio.service.zacks.Tickers
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/portfolio/tickers")
class PortfolioTickersController(
        @Qualifier("portfolioTickers") private val tickers: Tickers) {
    
    @GetMapping
    fun portfolio() = ResponseEntity.ok(tickers.readTickers())
    
    @DeleteMapping
    fun removeTicker(@PathVariable("ticker") ticker: String) =
            ResponseEntity.ok(tickers.removeTicker(ticker))
    
    @PutMapping
    fun addTicker(@PathVariable("ticker") ticker: String) =
            ResponseEntity.ok(tickers.addTicker(ticker))
}
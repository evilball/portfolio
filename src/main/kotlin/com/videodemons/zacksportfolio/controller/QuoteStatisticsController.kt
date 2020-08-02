package com.videodemons.zacksportfolio.controller

import com.videodemons.zacksportfolio.controller.response.CalculatedPortfolio
import com.videodemons.zacksportfolio.service.buildPortfolio
import com.videodemons.zacksportfolio.service.volatility
import com.videodemons.zacksportfolio.service.yahoo.YahooFinanceService
import com.videodemons.zacksportfolio.service.zacks.Tickers
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tickers/statistic/")
class QuoteStatisticsController(
        private val yahooFinanceService: YahooFinanceService,
        @Qualifier("portfolioTickers") private val tickers: Tickers) {
    
    @GetMapping("volatility/{ticker}")
    fun meanDeviation(@PathVariable("ticker") ticker: String, period: Int?): ResponseEntity<*> {
        return ResponseEntity.ok(yahooFinanceService.getQuotes(ticker).volatility())
    }
    
    @GetMapping("volatility/portfolio")
    fun meanDeviation(portfolioSum: Double?): CalculatedPortfolio {
        return buildPortfolio(
                portfolioSum ?: 17800.0,
                { tickers.readTickers() },
                { ticker -> yahooFinanceService.getQuotes(ticker) })
    }
}
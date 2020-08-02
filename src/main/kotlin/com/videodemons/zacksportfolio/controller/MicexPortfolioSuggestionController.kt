package com.videodemons.zacksportfolio.controller

import com.videodemons.zacksportfolio.controller.response.CalculatedPortfolio
import com.videodemons.zacksportfolio.service.buildPortfolio
import com.videodemons.zacksportfolio.service.yahoo.YahooFinanceService
import com.videodemons.zacksportfolio.service.zacks.Tickers
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

/**
 * Portfolio builder for micex exchange
 */
@Controller
@RequestMapping("/portfolio/micex/suggest")
class MicexPortfolioSuggestionController(
        private val yahooFinanceService: YahooFinanceService,
        @Qualifier("micexSmartEstimateCandidateTickers") private val micexCandidates: Tickers) {

    @GetMapping
    @ResponseBody
    fun suggest(sum: Double?, vPeriod: Int?): CalculatedPortfolio {
        val portfolioSum = sum ?: 478000.0
        val volatilityPeriod = vPeriod ?: 60
        val portfolio =
                micexCandidates.readTickers()
        return buildPortfolio(
                portfolioSum,
                tickers = { portfolio },
                quotes = { yahooFinanceService.getQuotes(it) },
                volatilityPeriod = volatilityPeriod)
    }
}
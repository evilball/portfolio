package com.videodemons.zacksportfolio.controller

import com.videodemons.zacksportfolio.controller.response.PortfolioEntry
import com.videodemons.zacksportfolio.controller.response.PortfolioSuggestion
import com.videodemons.zacksportfolio.controller.response.ZacksTickerInfo
import com.videodemons.zacksportfolio.service.buildPortfolio
import com.videodemons.zacksportfolio.service.yahoo.YahooFinanceService
import com.videodemons.zacksportfolio.service.zacks.Tickers
import com.videodemons.zacksportfolio.service.zacks.ZacksTickerInfoService
import com.videodemons.zacksportfolio.service.zacks.getAll
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Portfolio builder based on zacks ranks
 */
@RestController
@RequestMapping("/portfolio/zacks/suggest")
class ZacksRankSuggestionController(
        @Qualifier("candidateStrategies") private val candidateStrategy: CandidateStrategy,
        @Qualifier("portfolioTickers") private val portfolioTickers: Tickers,
        private val zacksTickerInfoService: ZacksTickerInfoService,
        private val yahooFinanceService: YahooFinanceService,
        private val dropStrategy: DropStrategy<ZacksTickerInfo>) {

    /**
     * @param sum: Total portfolio sum
     * @param vPeriod: Volatility period
     * @param strategy: Portfolio strategy
     */
    @GetMapping
    fun suggest(portfolioSum: Double,
                volatilityPeriod: Int,
                strategy: String): PortfolioSuggestion {
        val candidateTickers = candidateStrategy.candidates[strategy] ?: error("Unknown strategy!")
        
        val currentPortfolio = zacksTickerInfoService.getAll(portfolioTickers.readTickers())


        val (newPortfolio, removedTickers) = dropStrategy.drop(currentPortfolio)

        
        val candidates = zacksTickerInfoService.getAll(candidateTickers.readTickers())
        candidates.sortBy { it.totalScore }
        
        val newTickers = arrayListOf<ZacksTickerInfo>()
        for (i in 0 until (removedTickers.size - 1)) {
            newTickers.add(candidates[i])
        }
        newPortfolio.addAll(newTickers)
        newPortfolio.sortBy { it.totalScore }

        val calculatedPortfolio = buildPortfolio(
                portfolioSum,
                tickers = { newPortfolio.map { it.ticker } },
                quotes = { yahooFinanceService.getQuotes(it) },
                volatilityPeriod = volatilityPeriod)
        val groupedByTicker = calculatedPortfolio.portfolio.map { it.ticker to it}.toMap()
        
        val calculatedEntries = mutableListOf<PortfolioEntry>()
        for (entry in newPortfolio) {
            calculatedEntries += PortfolioEntry(entry, groupedByTicker[entry.ticker] ?: error("Unknown ticker: ${entry.ticker}!"))
        }
        
        return PortfolioSuggestion(
                calculatedPortfolio.baseSum,
                calculatedPortfolio.adjustedSum,
                removedTickers,
                newTickers,
                calculatedEntries)
    }
}
package com.videodemons.zacksportfolio.service

import com.videodemons.zacksportfolio.controller.response.CalculatedPortfolio
import com.videodemons.zacksportfolio.controller.response.Tick
import com.videodemons.zacksportfolio.controller.response.CalculatedEntry
import com.videodemons.zacksportfolio.controller.response.QuoteStatistic
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import java.math.BigDecimal
import java.math.RoundingMode

fun List<Tick>.volatility(period: Int = 60): Double {
    val closePrices = this
            .asSequence()
            .map { it.close }
            .toList()
    val returns = mutableListOf<BigDecimal>()
    var prev: BigDecimal? = null
    for (close in closePrices) {
        if (prev != null) {
            returns.add(close / prev - BigDecimal.ONE)
        }
        prev = close
    }
    return DescriptiveStatistics(
            returns.drop(returns.size - (period))
                    .map { it.toDouble() }
                    .toDoubleArray())
            .standardDeviation
}

fun buildPortfolio(portfolioSum: Double,
                   tickers: () -> Collection<String>,
                   quotes: (String) -> List<Tick>,
                   volatilityPeriod: Int = 60): CalculatedPortfolio {
    val quoteStatistics = HashMap<String, QuoteStatistic>()
    var totalVolatility = .0
    val portfolio = tickers()
    for (ticker in portfolio) {
        val lastQuoutes = quotes(ticker)
        val vola = lastQuoutes.volatility(volatilityPeriod)
        val invVola = 1.0 / vola
        totalVolatility += invVola
        quoteStatistics.put(ticker, QuoteStatistic(ticker, vola, invVola, lastQuoutes))
    }
    val base = totalVolatility / portfolio.size
    val deviations = mutableListOf<CalculatedEntry>()
    
    var totalSum = .0.toBigDecimal().setScale(2)
    var totalAdjustedSum = .0.toBigDecimal().setScale(2)
    for (stock in portfolio) {
        val statistic = quoteStatistics[stock]
        val sum = (statistic!!.inversedVolatility * (portfolioSum / portfolio.size) / base)
                .toBigDecimal()
                .setScale(2, RoundingMode.HALF_UP)
        val lastClose = statistic.quoutes.last().close.setScale(2, RoundingMode.HALF_UP)
        val lot = (sum / lastClose).setScale(0, RoundingMode.HALF_UP)
        val adjustedSum = lot * lastClose

        totalSum += sum
        totalAdjustedSum += adjustedSum
        deviations.add(CalculatedEntry(
                stock,
                statistic.volatility,
                statistic.inversedVolatility,
                sum,
                adjustedSum,
                lastClose,
                lot))
    }
    return CalculatedPortfolio(deviations, totalVolatility, base, totalSum, totalAdjustedSum)
}
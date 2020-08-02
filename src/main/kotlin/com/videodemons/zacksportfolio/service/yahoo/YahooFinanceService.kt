package com.videodemons.zacksportfolio.service.yahoo

import com.videodemons.zacksportfolio.controller.response.Tick
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import yahoofinance.YahooFinance
import yahoofinance.histquotes.Interval
import java.time.*
import java.util.*

@Component
open class YahooFinanceService {
    
    @Cacheable("yahoo-stock")
    open fun getQuotes(ticker: String) =
            YahooFinance.get(ticker, DEFAULT_FROM, Interval.DAILY)
                    .history
                    .asSequence()
                    .map { Tick(it.symbol, LocalDateTime.ofInstant(
                            it.date.toInstant(), ZoneId.systemDefault())
                            .toLocalDate(), it.open, it.low, it.high, it.close) }
                    .toList()
    
    
    companion object {
        val DEFAULT_FROM: Calendar = GregorianCalendar.from(ZonedDateTime.now().minusMonths(13))
    }
}
package com.videodemons.zacksportfolio.config

import com.videodemons.zacksportfolio.service.zacks.Tickers
import com.videodemons.zacksportfolio.service.zacks.TxtTickers
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class TxtTickersConfig {

    @Bean
    open fun portfolioTickers(@Value("\${portfolio.nyse-portfolio-path}") tickersPath: String): Tickers =
            TxtTickers(tickersPath)

    @Bean
    open fun nyseMomentumCandidateTickers(@Value("\${portfolio.nyse-momentum-candidates-path}") tickersPath: String): Tickers =
            TxtTickers(tickersPath)

    @Bean
    open fun nyseGrowthCandidateTickers(@Value("\${portfolio.nyse-growth-candidates-path}") tickersPath: String): Tickers =
            TxtTickers(tickersPath)

    @Bean
    open fun nyseValueCandidateTickers(@Value("\${portfolio.nyse-value-candidates-path}") tickersPath: String): Tickers =
            TxtTickers(tickersPath)

    @Bean
    open fun nyseSmartEstimateCandidateTickers(@Value("\${portfolio.nyse-smart-estimate-path}") tickersPath: String): Tickers =
            TxtTickers(tickersPath)
    
    @Bean
    open fun micexSmartEstimateCandidateTickers(@Value("\${portfolio.micex-smart-estimate-path") tickersPath: String): Tickers =
            TxtTickers(tickersPath)
}
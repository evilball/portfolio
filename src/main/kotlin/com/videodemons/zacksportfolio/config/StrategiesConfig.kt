package com.videodemons.zacksportfolio.config

import com.videodemons.zacksportfolio.controller.CandidateStrategy
import com.videodemons.zacksportfolio.service.zacks.Tickers
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class StrategiesConfig {

    @Bean
    open fun candidateStrategies(
            @Qualifier("nyseMomentumCandidateTickers") momentumCandidateTickers: Tickers,
            @Qualifier("nyseGrowthCandidateTickers") growthCandidateTickers: Tickers,
            @Qualifier("nyseValueCandidateTickers") valueCandidateTickers: Tickers,
            @Qualifier("nyseSmartEstimateCandidateTickers") smartEstimateCandidateTickers: Tickers) =
            CandidateStrategy(candidates = mapOf(
                    "momentum" to momentumCandidateTickers,
                    "growth" to growthCandidateTickers,
                    "value" to valueCandidateTickers,
                    "smart_estimate" to smartEstimateCandidateTickers
            ))
}
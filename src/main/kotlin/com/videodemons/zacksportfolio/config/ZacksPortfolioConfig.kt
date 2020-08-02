package com.videodemons.zacksportfolio.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
open class ZacksPortfolioConfig {
    
    @Bean
    open fun restTemplate() = RestTemplate()
}
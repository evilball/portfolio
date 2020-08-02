package com.videodemons.portfolio

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootApplication
open class PortfolioApplication

fun main(args: Array<String>) {
    SpringApplication.run(PortfolioApplication::class.java, *args)
}
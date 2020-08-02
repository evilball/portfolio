package com.videodemons.zacksportfolio.service.zacks

import com.videodemons.zacksportfolio.controller.response.ZacksTickerInfo
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

fun ZacksTickerInfoService.getAll(tickers: Collection<String>) =
        tickers.map { get(it) }.toMutableList()

@Component
open class ZacksTickerInfoService(
        private val restTemplate: RestTemplate) {

    @Cacheable("zacks-ticker-info")
    open fun get(ticker: String): ZacksTickerInfo {
        val response = restTemplate.getForEntity(
                "https://www.zacks.com/stock/quote/$ticker",
                String::class.java)
        val document = Jsoup.parse(response.body)

        val scores = document.select(".premium_research_score")
                .select("span.composite_val")
        
        return ZacksTickerInfo(
                ticker,
                rank(document),
                valueScore(scores),
                growthScore(scores),
                momentumScore(scores))
    } 
    
    private fun rank(document: Document) =
            Integer.parseInt(document.select(".rank_view")[0].text().substring(0, 1))
    
    private fun valueScore(scores: Elements) = score(scores, 0)
    private fun growthScore(scores: Elements) = score(scores, 1)
    private fun momentumScore(scores: Elements) = score(scores, 2)

    private fun score(scores: Elements, i: Int) = scores[i].text()
}
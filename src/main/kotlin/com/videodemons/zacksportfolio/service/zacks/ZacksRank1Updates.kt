package com.videodemons.zacksportfolio.service.zacks

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

private val DATE_FORMATTER: DateTimeFormatter =
        DateTimeFormatter.ofPattern("MMMM dd,yyyy").withLocale(Locale.US)

@Component
open class ZacksRank1Updates(private val restTemplate: RestTemplate) {
    
    @Cacheable("zacks-latest-momentum")
    open fun latestMomentum(date: LocalDate): Collection<String> = latestTop(date, "momentum")

    @Cacheable("zacks-latest-value")
    open fun latestValue(date: LocalDate): Collection<String> = latestTop(date, "value")

    @Cacheable("zacks-latest-growth")
    open fun latestGrowth(date: LocalDate): Collection<String> = latestTop(date, "growth")
    
    private fun latestTop(date: LocalDate, filter: String): Collection<String> {
        val response = restTemplate.getForObject(
                "https://www.zacks.com/newsroom/commentary/archive.php?id=25",
                String::class.java)
        val document = Jsoup.parse(response)

        return document.select(".listitem")
                .filter { it.attr("data-page-url").contains("top-ranked-$filter-stocks-to-buy-for-") }
                .filter { afterDate(it, date) }
                .flatMap { tickers(it) }
    }
    
    private fun tickers(e: Element) =
            e.select("span.hoverquote-symbol")
                    .map { it.text() }
    
    private fun afterDate(e: Element, lastDate: LocalDate) =
            lastDate.isBefore(parseDate(e))

    private fun parseDate(e: Element): LocalDate =
            parseDate(e.select("time").text().substring(13))
    
    private fun parseDate(date: String) =
            LocalDate.parse(date, DATE_FORMATTER)
}
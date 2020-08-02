package com.videodemons.portfolio.controller

import com.videodemons.portfolio.controller.response.UpdateResp
import com.videodemons.portfolio.controller.response.ZacksTickerInfo
import com.videodemons.portfolio.service.zacks.Tickers
import com.videodemons.portfolio.service.zacks.ZacksTickerInfoService
import com.videodemons.portfolio.service.zacks.getAll
import org.springframework.http.ResponseEntity
import java.time.LocalDate

fun updateCandidates(tickers: Tickers,
                     zacksTickerInfoService: ZacksTickerInfoService, 
                     updateFun: (LocalDate) -> Collection<String>): ResponseEntity<*> {
    val currentTickers = HashSet(tickers.readTickers())
    val updates = HashSet(updateFun(LocalDate.now().minusDays(4)))
    val newTickers = updates - currentTickers
    val allTickers = updates + currentTickers
    val allTickersInfo = zacksTickerInfoService.getAll(allTickers)
    val newTickersInfo = zacksTickerInfoService.getAll(newTickers)
    tickers.saveTickers(allTickers)
    return ResponseEntity.ok(UpdateResp(newTickersInfo, allTickersInfo))
}

fun rank(zacksTickerInfoService: ZacksTickerInfoService, tickers: Tickers): Collection<ZacksTickerInfo> {
    val tickerInfos = zacksTickerInfoService.getAll(tickers.readTickers())
    tickerInfos.sortBy { it.totalScore }
    return tickerInfos
}
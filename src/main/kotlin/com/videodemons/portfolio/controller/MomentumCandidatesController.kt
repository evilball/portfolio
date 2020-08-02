package com.videodemons.portfolio.controller

import com.videodemons.portfolio.controller.response.ZacksTickerInfo
import com.videodemons.portfolio.service.zacks.Tickers
import com.videodemons.portfolio.service.zacks.ZacksRank1Updates
import com.videodemons.portfolio.service.zacks.ZacksTickerInfoService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/candidates/momentum")
class MomentumCandidatesController(
        @Qualifier("nyseMomentumCandidateTickers") private val tickers: Tickers,
        private val zacksRank1Updates: ZacksRank1Updates,
        private val zacksTickerInfoService: ZacksTickerInfoService) {

    @GetMapping
    fun portfolio() = ResponseEntity.ok(tickers.readTickers())

    @DeleteMapping
    fun removeTicker(@PathVariable("ticker") ticker: String) =
            ResponseEntity.ok(tickers.removeTicker(ticker))

    @PutMapping
    fun addTicker(@PathVariable("ticker") ticker: String) =
            ResponseEntity.ok(tickers.addTicker(ticker))
    
    @PostMapping("update")
    fun updateMomentumCandidates(): ResponseEntity<*> =
            updateCandidates(tickers, zacksTickerInfoService) { zacksRank1Updates.latestMomentum(it) }

    @GetMapping("rank")
    fun rank(): Collection<ZacksTickerInfo> = rank(zacksTickerInfoService, tickers)
}